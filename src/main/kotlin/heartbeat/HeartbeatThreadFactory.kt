package heartbeat

import java.time.Instant
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.ThreadFactory
import javax.transaction.NotSupportedException

class HeartbeatThreadFactory(

) : HeartbeatMonitor, ThreadFactory {

    override fun getThreadStatus(operationId: String): ThreadStatus {
        val now = Instant.now()
        val thread = stateMap[operationId] ?: return ThreadStatus(null, operationId, now)
        return ThreadStatus(thread.name, operationId, now)
    }

    private val stateMap: ConcurrentHashMap<String, Thread> = ConcurrentHashMap<String, Thread>()

    override fun newThread(r: Runnable): Thread {
        return when (r) {
            is MonitoredRun -> createMonitoredThread(r.operationId)
            else -> throw NotSupportedException("Runnable not supported for heartbeat monitoring.")
        }
    }

    private fun createMonitoredThread(operationId: String): Thread {
        val thread = Thread()
        stateMap[operationId] = thread
        return thread
    }
}

interface HeartbeatMonitor {
    fun getThreadStatus(operationId: String): ThreadStatus
}