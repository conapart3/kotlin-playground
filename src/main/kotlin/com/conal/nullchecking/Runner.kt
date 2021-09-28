package com.conal.nullchecking

import com.conal.corda.PetState
import com.conal.jackson.MessageState
import com.conal.main.ContractState

fun main() {
    println(Runner().containsType(PetState::class.java, StateStatus.UNCONSUMED))
}

enum class StateStatus {
    CONSUMED, UNCONSUMED
}

class Runner {
    private val produced = listOf<Class<*>>(PetState::class.java)
    private val consumed = listOf<Class<*>>(MessageState::class.java)

    fun <T : ContractState> containsType(clazz: Class<T>, status: StateStatus?) =
        when (status) {
            StateStatus.UNCONSUMED -> produced.any { clazz.isAssignableFrom(it) }
            StateStatus.CONSUMED -> consumed.any { clazz.isAssignableFrom(it) }
            else -> consumed.any { clazz.isAssignableFrom(it) }
                    || produced.any { clazz.isAssignableFrom(it) }
        }
}