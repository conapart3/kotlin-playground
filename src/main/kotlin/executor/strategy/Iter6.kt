package executor.strategy

import executor.CordaConsumerRecord
import executor.SessionEvent
import executor.processRecord
import java.util.concurrent.Callable
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import java.util.concurrent.Future

interface TaskManager {
    fun <T, R> submit(items: List<T>, work: (T) -> R): List<Future<R>>
    fun <K, T, R> submit(items: List<T>, threadGroupingStrategy: (List<T>) -> Map<K, List<T>>, work: (T) -> R): List<Future<R>>

    fun close()//todo remove
}


fun process(record: CordaConsumerRecord<String, SessionEvent>): String {
    println("[${Thread.currentThread().name}] Start processing record with key: ${record.key}, value: ${record.value}")
    Thread.sleep(1000) // Simulates some asynchronous processing
    return "Processed record with key: ${record.key}, value: ${record.value}"
}

fun grouping(records: List<CordaConsumerRecord<String, SessionEvent>>) : Map<String, List<CordaConsumerRecord<String, SessionEvent>>> {
    return records.groupBy { it.key }
}

fun main() {
    val records: List<CordaConsumerRecord<String, SessionEvent>> = listOf(
        CordaConsumerRecord("flow.session.data", 0, 0, "flow1", SessionEvent("checkpoint1")),
        CordaConsumerRecord("flow.session.data", 0, 0, "flow2", SessionEvent("checkpoint2")),
        CordaConsumerRecord("flow.session.data", 1, 0, "flow3", SessionEvent("checkpoint3")),
        CordaConsumerRecord("flow.session.data", 1, 0, "flow4", SessionEvent("checkpoint4")),
        CordaConsumerRecord("flow.session.data", 0, 0, "flow1", SessionEvent("checkpoint1_2")),
        CordaConsumerRecord("flow.session.data", 2, 0, "flow5", SessionEvent("checkpoint5")),
        CordaConsumerRecord("flow.session.data", 2, 0, "flow6", SessionEvent("checkpoint6")),
    )

    /*val taskManager: TaskManager = TaskManagerImpl(8)

    // Without preserving order
    val futures = taskManager.submit(records, ::processRecord)

    // Wait for the futures to complete and retrieve the results
    val results = futures.map { future -> future.get() }

    // Process the results as needed
    results.forEach { result ->
        println(result)
    }

    // With preserving order using a sequentialKeySelector
//    val orderedKeySelector: (List<CordaConsumerRecord<String, SessionEvent>>) -> Map<String, List<CordaConsumerRecord<String, SessionEvent>>>
//            = { record -> record.key }
    val orderedFutures = taskManager.submit(records, ::grouping, ::process)

    // Wait for the futures to complete and retrieve the results
    val orderedResults = orderedFutures.map { future -> future.get() }

    // Process the results as needed, now preserving the order based on the selected key
    orderedResults.forEach { result ->
        println(result)
    }

    taskManager.close()*/
}