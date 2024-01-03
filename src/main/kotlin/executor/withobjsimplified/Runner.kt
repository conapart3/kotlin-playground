package executor.withobjsimplified

import executor.CordaConsumerRecord
import executor.SessionEvent
import executor.processRecord
import executor.processRecords
import java.util.UUID
import java.util.concurrent.Callable
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import java.util.concurrent.Future
import kotlin.random.Random

interface TaskManager {
    fun <T, R> submit(data: T, work: (T) -> R): Future<R>
}

class CordaTaskManagerImpl(
    private val numThreads: Int = 8,
    private val name: String = UUID.randomUUID().toString(),// for thread logging and metric tags
    private val executorService: ExecutorService = Executors.newScheduledThreadPool(
        numThreads,
//        ThreadFactoryBuilder().setUncaughtExceptionHandler(::handleUncaughtException).setNameFormat("$name-%d").setDaemon(true).build()
    )
) : TaskManager {

    override fun <T, R> submit(data: T, work: (T) -> R): Future<R> {
        return executorService.submit(Callable {
            work(data)
        })
    }

    fun shutdown() {
        executorService.shutdown()
    }
}

interface AllocationStrategy {
    fun <K, T> allocate(inputs: List<T>, groupingStrategy: (T) -> K): Map<K, List<T>>
}

class AllocationStrategyImpl : AllocationStrategy {
    override fun <K, T> allocate(inputs: List<T>, groupingStrategy: (T) -> K): Map<K, List<T>> {
        return inputs.groupBy(groupingStrategy)
    }
}

fun main() {

    val records = (0 until 100).map {
        val rand = Random.nextInt(5)
        CordaConsumerRecord("flow.session.data", rand, rand.toLong(), "flow$rand", SessionEvent("checkpoint_${UUID.randomUUID()}"))
    }

    val taskManager = CordaTaskManagerImpl(8)
    val allocationStrategy: AllocationStrategy = AllocationStrategyImpl()

    val groupedRecords = allocationStrategy.allocate(records) { it.key }

    val submittedTasks = groupedRecords.entries.map {
        taskManager.submit(it.value, ::processRecords)
    }

    val results = submittedTasks.forEach { it.get() }

    println(results)

    taskManager.shutdown()
}
