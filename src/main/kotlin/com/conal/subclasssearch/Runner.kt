package com.conal.subclasssearch

fun main(){
    Runner().cordaServiceSubClassesSearch<CordaService>(MyOuterCs::class.java, arrayListOf())
}

class Runner {

    fun <T : CordaService> cordaServiceSubClassesSearch(clazz : Class<*>, classes : ArrayList<Class<T>>) : Boolean {

        // If this class is CordaService then return true.
        if (clazz == CordaService::class.java) {
            return true
        }

        // Otherwise, look for interfaces that inherit from CordaService.
        val interfaces = clazz.interfaces
        var cs = false
        for (intf in interfaces) {
            if (cordaServiceSubClassesSearch(intf, classes)) {
                // Do not add CordaService class.
                if (intf != CordaService::class.java) {
                    @Suppress("UNCHECKED_CAST")
                    classes.add(intf as Class<T>)
                }
                cs = true
            }
        }

        // Check the superclass as well. But do not add as is not an interface.
        // We no longer allow implementations directly inheriting from CordaService.
        val superclazz = clazz.superclass
        if (superclazz != null) {
            if (cordaServiceSubClassesSearch(superclazz, classes)) {
                cs = true
            }
        }
        return cs
    }

}

class MyOuterCs : MyBaseCs()

open class MyBaseCs : CordaService

interface CordaService