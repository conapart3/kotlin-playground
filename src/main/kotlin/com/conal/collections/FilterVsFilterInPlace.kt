package com.conal.collections

import java.time.Duration
import kotlin.system.measureNanoTime

fun main() {
    // different list references, same instances of Processors
    val list =
        (0 until 10).map { Processor(it, it%2==0) }.toMutableList()

    val filtered = measureNanoTime {
        list.filter { it.availableForRPC }
    }
    println("filter took $filtered")

    // same list reference
    val retained = measureNanoTime {
        list.retainAll { it.availableForRPC }
    }
    println("retain took $retained")
}

class Processor(val number: Int, val availableForRPC: Boolean = true) {
}

fun <T> logElapsedTime(label: String, body: () -> T): T {
    // Use nanoTime as it's monotonic.
    val now = System.nanoTime()
    var failed = false
    try {
        return body()
    } catch (th: Throwable) {
        failed = true
        throw th
    } finally {
        val elapsed = Duration.ofNanos(System.nanoTime() - now).toMillis()
        val msg = (if (failed) "Failed " else "") + "$label took $elapsed msec"
        println(msg)
    }
}