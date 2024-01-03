//package executor.withtaskobject
//
//import executor.CordaConsumerRecord
//import executor.SessionEvent
//import executor.processRecord
//import executor.processRecords
//import java.util.UUID
//import java.util.concurrent.Callable
//import java.util.concurrent.ExecutorService
//import java.util.concurrent.Executors
//import java.util.concurrent.Future
//import kotlin.random.Random
//
//data class CordaTask<T, R> (
//    val data: T,
//    val work: (T) -> R
//)
//
//data class CordaSubmittedTask<R>(
//    val id: String,
//    val result: Future<R>
//)
//
//interface CordaTaskManager {
//    fun <T, R> submit(input: CordaTask<T, R>): CordaSubmittedTask<R>
//
//    //todo remove
//    fun shutdown()
//}
//
//class CordaTaskManagerImpl(
//    private val numThreads: Int = 8,
//    private val name: String = UUID.randomUUID().toString(),
//    private val executorService: ExecutorService = Executors.newScheduledThreadPool(
//        numThreads,
////        ThreadFactoryBuilder().setUncaughtExceptionHandler(::handleUncaughtException).setNameFormat("$name-%d").setDaemon(true).build()
//    )
//) : CordaTaskManager {
//
//    override fun <T, R> submit(input: CordaTask<T, R>): CordaSubmittedTask<R> {
//        return CordaSubmittedTask(
//            input.id,
//            executorService.submit(Callable {
//                input.work(input.data)
//            })
//        )
//    }
//
//    override fun shutdown() {
//        executorService.shutdown()
//    }
//}
//
//interface AllocationStrategy {
//    fun <K, T> allocate(inputs: List<T>, groupingStrategy: (T) -> K): Map<K, List<T>>
//}
//
//class AllocationStrategyImpl : AllocationStrategy {
//    override fun <K, T> allocate(inputs: List<T>, groupingStrategy: (T) -> K): Map<K, List<T>> {
//        return inputs.groupBy(groupingStrategy)
//    }
//}
//
//fun main() {
//
//    val records = (0 until 100).map {
//        val rand = Random.nextInt(5)
//        CordaConsumerRecord("flow.session.data", rand, rand.toLong(), "flow$rand", SessionEvent("checkpoint_${UUID.randomUUID()}"))
//    }
//
//    val taskManager: CordaTaskManager = CordaTaskManagerImpl(8)
//    val allocationStrategy: AllocationStrategy = AllocationStrategyImpl()
//
//    val groupedRecords = allocationStrategy.allocate(records) { it.key }
//
//    val submittedTasks = groupedRecords.entries.map {
//        taskManager.submit(CordaTask(it.key, it.value, work = ::processRecords))
//    }
//
//    val results = submittedTasks.forEach { it.result.get() }
//
//    println(results)
//
//    taskManager.shutdown()
//}
