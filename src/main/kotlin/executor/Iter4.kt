package executor

import java.util.concurrent.*

fun main() {
    val executor = KeyBasedExecutor()

    val records: List<CordaConsumerRecord<String, SessionEvent>> = listOf(
        CordaConsumerRecord("flow.session.data", 0, 0, "flow1", SessionEvent("checkpoint1")),
        CordaConsumerRecord("flow.session.data", 0, 0, "flow2", SessionEvent("checkpoint2")),
        CordaConsumerRecord("flow.session.data", 1, 0, "flow3", SessionEvent("checkpoint3")),
        CordaConsumerRecord("flow.session.data", 1, 0, "flow4", SessionEvent("checkpoint4")),
        CordaConsumerRecord("flow.session.data", 0, 0, "flow1", SessionEvent("checkpoint1_2")),
        CordaConsumerRecord("flow.session.data", 2, 0, "flow5", SessionEvent("checkpoint5")),
        CordaConsumerRecord("flow.session.data", 2, 0, "flow6", SessionEvent("checkpoint6")),
    )

    val futures = executor.submit(records.groupBy { it.key }, ::work)

    val results = futures.map { it.get() }

    results.forEach {
        println(it)
    }

    executor.shutdown()
}

fun work(record: CordaConsumerRecord<String, SessionEvent>): String {
    println("[${Thread.currentThread().name}] Start processing record with key: ${record.key}, value: ${record.value}")
    Thread.sleep(1000) // Simulates some asynchronous processing
    return "Processed record with key: ${record.key}, value: ${record.value}"
}

class KeyBasedExecutor(
    private val numThreads: Int = 8,
    private val executorService: ExecutorService = Executors.newFixedThreadPool(numThreads)
){
    fun <K, T, R> submit(inputs: Map<K, List<T>>, work: (T) -> R): List<Future<List<R>>> {
        val results = mutableListOf<Future<List<R>>>()
        val tasks = createTasks(inputs, work)
        tasks.forEach { task ->
            val future = executorService.submit(task)
            results.add(future)
        }
        return results
    }

    private fun <K, T, R> createTasks(inputs: Map<K, List<T>>, work: (T) -> R): List<Callable<List<R>>> {
        val tasks = mutableListOf<Callable<List<R>>>()
        val taskMap = mutableMapOf<K, MutableList<T>>()

        // Populate the task map with inputs grouped by key
        for ((key, value) in inputs) {
            taskMap.computeIfAbsent(key) { mutableListOf() }.addAll(value)
        }

        // Create tasks that process inputs with the same key in order
        for ((key, values) in taskMap) {
            tasks.add(Callable {
                values.map { work(it) }
            })
        }

        return tasks
    }

    fun shutdown() {
        executorService.shutdown()
    }
}
