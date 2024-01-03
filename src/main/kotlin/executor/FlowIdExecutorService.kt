package executor

import java.util.concurrent.*

class FlowIdExecutorService(numThreads: Int) : AbstractExecutorService() {

    private val threadPool: ExecutorService = Executors.newFixedThreadPool(numThreads)

    private val flowIdLockMap: MutableMap<String, Any> = HashMap()

    override fun execute(command: Runnable) {
        throw UnsupportedOperationException("Use execute with flowId instead.")
    }

    fun execute(flowId: String, command: Runnable) {
        synchronized(this) {
            var lock = flowIdLockMap[flowId]
            if (lock == null) {
                lock = Object()
                flowIdLockMap[flowId] = lock
            }
            threadPool.execute {
                synchronized(lock) {
                    command.run()
                }
            }
        }
    }

    override fun shutdown() {
        threadPool.shutdown()
    }

    override fun shutdownNow(): List<Runnable> {
        return threadPool.shutdownNow()
    }

    override fun isShutdown(): Boolean {
        return threadPool.isShutdown
    }

    override fun isTerminated(): Boolean {
        return threadPool.isTerminated
    }

    override fun awaitTermination(timeout: Long, unit: TimeUnit): Boolean {
        return threadPool.awaitTermination(timeout, unit)
    }

    // Other methods from ExecutorService can be implemented similarly if needed
}
