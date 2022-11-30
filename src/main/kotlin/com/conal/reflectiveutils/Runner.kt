package com.conal.reflectiveutils

import java.lang.reflect.Field
import kotlin.jvm.internal.Reflection
import kotlin.reflect.KClass
import kotlin.reflect.full.allSuperclasses
import kotlin.reflect.full.allSupertypes

/**
 * allSuperclasses()
 * All superclasses of this class, including indirect ones, in no particular order.
 * Includes superclasses and superinterfaces of the class, but does not include the class itself.
 * The returned collection does not contain more than one instance of any given class.
 */

fun main() {
    val fields = Runner().injectDependencies(
        object : Flow<String> {
            @CordaInject
            lateinit var someString: String
            lateinit var someOtherString: String
            override fun call(): String {
                return ""
            }
        }
    )
    println("Successfully obtained fields for anonymous impl of interface Flow:")
    println(fields)

    val fields2 = Runner().injectDependencies(
        object : SignTransactionFlow() {
            @CordaInject
            lateinit var someString: String
            lateinit var someOtherString: String
            override fun call(): SignedTransaction {
                return SignedTransactionImpl()
            }
        }
    )
    println("Successfully obtained fields for anonymous impl of abstract class SignTransactionFlow:")
    println(fields2)
}

/*class Runner {
    // here flow is the anonymous class, flowStateMachineInjectable doesn't matter
    fun injectDependencies(flow: Flow<*>): Collection<Field> {
        return flow::class.getFieldsForInjection()
    }

    // annotation doesnt matter either
    private fun KClass<*>.getFieldsForInjection(): Collection<Field> {
        return setOf(
            this,
            *this.allSuperclasses.toTypedArray()
        )
            .flatMap { it.java.declaredFields.toSet() }
    }
}*/

class Runner {
    fun injectDependencies(flow: Flow<*>) : Collection<Field> {
        return flow::class.java.getFieldsForInjection()
    }
    private fun Class<*>.getFieldsForInjection(): Collection<Field> {
        return this.getAllFields()
    }
    private fun Class<*>.getAllFields(): Set<Field> {
        val fields = this.declaredFields.toMutableSet()
        if(this.superclass != null)
        {
            fields.addAll(this.superclass.getAllFields())
        }
        return fields
    }
}

@Target(AnnotationTarget.FIELD)
annotation class CordaInject

interface Flow<out T> {
    fun call(): T
}

class ExtendsSignTransactionFlow : SignTransactionFlow() {
    override fun call(): SignedTransaction {
        return SignedTransactionImpl()
    }
}

abstract class SignTransactionFlow : LayerTwoAbstractFlowClass() {
    @CordaInject
    lateinit var fieldOfSuperclass: String
}

abstract class LayerTwoAbstractFlowClass : Flow<SignedTransaction> {
    @CordaInject
    lateinit var fieldOfLayerTwoClass: String
}

interface SignedTransaction {

}

class SignedTransactionImpl : SignedTransaction {

}

/*
class Runner {
    // here flow is the anonymous class, flowStateMachineInjectable doesn't matter
    fun injectDependencies(flow: Flow<*>) : Collection<Field> {
        return flow::class.getFieldsForInjection()
    }
    // annotation doesnt matter either
    private fun KClass<*>.getFieldsForInjection(): Collection<Field> {
        return setOf(
            this,
            *this.allSuperclasses.toTypedArray()
        )
            .flatMap { it.java.declaredFields.toSet() }
    }
}*/
