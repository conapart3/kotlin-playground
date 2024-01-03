package executor

import java.util.concurrent.*

data class CordaConsumerRecord<K, V>(
    val topic: String,
    val partition: Int,
    val offset: Long,
    val key: K,
    val value: V?,
    val headers: List<Pair<String, String>> = listOf()
)

data class SessionEvent(
    val payload: String
)

// pipelining POC sped up x3
interface TaskManagerA {
    fun <T, R> submit(records: List<T>, work: (T) -> R): List<Future<R>>
    fun <T, K, R> submitAndPreserveOrder(records: List<T>, orderedKeySelector: (T) -> K, work: (T) -> R): List<Future<R>>
    // todo remove from this API
    fun shutdown()
}

class TaskManagerAImpl(numThreads: Int) : TaskManagerA {
    private val executorService: ExecutorService = Executors.newFixedThreadPool(numThreads)

    override fun <T, R> submit(records: List<T>, work: (T) -> R): List<Future<R>> {
        return records.map { record ->
            executorService.submit(Callable {
                work(record)
            })
        }
    }

    override fun <T, K, R> submitAndPreserveOrder(
        records: List<T>,
        orderedKeySelector: (T) -> K,
        work: (T) -> R
    ): List<Future<R>> {
        val groupedRecords: Map<K, List<T>> = records.groupBy { record -> orderedKeySelector(record) }
        return groupedRecords.flatMap { (_, groupedRecordsList) ->
            groupedRecordsList.map { record ->
                executorService.submit(Callable {
                    work(record)
                })
            }
        }
    }

    // TODO remove from the API
    override fun shutdown() {
        executorService.shutdown()
    }
}

// Example usage
fun processRecord(record: CordaConsumerRecord<String, SessionEvent>): String {
    println("[${Thread.currentThread().name}] Start processing record with key: ${record.key}, value: ${record.value}")
    Thread.sleep(1000) // Simulates some asynchronous processing
    return "Processed record with key: ${record.key}, value: ${record.value}"
}

// Example usage
fun processRecords(records: List<CordaConsumerRecord<String, SessionEvent>>): List<String> {
    return records.map { record ->
        println("[${Thread.currentThread().name}] Start processing record with key: ${record.key}, value: ${record.value}")
        Thread.sleep(1000) // Simulates some asynchronous processing
        "Processed record with key: ${record.key}, value: ${record.value}"
    }
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

    val taskManager: TaskManagerA = TaskManagerAImpl(8)

    // Without preserving order
    val futures = taskManager.submit(records, ::processRecord)

    // Wait for the futures to complete and retrieve the results
    val results = futures.map { future -> future.get() }

    // Process the results as needed
    results.forEach { result ->
        println(result)
    }

    // With preserving order using a sequentialKeySelector
    val orderedKeySelector: (CordaConsumerRecord<String, SessionEvent>) -> String = { record -> record.key }
    val orderedFutures = taskManager.submitAndPreserveOrder(records, orderedKeySelector, ::processRecord)

    // Wait for the futures to complete and retrieve the results
    val orderedResults = orderedFutures.map { future -> future.get() }

    // Process the results as needed, now preserving the order based on the selected key
    orderedResults.forEach { result ->
        println(result)
    }

    taskManager.shutdown()
}
