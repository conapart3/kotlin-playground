package heartbeat

import java.time.Instant
import java.util.UUID
import java.util.concurrent.Executors

fun main() {
//    Executors.newFixedThreadPool()

    val heartbeatService = HeartbeatService()

    val operationId = UUID.randomUUID().toString()

    val virtualNodeRepository = VirtualNodeRepository()

    heartbeatService.runAndMonitor(RunLiquibaseUpdate(operationId)) { status ->
        if(status.threadId == null) {
            virtualNodeRepository.expireOperation(status.operationId, status.lastTimestamp)
        }
        virtualNodeRepository.updateOperation(status.operationId, status.lastTimestamp)
    }


}

class RunLiquibaseUpdate(
    override val operationId: String
) : MonitoredRun {
    override fun run() {
        for (i in 1..10)
            Thread.sleep(5000)

        Thread.sleep(10000)
    }
}

class VirtualNodeRepository {
    fun updateOperation(operationId: String, lastTimestamp: Instant) {
        println("Storing VirtualNodeOperation($operationId, $lastTimestamp)")
    }
    fun expireOperation(operationId: String, lastTimestamp: Instant) {
        println("Expiring VirtualNodeOperation($operationId, $lastTimestamp)")
    }
}