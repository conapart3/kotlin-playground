//package executor.withscheduling
//
//import executor.CordaConsumerRecord
//import executor.SessionEvent
//import executor.processRecord
//import java.util.concurrent.Callable
//import java.util.concurrent.ExecutorService
//import java.util.concurrent.Executors
//import java.util.concurrent.Future
//import java.util.concurrent.ScheduledFuture
//import java.util.concurrent.TimeUnit
//
//interface TaskManager {
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
//    /**
//     * Submit a batch of records to be processed as input to the work function.
//     *
//     * The grouping key selector function [groupingKeySelector] will be used to select a key on which to group inputs. The
//     * function will guarantee inputs with the same grouping key will be executed sequentially.
//     *
//     * As [work] will be executed in parallel across many threads, it must not mutate any shared state.
//     */
//    fun <T, K, R> submit(inputs: List<T>, work: (T) -> R, groupingKeySelector: (List<T>) -> Map<K, List<T>>): List<Future<R>>
//
//    /**
//     * Schedule a task with the underlying executor, that will become available for execution after the given delay.
//     *
//     * @param name the name of the scheduled task
//     * @param command the command to be executed
//     * @param delay the time from now to delay execution
//     * @param unit the time unit of the delay parameter
//     * @return a [ScheduledFuture] representing pending completion of the task and whose get() method will return null upon completion.
//     * @throws RejectedExecutionException - if the task cannot be scheduled for execution
//     * @throws NullPointerException - if command or unit is null
//     */
//    fun <R> schedule(name: String, command: Callable<R>, delay: Long, unit: TimeUnit): ScheduledFuture<R>
//
//    fun submit()
//
//    //todo remove
//    fun shutdown()
//}
//
//class TaskManagerImpl(
//    private val numThreads: Int = 8,
//    private val executorService: ExecutorService = Executors.newScheduledThreadPool(
//        numThreads,
////        ThreadFactoryBuilder()
////            .setUncaughtExceptionHandler(::handleUncaughtException)
////            .setNameFormat("lifecycle-coordinator-%d")
////            .setDaemon(true)
////            .build()
//        )
//) : TaskManager {
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
//
//        fun workGroup(group: List<T>): Callable<List<R>> {
//            return group.map { work(it) }
//        }
//
//        return inputs.entries.map { (key, list) ->
//
//
//            executorService.submit(Callable {
//                ::workGroup(list)
//            })
//        }
//    }
//
//    override fun <T, K, R> submit(inputs: List<T>, work: (T) -> R, groupingKeySelector: (List<T>) -> Map<K, List<T>>): List<Future<R>> {
//        TODO("Not yet implemented")
//    }
//
//    override fun submit() {
//        TODO("Not yet implemented")
//    }
//
//    override fun <R> schedule(name: String, command: Callable<R>, delay: Long, unit: TimeUnit): ScheduledFuture<R> {
//        TODO("Not yet implemented")
//    }
//
//    override fun shutdown() {
//        TODO("Not yet implemented")
//    }
//}
//
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
//    )
//
//    val taskManager: TaskManager = TaskManagerImpl(8)
//
//    // Without preserving order
//    val futures = taskManager.submit(records, ::processRecord)
//
//    // Wait for the futures to complete and retrieve the results
//    val results = futures.map { future -> future.get() }
//
//    // Process the results as needed
//    results.forEach { result ->
//        println(result)
//    }
//
//    // With preserving order using a sequentialKeySelector
//    val orderedKeySelector: (CordaConsumerRecord<String, SessionEvent>) -> String = { record -> record.key }
//    val orderedFutures = taskManager.submit(records, ::processRecord) { inputs ->
//        inputs.groupBy { it.key }
//    }
//
//    // Wait for the futures to complete and retrieve the results
//    val orderedResults = orderedFutures.map { future -> future.get() }
//
//    // Process the results as needed, now preserving the order based on the selected key
//    orderedResults.forEach { result ->
//        println(result)
//    }
//
//    taskManager.shutdown()
//}
