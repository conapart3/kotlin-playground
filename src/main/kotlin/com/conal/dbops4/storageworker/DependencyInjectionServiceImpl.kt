package com.conal.dbops4.storageworker

import com.conal.corda.CordaInject
import com.conal.dbops4.DbOps
import com.google.common.collect.MutableClassToInstanceMap
import java.lang.reflect.Field
import kotlin.reflect.KClass
import kotlin.reflect.full.allSuperclasses

// simulated diServiceImpl for testing
class DependencyInjectionServiceImpl  {
    val singletonServices = MutableClassToInstanceMap.create<Any>()

    private fun getSingletonService(injectableInterface: KClass<*>): Any? = singletonServices.getInstance(injectableInterface.java)

    fun injectDependenciesIntoDbOps(dbOpsList: List<DbOps>) {
        dbOpsList.forEach { dbOps ->
            dbOps::class.getFieldsForInjection()
                .forEach { field ->
                    field.isAccessible = true
                    field.set(dbOps, getSingletonService(field.type.kotlin))
                }
        }
    }

    private fun KClass<*>.getFieldsForInjection(): Collection<Field> {
        return setOf(
            this,
            *this.allSuperclasses.toTypedArray()
        )
            .flatMap { it.java.declaredFields.toSet() }
            .filter {
                it.isAnnotationPresent(CordaInject::class.java)
            }
    }
}