package executor.taskobjectsimplified

import executor.CordaConsumerRecord
import executor.SessionEvent
import java.util.UUID
import java.util.concurrent.CompletableFuture
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import kotlin.math.pow
import kotlin.math.sqrt
import kotlin.random.Random
import kotlin.system.measureTimeMillis

enum class TaskType {
    FLOW, LONG_RUNNING, SCHEDULED
}

interface Task<T> {
    val id: String
    val input: T
    val type: TaskType
}

data class TaskImpl<T>(
    override val id: String,
    override val input: T,
    override val type: TaskType
) : Task<T>

/**
 * A service for managing tasks and exporting metric information about thread utilization.
 */
interface TaskManager {
    /**
     * Submit [task] to be processed by the [work] function.
     *
     * It is guaranteed one invocation to this API will execute input on one thread, for example, if the input is a list, all items in
     * the list will be executed on the same thread.
     *
     * Note that [work] should be thread safe as it could potentially be executed in parallel across many threads.
     */
    fun <T, R> submit(task: Task<T>, work: (T) -> R): CompletableFuture<R>

    // this API can be batched under the hood. but not necessary for now.
//    fun <T, R> submit(tasks: List<Task<T>>, work: (T) -> R): List<CompletableFuture<R>>
    // scheduling APIS (for later)
//    fun <K, T, R> schedule(task: Task<T>, delay: Long, unit: TimeUnit, work: (T) -> R): ScheduledFuture<R>
//    fun <K, T, R> scheduleAtFixedRate(task: Task<T>, initialDelay: Long, period: Long, unit: TimeUnit, work: (T) -> R): ScheduledFuture<R>
//    fun <T, R> submit(task: Task<T>, work: (T) -> R): Future<R>

    // todo remove off the API
    fun close()
}

class TaskManagerImpl(
    private val numThreads: Int = 8,
    private val name: String = UUID.randomUUID().toString(),// for thread logging and metric tags
    private val executorService: ExecutorService = Executors.newScheduledThreadPool(
        numThreads,
//        ThreadFactoryBuilder().setUncaughtExceptionHandler(::handleUncaughtException).setNameFormat("$name-%d").setDaemon(true).build()
    )
) : TaskManager {

    override fun <T, R> submit(task: Task<T>, work: (T) -> R): CompletableFuture<R> {
        return CompletableFuture.supplyAsync(
            {
                work(task.input)
            },
            executorService
        )
    }


//    override fun <T, R> submit(task: Task<T>, work: (T) -> R): Future<R> {
//        return executorService.submit(
//            Callable { work(task.input) }
//        )
//    }

    override fun close() {
        executorService.shutdown()
    }
}

// Example usage
fun processEvents1(records: List<CordaConsumerRecord<String, SessionEvent>>): List<String> {
    return records.map { record ->
//        println("[${Thread.currentThread().name}] Start processing record with key: ${record.key}, value: ${record.value}")
        Thread.sleep(10) // Simulates some asynchronous processing
        "Processed record with key: ${record.key}, value: ${record.value}"
    }
}

fun processEvent1(record: CordaConsumerRecord<String, SessionEvent>): String {
    Thread.sleep(10) // Simulates some asynchronous processing
    return "Processed record with key: ${record.key}, value: ${record.value}"
}

fun main() {
//    perfTest(8, 1000, 5000)
    experiment(8, 1000, 5000)
}

private fun experiment(numThreads: Int, numRecords: Int, numGroups: Int) {
    val taskManager: TaskManagerImpl = TaskManagerImpl(numThreads)
    val records = (0 until numRecords).map {
        val rand = Random.nextInt(numGroups)
        CordaConsumerRecord("flow.session.data", rand, rand.toLong(), "flow$rand", SessionEvent("checkpoint_${UUID.randomUUID()}"))
    }
    val groupedEvents = records.groupBy { it.key }
    println("Records $numRecords, groups $numGroups")
    println("Warming up")

    val submittedTasks = groupedEvents.map { (key, value) ->
        taskManager.submit(TaskImpl(key, value, TaskType.FLOW), ::processEvents1)
    }

    submittedTasks.forEach { it.get() }

    taskManager.close()
}

private fun perfTest(numThreads: Int, numRecords: Int, numGroups: Int) {
    val taskManager: TaskManagerImpl = TaskManagerImpl(numThreads)
    val records = (0 until numRecords).map {
        val rand = Random.nextInt(numGroups)
        CordaConsumerRecord("flow.session.data", rand, rand.toLong(), "flow$rand", SessionEvent("checkpoint_${UUID.randomUUID()}"))
    }
    val groupedEvents = records.groupBy { it.key }
    println("Records $numRecords, groups $numGroups")
    println("Warming up")
    var warmup = 0
    while (warmup < 10) {
        val submittedTasks = groupedEvents.map { (key, value) ->
            taskManager.submit(TaskImpl(key, value, TaskType.FLOW), ::processEvents1)
        }

        submittedTasks.forEach { it.get() }

        warmup++
        println("Warming up iteration $warmup")
    }
    println("Warming up complete")

    val data = mutableListOf<Long>()
    var n = 0
    while (n < 10) {
        val time = measureTimeMillis {
            val submittedTasks = groupedEvents.map { (key, value) ->
                taskManager.submit(TaskImpl(key, value, TaskType.FLOW), ::processEvents1)
            }

            submittedTasks.forEach { it.get() }
        }
        data.add(time)
        n++
        println("Iteration $n, time $time ms")
    }
    println("Records $numRecords, groups $numGroups")
    val mean = calculateMean(data)
    val median = calculateMedian(data)
    val minimum = calculateMinimum(data)
    val maximum = calculateMaximum(data)
    val sum = calculateSum(data)
    val stdDeviation = calculateStandardDeviation(data, mean)

    println("Mean: $mean ms")
    println("Median: $median ms")
    println("Minimum: $minimum ms")
    println("Maximum: $maximum ms")
    println("Sum: $sum ms")
    println("Standard Deviation: $stdDeviation ms")
    taskManager.close()
}

fun calculateMean(data: List<Long>): Double {
    val sum = calculateSum(data)
    return sum.toDouble() / data.size
}

fun calculateMedian(data: List<Long>): Long {
    val sortedData = data.sorted()
    val size = sortedData.size
    return if (size % 2 == 0) {
        val middleIndex = size / 2
        (sortedData[middleIndex - 1] + sortedData[middleIndex]) / 2
    } else {
        sortedData[size / 2]
    }
}

fun calculateMinimum(data: List<Long>): Long {
    return data.minOrNull() ?: 0L
}

fun calculateMaximum(data: List<Long>): Long {
    return data.maxOrNull() ?: 0L
}

fun calculateSum(data: List<Long>): Long {
    return data.sum()
}

fun calculateStandardDeviation(data: List<Long>, mean: Double): Double {
    val sumOfSquares = data.map { (it - mean).pow(2) }.sum()
    return sqrt(sumOfSquares / data.size)
}

/**
 * 8 groups of size 100,000/8
 * Mean: 427.33 ms
 * Median: 417 ms
 * Minimum: 390 ms
 * Maximum: 720 ms
 * Sum: 42733 ms
 * Standard Deviation: 45.72156055954346 ms
 *
 * groups of size 1
 * Mean: 596.96 ms
 * Median: 590 ms
 * Minimum: 560 ms
 * Maximum: 891 ms
 * Sum: 59696 ms
 * Standard Deviation: 39.20482623351366 ms
 */

/**
 * test 2 - 100 records, repeats 100 times
 * 8 groups of size 100/8:
 * Mean: 266.89 ms
 * Median: 267 ms
 * Minimum: 256 ms
 * Maximum: 282 ms
 * Sum: 26689 ms
 * Standard Deviation: 5.578342047598014 ms
 *
 * groups with small number of records:
 * Mean: 205.01 ms
 * Median: 205 ms
 * Minimum: 193 ms
 * Maximum: 216 ms
 * Sum: 20501 ms
 * Standard Deviation: 4.136411488234697 ms
 */

/**
 * test 3 - 1000 records, repeats 100 times
 * 8 groups of size 1000/8:
 * Mean: 2236.48 ms
 * Median: 2226 ms
 * Minimum: 2210 ms
 * Maximum: 2346 ms
 * Sum: 223648 ms
 * Standard Deviation: 28.88511035118266 ms
 *
 * groups with small number of records:
 * Mean: 1945.42 ms
 * Median: 1945 ms
 * Minimum: 1936 ms
 * Maximum: 1956 ms
 * Sum: 194542 ms
 * Standard Deviation: 3.734113013822693 ms
 */

/**
 * 5000 groups, 1000 records
 * Mean: 1945.3 ms
 * Median: 1944 ms
 * Minimum: 1942 ms
 * Maximum: 1951 ms
 * Sum: 19453 ms
 * Standard Deviation: 3.1 ms
 *
 * 1000 records, 8 groups
 * Mean: 2475.3 ms
 * Median: 2476 ms
 * Minimum: 2469 ms
 * Maximum: 2479 ms
 * Sum: 24753 ms
 * Standard Deviation: 3.551056180912941 ms
 */

/**
 * Using Async api
 * Records 1000, groups 8
 * Mean: 2056.6 ms
 * Median: 2057 ms
 * Minimum: 2051 ms
 * Maximum: 2062 ms
 * Sum: 20566 ms
 * Standard Deviation: 3.3226495451672293 ms
 *
 * Records 1000, groups 5000
 * Mean: 1948.6 ms
 * Median: 1949 ms
 * Minimum: 1943 ms
 * Maximum: 1954 ms
 * Sum: 19486 ms
 * Standard Deviation: 3.4698703145794942 ms
 */