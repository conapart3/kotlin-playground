package executor.strategy.lock

import executor.strategy.TaskManager
import java.util.concurrent.Callable
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import java.util.concurrent.Future

class LockBasedTaskManager(
    private val numThreads: Int = 8,
    private val executorService: ExecutorService = Executors.newFixedThreadPool(numThreads),
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
        val lockMap: ConcurrentHashMap<K, Any?> = ConcurrentHashMap()

        return listOf()
//        groupedItems.map { (key, items) ->
//            executorService.submit(Callable {
//                lockMap.computeIfAbsent(key) { Any() }
//            })
//        }
    }

    override fun close() {
        executorService.shutdown()
    }
}
