package threads

import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

fun main() {
    println("Start: ${Thread.currentThread().name}")

    val first = Thread {
        (0..100).map {
            println("First $it: ${Thread.currentThread().name}")
        }
    }
    val second = Thread {
        (0..100).map {
            println("Second $it: ${Thread.currentThread().name}")
        }
    }

    val executorService = Executors.newFixedThreadPool(2)
    val firstFuture = executorService.submit(first)
    val secondFuture = executorService.submit(second)

    firstFuture.get()
    secondFuture.get()
    executorService.shutdown()

    println("End: ${Thread.currentThread().name}")
}
