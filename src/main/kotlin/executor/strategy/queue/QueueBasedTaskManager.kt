package executor.strategy.queue

import executor.strategy.TaskManager
import java.util.concurrent.Callable
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import java.util.concurrent.Future

class QueueBasedTaskManager(
    private val numThreads: Int = 8,
    private val executorService: ExecutorService = Executors.newFixedThreadPool(numThreads)
) : TaskManager {
    override fun <T, R> submit(items: List<T>, work: (T) -> R): List<Future<R>> {
        return items.map {
            executorService.submit(Callable {
                work(it)
            })
        }
    }

    override fun <K, T, R> submit(items: List<T>, threadGroupingStrategy: (List<T>) -> Map<K, List<T>>, work: (T) -> R): List<Future<R>> {
        val groupedItems: Map<K, List<T>> = threadGroupingStrategy(items)
        val futures = mutableListOf<Future<R>>()
//        groupedItems.entries.forEach { (key, sequentialItems) ->
//            val future = executorService.submit(Callable {
//                sequentialItems.map {
//                    work(it)
//                    futures.add()
//                }
//            })
//            futures.add(future)
        return listOf()
    }

    override fun close() {
        executorService.shutdown()
    }
}
