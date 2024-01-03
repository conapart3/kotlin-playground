package executor

import java.util.concurrent.Future

interface IdBasedExecutorService {
    fun <T> execute(flowId: String, command: Runnable): Future<T>
}