package streams

import java.util.stream.Stream

fun main() {

    val str = doItAll().filter { it > 2 }

    str.use {
        it.forEach { i ->
            println("foreach $i")
        }
    }
}

fun doItAll(): Stream<Int> {
    return context().map { context ->
        doGetAllVersionedRecords(context).onClose {
            println("$context closing")
        }
    }.flatMap { i -> i }
}

fun context() = listOf("a", "b", "c").stream()

fun doGetAllVersionedRecords(context: String) = listOf(1, 2, 3).stream() .onClose { println("intStream closing") }