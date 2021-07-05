@file:JvmName("ConcurrencyUtils")
package com.conal.corda

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.time.Duration
import java.util.concurrent.CancellationException
import java.util.concurrent.ExecutionException
import java.util.concurrent.Future
import java.util.concurrent.TimeoutException
import java.util.concurrent.atomic.AtomicBoolean

/** Invoke [getOrThrow] and pass the value/throwable to success/failure respectively. */
fun <V, W> Future<V>.match(success: (V) -> W, failure: (Throwable) -> W): W {
    val value = try {
        getOrThrow()
    } catch (t: Throwable) {
        return failure(t)
    }
    return success(value)
}

/**
 * As soon as a given future becomes done, the handler is invoked with that future as its argument.
 * The result of the handler is copied into the result future, and the handler isn't invoked again.
 * If a given future errors after the result future is done, the error is automatically logged.
 */
fun <V, W> firstOf(vararg futures: CordaFuture<out V>, handler: (CordaFuture<out V>) -> W) = firstOf(futures, defaultLog, handler)

private val defaultLog = LoggerFactory.getLogger("net.corda.core.concurrent")

internal const val SHORT_CIRCUITED_TASK_FAILED_MESSAGE = "Short-circuited task failed:"

internal fun <V, W> firstOf(futures: Array<out CordaFuture<out V>>, log: Logger, handler: (CordaFuture<out V>) -> W): CordaFuture<W> {
    val resultFuture = openFuture<W>()
    val winnerChosen = AtomicBoolean()
    futures.forEach {
        it.then {
            when {
                winnerChosen.compareAndSet(false, true) -> resultFuture.capture { handler(it) }
                it.isCancelled -> {
                    // Do nothing.
                }
                else -> it.match({}, { log.error(SHORT_CIRCUITED_TASK_FAILED_MESSAGE, it) })
            }
        }
    }
    return resultFuture
}

/**
 * Waits if necessary for the computation to complete, and then retrieves its result.
 *
 * This function is the same as [Future.get] except that it throws the underlying exception by unwrapping the [ExecutionException].
 *
 * @param timeout The maximum duration to wait until the future completes.
 *
 * @return The computed result.
 *
 * @throws CancellationException If the computation was cancelled.
 * @throws InterruptedException  If the current thread was interrupted while waiting.
 * @throws TimeoutException      If the wait timed out.
 * @see getOrThrow()
 * @see Future.get(long timeout, TimeUnit unit)
 */
@Throws(InterruptedException::class, TimeoutException::class)
fun <V> Future<V>.getOrThrow(timeout: Duration? = null): V = try {
    get(timeout)
} catch (e: ExecutionException) {
    throw e.cause!!
}