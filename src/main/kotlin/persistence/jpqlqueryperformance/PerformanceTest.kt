package persistence.jpqlqueryperformance

import kotlin.system.measureTimeMillis

/**
 * Execute the [block] in a performance test for a set number of [iterations].
 *
 * The block should return a time in milliseconds.
 *
 * @return average time, fastest time, slowest time
 */
fun performanceTest(iterations: Int = 1, warmupIterations: Int = 0, message: String = "Perf test", block: () -> Unit): Triple<Long, Long, Long> {
    assert(iterations > 0){
        "Iterations should be greater than 0"
    }
    if(warmupIterations > 0) {
        println("Warming up $warmupIterations times.")
        warmup(warmupIterations, block)
    }
    val times = mutableListOf<Long>()
    var count = 0
    println("Starting actual test loop.")
    while(count < iterations) {
        val timeInMillis = measureTimeMillis {
            block.invoke()
        }
        times += timeInMillis
        count++
    }
    val av = times.average().toLong()
    val fastest = times.minOrNull()!!
    val slowest = times.maxOrNull()!!
    println("$message: Avg: $av, Fastest: $fastest, Slowest: $slowest.")
    return Triple(av, fastest, slowest)
}

fun warmup(iterations: Int, block: () -> Unit) {
    for (i in 1 until iterations) {
        block.invoke()
    }
}

fun <T : Any> withPerformanceTest(message: String, block: () -> T): T {
    val start = System.currentTimeMillis()

    val result = block.invoke()

    val timeInMillis = System.currentTimeMillis() - start
    println("$message: $timeInMillis ms.")

    return result
}