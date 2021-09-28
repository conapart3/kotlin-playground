package com.conal.compatibility.gqpp

import java.util.stream.Stream

class ClientPostProcessor : StateAndRefPostProcessor<Int> {
    override val name: String
        get() = "client"

    override fun postProcess(inputs: Stream<String>): Stream<Int> {
        println("client")
        return inputs.map { it.length }
    }
}