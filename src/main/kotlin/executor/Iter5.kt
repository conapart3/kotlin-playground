import java.util.concurrent.Future

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
interface TaskManager {
    /**
     * Submit a batch of [inputs] to be processed by the [work] function.
     *
     * There are no guarantees about the order these records will be processed.
     *
     * As [work] will be executed in parallel across many threads, it must not mutate any shared state.
     */
    fun <T, R> submit(inputs: List<T>, work: (T) -> R): List<Future<R>>

    /**
     * Submit a batch of grouped [inputs] to be processed by the [work] function.
     *
     * Given a map of [inputs], for a given key the list of inputs associated with that key are guaranteed to be executed sequentially.
     *
     * For example, given the map:
     *
     * ```kotlin
     * [ "k1" -> [1, 7, 8], "k2" -> [4, 5, 6], "k3" -> [2], "k4" -> [3] ]
     * ```
     *
     * This function guarantees [1] will be processed before [7], [4] will be processed before [5], etc. There is no guarantee about the
     * order of execution between keys, [2] may be processed before [4], [7] may be processed before [4], etc.
     *
     * As [work] will be executed in parallel across many threads, it must not mutate any shared state.
     */
    fun <K, T, R> submit(inputs: Map<K, List<T>>, work: (T) -> R): List<Future<R>>

    /**
     * Submit a batch of records to be processed as input to the work function.
     *
     * The grouping key selector function [sequentialGroupingKeySelector] will be used to select a key on which to group inputs. The
     * function will guarantee inputs with the same grouping key will be executed sequentially.
     *
     * As [work] will be executed in parallel across many threads, it must not mutate any shared state.
     */
    fun <T, K, R> submit(inputs: List<T>, sequentialGroupingKeySelector: (T) -> K, work: (T) -> R): List<Future<R>>

    //todo remove
    fun shutdown()
}
//
//class TaskManagerLockBased(private val numThreads: Int) : TaskManager {
//
//    private val executorService: ExecutorService = Executors.newFixedThreadPool(numThreads)
//
//    override fun <T, R> submit(inputs: List<T>, work: (T) -> R): List<Future<R>> {
//        return inputs.map  {
//            executorService.submit(Callable {
//                work(it)
//            })
//        }
//    }
//
//    override fun <K, T, R> submit(inputs: Map<K, List<T>>, work: (T) -> R): List<Future<R>> {
//        // distribution of keys could be spread more evenly across threads. Current implementation:
//        // k1 -> 1,2 k2 -> 3,4,
//
//        val keyLock: ConcurrentHashMap<K, Any> = ConcurrentHashMap(inputs.keys.size)
//
//
//
//        return inputs.keys.map { key ->
//            executorService.submit(Callable {
//                work(inputs[key]!!)
//            })
//        }
//    }
//
//    override fun <T, K, R> submit(inputs: List<T>, sequentialGroupingKeySelector: (T) -> K, work: (T) -> R): List<Future<R>> {
////        val groupedInputs: Map<K, List<T>> = inputs.groupBy(sequentialGroupingKeySelector)
//
//        val keyLock: ConcurrentHashMap<K, Any> = ConcurrentHashMap(inputs.size)
//
//        inputs.map { item ->
//            executorService.submit {
//                val key = sequentialGroupingKeySelector(item)
//                keyLock.computeIfAbsent(key) { Any() }
//            }
//        }
//
//    }
//
//    // TODO remove from the API
//    override fun shutdown() {
//        executorService.shutdown()
//    }
//}
//
//interface TaskManagerQ {
//    /**
//     * Submit an [input] to be processed by the [work] function.
//     *
//     * There are no guarantees about what thread will execute work for this input.
//     *
//     * As [work] will be executed in parallel across many threads, it must not mutate any shared state.
//     */
//    fun <T, R> submit(input: T, work: (T) -> R): Future<R>
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
//     * As [work] will be executed in parallel across many threads, it must not mutate any shared state.     *
//     *
//     * The input to the [work] function is a list of [T]. This function guarantees inputs with the same key will be in this list.
//     */
//    fun <K, T, R> submit(inputs: Map<K, List<T>>, work: (List<T>) -> R): List<Future<R>>
//
//    /**
//     * Submit a batch of records to be processed as input to the work function.
//     *
//     * The grouping key selector function [groupingStrategy] will be used to select a key on which to group inputs. The
//     * function will guarantee inputs with the same grouping key will be executed sequentially.
//     *
//     * As [work] will be executed in parallel across many threads, it must not mutate any shared state.
//     *
//     * The input to the [work] function is a list of [T]. This function guarantees inputs with the same key will be in this list.
//     */
//    fun <T, K, R> submit(inputs: List<T>, groupingStrategy: (T) -> K, work: (List<T>) -> R): List<Future<R>>
//
//    //todo remove
//    fun shutdown()
//}
//
//class TaskManagerQueueBased(private val numThreads: Int) : TaskManagerQ {
//
//    private val executorService: ExecutorService = Executors.newFixedThreadPool(numThreads)
//
//    override fun <T, R> submit(inputs: List<T>, work: (T) -> R): List<Future<R>> {
//        return inputs.map  {
//            executorService.submit(Callable {
//                work(it)
//            })
//        }
//    }
//
//    // this approach means our "work" function takes a list of inputs, and lets the caller potentially share any states
//    // that have changed between runs for the same flowId.
//    override fun <K, T, R> submit(inputs: Map<K, List<T>>, work: (List<T>) -> R): List<Future<R>> {
//        /*return inputs.map { (_, inputs) ->
//            executorService.submit(Callable {
//                work(inputs)
//            })
//        }*/
//        // option 1, divide all inputs across all threads
//        val inputsPerThread = ConcurrentHashMap<Int, MutableList<T>>()
//        for ((key, values) in inputs) {
//            val hash = key.hashCode()
//            val threadAssignment = (hash * 13) % numThreads
//            inputsPerThread.computeIfAbsent(threadAssignment) { mutableListOf() }.addAll(values)
//        }
//        return inputsPerThread.keys.map { key ->
//            executorService.submit(Callable {
//                work(inputsPerThread[key]!!)
//            })
//        }
//
//        // option 2, just submit lists where all elements in the list are of the same key
//
//
//    }
//
//    override fun <T, K, R> submit(inputs: List<T>, groupingStrategy: (T) -> K, work: (List<T>) -> R): List<Future<R>> {
//        return submit(inputs.groupBy(groupingStrategy), work)
//    }
//
//    // TODO remove from the API
//    override fun shutdown() {
//        executorService.shutdown()
//    }
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
//    val taskManager: TaskManager = TaskManagerLockBased(8)
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
