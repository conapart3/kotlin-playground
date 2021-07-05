package com.conal.asyncdbops1.storageworker

import com.conal.asyncdbops1.AsyncDbOps
import com.conal.corda.DbOpsException
import com.google.common.primitives.Primitives
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type
import java.lang.reflect.TypeVariable
import java.util.HashMap
import java.util.NoSuchElementException
import kotlin.reflect.KFunction
import kotlin.reflect.KParameter
import kotlin.reflect.jvm.javaConstructor
import kotlin.reflect.jvm.javaType

class DbOpsRefFactoryImpl {

    fun createDbOps(dbOpsClass: Class<out AsyncDbOps>, vararg args: Any?) : AsyncDbOpsRef {
        val argTypes = args.map { it?.javaClass }
        val constructor = try {
            findConstructor(dbOpsClass, argTypes)
        } catch (e: IllegalArgumentException) {
            throw DbOpsException("due to ambiguous match against the constructors: $argTypes")
        } catch (e: NoSuchElementException) {
            throw DbOpsException("due to missing constructor for arguments: $argTypes")
        }

        // Build map of args from array
        val argsMap = args.zip(constructor.parameters).map { Pair(it.second.name!!, it.first) }.toMap()
        return createKotlin(dbOpsClass, argsMap)
    }

    fun findConstructor(dbOpsClass: Class<out AsyncDbOps>, argTypes: List<Class<Any>?>): KFunction<AsyncDbOps> {
        try {
            return findConstructorDirectMatch(dbOpsClass, argTypes)
        } catch(e: java.lang.IllegalArgumentException) {
            println("findConstructorDirectMatch threw IllegalArgumentException (more than 1 matches).")
        } catch (e: NoSuchElementException) {
            println("findConstructorDirectMatch threw NoSuchElementException (no matches).")
        }
        return findConstructorCheckDefaultParams(dbOpsClass, argTypes)
    }


    private fun findConstructorDirectMatch(dbOpsClass: Class<out AsyncDbOps>, argTypes: List<Class<Any>?>): KFunction<AsyncDbOps> {
        return dbOpsClass.kotlin.constructors.single { ctor ->
            // Get the types of the arguments, always boxed (as that's what we get in the invocation).
            val ctorTypes = ctor.javaConstructor!!.parameterTypes.map { Primitives.wrap(it) }
            if (argTypes.size != ctorTypes.size)
                return@single false
            for ((argType, ctorType) in argTypes.zip(ctorTypes)) {
                if (argType == null) continue // Try and find a match based on the other arguments.
                if (!ctorType.isAssignableFrom(argType)) return@single false
            }
            true
        }
    }

    private fun findConstructorCheckDefaultParams(dbOpsClass: Class<out AsyncDbOps>, argTypes: List<Class<Any>?>):
            KFunction<AsyncDbOps> {
        // There may be multiple matches. If there are, we will use the one with the least number of default parameter matches.
        var ctorMatch: KFunction<AsyncDbOps>? = null
        var matchNumDefArgs = 0
        for (ctor in dbOpsClass.kotlin.constructors) {
            // Get the types of the arguments, always boxed (as that's what we get in the invocation).
            val ctorTypes = ctor.javaConstructor!!.parameterTypes.map {
                if (it == null) { it } else { Primitives.wrap(it) }
            }

            val optional = ctor.parameters.map { it.isOptional }
            val (matched, numDefaultsUsed) = matchConstructorArgs(ctorTypes, optional, argTypes)
            if (matched) {
                if (ctorMatch == null || numDefaultsUsed < matchNumDefArgs) {
                    ctorMatch = ctor
                    matchNumDefArgs = numDefaultsUsed
                }
            }
        }

        if (ctorMatch == null) {
            handleNoMatchingConstructor(dbOpsClass, argTypes)
            // Must do the throw here, not in handleNoMatchingConstructor(added for Detekt) else we can't return ctorMatch as non-null
            throw DbOpsException("No constructor found that matches arguments (${argTypes.joinToString()}), see log for more information.")
        }

        println("Matched constructor: ${ctorMatch} (num_default_args_used=$matchNumDefArgs)")
        return ctorMatch
    }

    private fun matchConstructorArgs(ctorTypes: List<Class<out Any>>, optional: List<Boolean>,
                                     argTypes: List<Class<Any>?>): Pair<Boolean, Int> {
        // There must be at least as many constructor arguments as supplied arguments
        if (argTypes.size > ctorTypes.size) {
            return Pair(false, 0)
        }

        // Check if all constructor arguments are assignable for all supplied arguments, then for remaining arguments in constructor
        // check that they are optional. If they are it's still a match. Return if matched and the number of default args consumed.
        var numDefaultsUsed = 0
        var index = 0
        for (conArg in ctorTypes) {
            if (index < argTypes.size) {
                val argType = argTypes[index]
                if (argType != null && !conArg.isAssignableFrom(argType)) {
                    return Pair(false, 0)
                }
            } else {
                if (index >= optional.size || !optional[index]) {
                    return Pair(false, 0)
                }
                numDefaultsUsed++
            }
            index++
        }

        return Pair(true, numDefaultsUsed)
    }

    private fun handleNoMatchingConstructor(dbOpsClass: Class<out AsyncDbOps>, argTypes: List<Class<Any>?>) {
        println("Cannot find Constructor to match arguments: ${argTypes.joinToString()}")
        println("Candidate constructors are:")
        for (ctor in dbOpsClass.kotlin.constructors) {
            println("${ctor}")
        }
    }

    private fun createKotlin(type: Class<out AsyncDbOps>, args: Map<String, Any?>): AsyncDbOpsRef {
        // Check we can find a constructor and populate the args to it, but don't call it
        createConstructor(type, args)
        return AsyncDbOpsRefImpl(type.name, args)
    }
    data class AsyncDbOpsRefImpl internal constructor(val dbOpsClassName: String, val args: Map<String, Any?>) : AsyncDbOpsRef
    interface AsyncDbOpsRef

    private fun createConstructor(clazz: Class<out AsyncDbOps>, args: Map<String, Any?>): () -> AsyncDbOps {
        for (constructor in clazz.kotlin.constructors) {
            val params = buildParams(constructor, args) ?: continue
            // If we get here then we matched every parameter
            return { constructor.callBy(params) }
        }
        throw DbOpsException("as could not find matching constructor for: $args")
    }

    private fun buildParams(constructor: KFunction<AsyncDbOps>, args: Map<String, Any?>): HashMap<KParameter, Any?>? {
        val params = hashMapOf<KParameter, Any?>()
        val usedKeys = hashSetOf<String>()
        for (parameter in constructor.parameters) {
            if (!tryBuildParam(args, parameter, params)) {
                return null
            } else {
                usedKeys += parameter.name!!
            }
        }
        if ((args.keys - usedKeys).isNotEmpty()) {
            // Not all args were used
            return null
        }
        return params
    }

    private fun tryBuildParam(args: Map<String, Any?>, parameter: KParameter, params: HashMap<KParameter, Any?>): Boolean {
        val containsKey = parameter.name in args
        // OK to be missing if optional
        return (parameter.isOptional && !containsKey) || (containsKey && paramCanBeBuilt(args, parameter, params))
    }

    private fun paramCanBeBuilt(args: Map<String, Any?>, parameter: KParameter, params: HashMap<KParameter, Any?>): Boolean {
        val value = args[parameter.name]
        params[parameter] = value
        return (value is Any && parameterAssignableFrom(parameter.type.javaType, value)) || parameter.type.isMarkedNullable
    }

    private fun parameterAssignableFrom(type: Type, value: Any): Boolean {
        return if (type is Class<*>) {
            if (type.isPrimitive) {
                Primitives.unwrap(value.javaClass) == type
            } else {
                type.isAssignableFrom(value.javaClass)
            }
        } else if (type is ParameterizedType) {
            parameterAssignableFrom(type.rawType, value)
        } else if (type is TypeVariable<*>) {
            type.bounds.all { parameterAssignableFrom(it, value) }
        } else {
            false
        }
    }
}