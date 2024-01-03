package executor.coroutinesonly

import executor.CordaConsumerRecord
import executor.SessionEvent
import executor.processRecords
import java.util.UUID
import java.util.concurrent.CompletableFuture
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.Executor
import java.util.concurrent.Future
import kotlin.random.Random

enum class CommandType {
    FLOW
}

interface TasksManager : Executor {
    fun waitForAllJobs(): Future<Void>
    fun cancelAllJobs(): Future<Void>
    fun shutdown(): Future<Void>
    fun <T> execute(type: CommandType, command: () -> T): Job<T>
}

class TasksManagerImpl : TasksManager {

    private val jobs = ConcurrentHashMap.newKeySet<CompletableFuture<*>>()

    override fun waitForAllJobs(): CompletableFuture<Void> {
        TODO("Not yet implemented")
    }

    override fun cancelAllJobs(): CompletableFuture<Void> {
        TODO("Not yet implemented")
    }

    override fun shutdown(): CompletableFuture<Void> {
        TODO("Not yet implemented")
    }

    override fun <T> execute(type: CommandType, command: () -> T): CompletableFuture<T> {
        val coroutineScope = CoroutineScope(Dispatchers.Default)
        val deferred = coroutineScope.async { command.invoke() }
        val completableFuture = CompletableFuture<T>()

        // Use the coroutine's Deferred to complete the CompletableFuture
        deferred.invokeOnCompletion { cause ->
            if (cause == null) {
                completableFuture.complete(deferred.await())
            } else {
                completableFuture.completeExceptionally(cause)
            }
        }

        jobs.add(completableFuture)
        completableFuture.whenComplete { _, _ ->
            jobs.remove(completableFuture)
        }

        return completableFuture
    }

    override fun execute(command: Runnable) {
        TODO("Not yet implemented")
    }
}


fun main() {

    val records = (0 until 100).map {
        val rand = Random.nextInt(5)
        CordaConsumerRecord("flow.session.data", rand, rand.toLong(), "flow$rand", SessionEvent("checkpoint_${UUID.randomUUID()}"))
    }

    val taskManager: TasksManager = TasksManagerImpl()

    val groupedTasks = records.groupBy {it.key }
    val futures = groupedTasks.map { (key, list) ->
        taskManager.execute(CommandType.FLOW) {
            processRecords(list)
        }
    }

    val all = CompletableFuture.allOf(*futures.toTypedArray())
    all.join()

    taskManager.shutdown()
}

class Runner {




}