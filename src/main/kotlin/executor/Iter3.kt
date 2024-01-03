//package executor
//
//import java.util.concurrent.*
//
////interface ThreadPoolManager {
////    fun <T, R> execute(items: List<List<T>>, work: (T) -> R): List<Future<R>>
////}
////
////interface TaskManagerW<T, R> {
////    val threadPoolManager: ThreadPoolManager
////    fun submit(records: List<T>, work: (T) -> R): List<Future<R>>
////}
////
////class FlowEventTaskManager<K, V>(override val threadPoolManager: ThreadPoolManager) : TaskManagerW<CordaConsumerRecord<K, V>, CordaConsumerRecord<K, V>> {
////    override fun submit(
////        records: List<CordaConsumerRecord<K, V>>,
////        work: (CordaConsumerRecord<K, V>) -> CordaConsumerRecord<K, V>
////    ): List<Future<CordaConsumerRecord<K, V>>> {
////        // perform grouping of records by K
////        // send to threadPoolManager to execute a list of items
////    }
////}
//
//interface TaskManagerz {
//    /**
//     * Submit a batch of [inputs] to be processed by the [work] function.
//     *
//     * There are no guarantees about the order these records will be processed.
//     *
//     * As [work] will be executed in parallel across many threads, it must not mutate any shared state.
//     */
//    fun <T, R> submit(inputs: List<T>, work: (T) -> R): List<Future<R>>
//
//    /**
//     * Submit a batch of grouped [inputs] to be processed by the [work] function.
//     *
//     * Given a map of [inputs], for a given key the list of inputs associated with that key are guaranteed to be executed sequentially.
//     *
//     * For example, given the map:
//     *
//     * ```kotlin
//     * [ "k1" -> [1, 7, 8], "k2" -> [4, 5, 6], "k3" -> [2], "k4" -> [3] ]
//     * ```
//     *
//     * This function guarantees [1] will be processed before [7], [4] will be processed before [5], etc. There is no guarantee about the
//     * order of execution between keys, [2] may be processed before [4], [7] may be processed before [4], etc.
//     *
//     * As [work] will be executed in parallel across many threads, it must not mutate any shared state.
//     */
//    fun <K, T, R> submit(inputs: Map<K, List<T>>, work: (T) -> R): List<Future<R>>
//
//
//    //todo remove
//    fun shutdown()
//}
//
//class TaskManagerzImpl(private val numThreads: Int) : TaskManagerz {
//
//    private val executorService: ExecutorService = Executors.newFixedThreadPool(numThreads)
//
//    override fun <T, R> submit(inputs: List<T>, work: (T) -> R): List<Future<R>> {
//        val chunkSize = (inputs.size + numThreads - 1) / numThreads
//        val groupedEvents = inputs.chunked(chunkSize)
//        return groupedEvents.map  {
//            executorService.submit(Callable {
//                work(it)
//            })
//        }
//    }
//
//    override fun <K, T, R> submit(inputs: Map<K, List<T>>, work: (T) -> R): List<Future<R>> {
//        // distribution of keys could be spread more evenly across threads. Current implementation:
//        // k1 -> 1,2 k2 -> 3,4,
//        return inputs.keys.map { key ->
//            executorService.submit(Callable {
//                work(inputs[key]!!)
//            })
//        }
//    }
//
//    // TODO remove from the API
//    override fun shutdown() {
//        executorService.shutdown()
//    }
//}
//
//fun processRecords(records: List<CordaConsumerRecord<String, SessionEvent>>): List<String> {
//    val result = mutableListOf<String>()
//    records.forEach {
//        println("[${Thread.currentThread().name}] Start processing record with key: ${it.key}, value: ${it.value}")
//        Thread.sleep(1000) // Simulates some asynchronous processing
//        result.add("Processed record with key: ${it.key}, value: ${it.value}")
//    }
//    return result
//}
//
//fun main() {
//    val records: List<CordaConsumerRecord<String, SessionEvent>> = listOf(
//        CordaConsumerRecord("flow.session.data", 0, 0, "flow1", SessionEvent("checkpoint1")),
//        CordaConsumerRecord("flow.session.data", 0, 0, "flow2", SessionEvent("checkpoint2")),
//        CordaConsumerRecord("flow.session.data", 1, 0, "flow3", SessionEvent("checkpoint3")),
//        CordaConsumerRecord("flow.session.data", 1, 0, "flow4", SessionEvent("checkpoint4")),
//        CordaConsumerRecord("flow.session.data", 0, 0, "flow1", SessionEvent("checkpoint1_2")),
//        CordaConsumerRecord("flow.session.data", 2, 0, "flow5", SessionEvent("checkpoint5")),
//        CordaConsumerRecord("flow.session.data", 2, 0, "flow6", SessionEvent("checkpoint6")),
//        CordaConsumerRecord("flow.session.data", 2, 0, "flow6", SessionEvent("checkpoint6_2")),
//        CordaConsumerRecord("flow.session.data", 2, 0, "flow6", SessionEvent("checkpoint6_3")),
//        CordaConsumerRecord("flow.session.data", 2, 0, "flow6", SessionEvent("checkpoint6_4")),
//        CordaConsumerRecord("flow.session.data", 2, 0, "flow6", SessionEvent("checkpoint6_5")),
//    )
//
//    val taskManager: TaskManagerz = TaskManagerzImpl(8)
////
////    // Without preserving order
////    val futures = taskManager.submit(records.groupBy { it.key }, ::processRecords)
////
////    // Wait for the futures to complete and retrieve the results
////    val results = futures.map { future -> future.get() }
////
////    // Process the results as needed
////    results.forEach { result ->
////        println(result)
////    }
//
//    println("====================== no mapping ========================")
//
//    val futuresNoMap = taskManager.submit(records, ::processRecords)
//    val resultsNoMap = futuresNoMap.map { future -> future.get() }
//
//    // Process the results as needed
//    resultsNoMap.forEach { result ->
//        println(result)
//    }
//
//    taskManager.shutdown()
//}
