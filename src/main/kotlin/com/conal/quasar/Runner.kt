package com.conal.quasar

import co.paralleluniverse.fibers.Fiber
import co.paralleluniverse.fibers.instrument.JavaAgent

fun main() {
    check(JavaAgent.isActive()) {
        "Missing the '-javaagent' JVM argument. Make sure you run the tests with the Quasar java agent attached to your JVM."
    }
    Fiber<Any>()
        .run {
            Runner().runFiber()
        }
}

class Runner {
    fun runFiber() {
        println("running in fiber")
    }
}