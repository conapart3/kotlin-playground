package com.conal.asyncdbops2.impl

import com.conal.corda.CordaInject
import com.conal.corda.Flow
import com.conal.dbops4.DbOps
import com.google.common.collect.MutableClassToInstanceMap
import java.lang.reflect.Field
import kotlin.reflect.KClass
import kotlin.reflect.full.allSuperclasses

// note cs - this isn't needed
//  - just simulates diServiceImpl for testing injection of asyncDbOpsStarter into flow
class DependencyInjectionServiceImpl  {
    val singletonServices = MutableClassToInstanceMap.create<Any>()

    fun getSingletonService(injectableInterface: KClass<*>): Any? = singletonServices.getInstance(injectableInterface.java)

    /**
     * Inject instances of all [CordaInject] annotated properties for the FlowLogic instance.
     * The list of allowed interfaces to be injected is controlled by [DependencyInjectionService].
     */
    fun injectDependencies(flow: Flow<*>) {
        flow::class.getFieldsForInjection()
            .forEach { field ->
                field.isAccessible = true
                field.set(flow, getSingletonService(field.type.kotlin))
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