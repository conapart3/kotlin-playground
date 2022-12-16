package heartbeat

import java.time.Duration
import java.time.Instant
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class HeartbeatService(
    val expiryThreshold: Duration = Duration.ofSeconds(7)
) {
    private val threadPool: ExecutorService
    private val heartbeatMonitor: HeartbeatThreadFactory = HeartbeatThreadFactory()

    init {
        threadPool = Executors.newFixedThreadPool(10, heartbeatMonitor)
    }

    fun runAndMonitor(r: MonitoredRun, onHeartbeat: (t: ThreadStatus) -> Unit){
        threadPool.submit(r)
        val threadStatus = heartbeatMonitor.getThreadStatus(r.operationId)
        onHeartbeat(threadStatus)
    }
}

data class ThreadStatus(
    val threadId: String?,
    val operationId: String,
    val lastTimestamp: Instant
)

interface MonitoredRun: Runnable {
    val operationId: String
}