package com.conal

import com.conal.main.ContractState
import com.conal.main.VaultQueryPostProcessor

fun main(){
//    ReflectionTester().findName()
    ReflectionTester().somFunc()
}

class ReflectionTester {
    fun findName(){
        val className1 = "com.conal.MyImpl"
        val className2 = "com.conal.MyImpl2"

        val c = Class.forName(className1)
        val declaredField = c.getDeclaredField("name")
        val value = declaredField.get(null)
        println("done")
    }

    fun somFunc() {
        val obj = object : VaultQueryPostProcessor<ContractState, Any> {
            override fun postProcess(inputs: Sequence<ContractState>): Sequence<Any> {
                return listOf(Any()).asSequence()
            }

        }
        println(obj)
    }
}



interface Base{
    val name: String;
}

class MyImpl : Base {
    override val name = "myName"
}
class MyImpl2 : Base {
    override val name: String
        get() = "myName2"
}