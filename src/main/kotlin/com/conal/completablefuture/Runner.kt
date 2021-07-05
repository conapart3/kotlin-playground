package com.conal.completablefuture

import com.conal.jackson.ContractState
import com.conal.jackson.MessageState
import java.util.concurrent.CompletableFuture

class Runner {
    fun findContractStates() : CompletableFuture<String> {
        return CompletableFuture.supplyAsync {
            Thread.sleep(100)
            "Hello"
        }
    }
}

fun main() {
    val findContractStates = Runner().findContractStates()
    while(!findContractStates.isDone){
        println(findContractStates.getNow("not there"))
        println(findContractStates.getNow("not there"))
    }

    println("string"::class.java.simpleName)
    println("string"::class.java.name)

    println(MessageState::class.java.simpleName)
    println(MessageState::class.java.name)
}