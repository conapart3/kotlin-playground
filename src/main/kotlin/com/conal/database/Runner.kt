package com.conal.database

import com.conal.corda.NamedQueryFilter
import com.conal.main.ContractState

fun main() {

    val s = SetBasedFilter("sre")

    fun printSomething(something: Any?) {
        println("postFilter ${something?.toString()}")
    }

    printSomething(null)

    println(setOf(ContractState::class.java.name) == setOf(ContractState::class.java.name))

    Runner().doSomething(SetBasedFilter(""))
    Runner().doSomething(FileBasedFilter(""))

    val res = Runner().find(Any::class.java, Any())

    println(res)
}

data class SetBasedFilter(val set: String) : NamedQueryFilter
data class FileBasedFilter(val file: String) : NamedQueryFilter

class Runner {
    fun doSomething(filter: NamedQueryFilter) {
        print(filter.javaClass)
    }

    fun <T : Any> find(entityClass: Class<T>, primaryKey: Any): T? {

        var someVar: Any? = null
        val rand = Math.random()
        if(rand > 0.5) {
            someVar = Any()
        }

        @Suppress("UNCHECKED_CAST")
        return someVar as? T
    }
}
