@file:JvmName("Client")

package com.conal.compatibility.gqpp

import java.util.stream.Stream
import kotlin.streams.toList

fun main() {
    val ints = ClientPostProcessor().postProcess(Stream.of("a", "bc", "def"))
    println(ints.toList())
}