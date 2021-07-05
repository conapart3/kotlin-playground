package com.conal.dbops2

import com.conal.corda.Flow
import com.conal.corda.uncheckedCast
import java.lang.reflect.Field
import java.util.Collections.synchronizedMap
import kotlin.reflect.KClass
import kotlin.reflect.full.allSuperclasses

class DbOpsDiContainer {
    // (originalClassType to (original instance to proxy pair))
    private val dbOpsMap: MutableMap<Class<out DbOps>, DbOpsImplWithProxy<out DbOps>> = synchronizedMap(mutableMapOf())

    fun registerDbOps(dbOpsImplWithProxies: List<DbOpsImplWithProxy<out DbOps>>){
        dbOpsImplWithProxies.forEach {
            registerDbOps(it)
        }
    }

    fun registerDbOps(dbOpsImplWithProxy: DbOpsImplWithProxy<out DbOps>){
        if(dbOpsMap.containsKey(dbOpsImplWithProxy.dbOps.javaClass)) {
            throw DbOpsException("Registration of dbOps failed because implementation already exists.")
        }
        dbOpsMap[dbOpsImplWithProxy.dbOps.javaClass] = dbOpsImplWithProxy
    }

    /**
     * Inject instances of all [DbOpsInject] annotated properties for the FlowLogic instance.
     * The list of allowed interfaces to be injected is controlled by [DbOpsDiContainer].
     */
    fun injectDbOpsProxies(flow: Flow<*>) {
        flow::class.getFieldsForDbOpsInjection()
            .forEach { field ->
                field.isAccessible = true
                if(field.get(flow) == null){
                    val proxy = getProxy<DbOps>(field.type)
                    field.set(flow, proxy)
                }
            }
    }

    private fun <T : DbOps> getProxy(type: Class<*>): T {
        if(type.isInterface){
            println("DbOps DI injection $type is interface but should be implementation.")
            // probably don't want to throw an exception just skip the dbOps that couldn't be injected?
            throw DbOpsException("DbOps DI injection $type is interface but should be implementation.")
        }
        if(!DbOps::class.java.isAssignableFrom(type)){
            // probably don't want to throw an exception just skip the dbOps that couldn't be injected?
            println("DbOps DI injection $type does not implement DbOps.")
            throw DbOpsException("DbOps DI injection $type does not implement DbOps.")
        }
        val implWithProxy = dbOpsMap[type]
        if (implWithProxy == null) {
            // probably don't want to throw an exception just skip the dbOps that couldn't be injected?
            println("Attempted to inject dbOps dependency but $type is not registered.")
            throw DbOpsException("DbOps DI injection has no implementation of $type.")
        }
        return uncheckedCast(implWithProxy.proxy)
    }
}

class DbOpsException(message: String?) : RuntimeException(message)

/**
 * Get the declared fields of the current KClass, and of the superclasses of this KClass.
 * We get declared fields to include fields of all accessibility types.
 * Finally we need to filter so that only fields annotated with [CordaInject] are returned.
 */
private fun KClass<*>.getFieldsForDbOpsInjection(): Collection<Field> {
    return setOf(
        this,
        *this.allSuperclasses.toTypedArray()
    )
        .flatMap { it.java.declaredFields.toSet() }
        .filter {
            it.isAnnotationPresent(DbOpsInject::class.java)
        }
}