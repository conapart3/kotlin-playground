package futures

import java.util.concurrent.CompletableFuture

fun main() {
    val fut = CompletableFuture<String>()

    fut.whenComplete { t, u ->
        println("${Thread.currentThread().id} I'm completed, $t $u")
    }

    println("${Thread.currentThread().id} Doing some logic after when complete code")
    println("${Thread.currentThread().id} Completing the future")

    fut.complete("response")
}