package com.conal.corda

import java.time.Duration
import java.util.concurrent.CancellationException
import java.util.concurrent.ExecutionException
import java.util.concurrent.CompletableFuture
import java.util.concurrent.Future
import java.util.concurrent.TimeoutException
import java.util.function.Consumer

/**
 * [CordaFuture] is the same as [Future] with additional methods to provide some of the features of [CompletableFuture] while minimising
 * the API surface area. In Kotlin, to avoid compile errors, whenever [CordaFuture] is used in a parameter or extension method receiver
 * type, its type parameter should be specified with out variance.
 *
 * @param V The result type returned by this Future's [get] method.
 */
interface CordaFuture<V> : Future<V> {

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
    fun getOrThrow(timeout: Duration): V

    /**
     * Waits if necessary for the computation to complete, and then retrieves its result.
     *
     * This function is the same as [Future.get] except that it throws the underlying exception by unwrapping the [ExecutionException].
     *
     * @return The computed result.
     *
     * @throws CancellationException If the computation was cancelled.
     * @throws InterruptedException  If the current thread was interrupted while waiting.
     * @see #getOrThrow(Duration)
     * @see Future#get()
     */
    @Throws(InterruptedException::class)
    fun getOrThrow(): V

    /**
     * Run the given [callback] when this future is done on the completion thread.
     *
     * If the completion thread is problematic for you e.g. deadlock, you can submit to an executor manually. If callback fails, its
     * throwable is logged.
     *
     * @param callback The callback to execute when this future completes.
     */
    fun then(callback: (CordaFuture<V>) -> Unit)

    /**
     * Run the given [callback] when this future is done on the completion thread.
     *
     * If the completion thread is problematic for you e.g. deadlock, you can submit to an executor manually. If callback fails, its
     * throwable is logged.
     *
     * @param callback The [Consumer] to execute when this future completes.
     */
    fun then(callback: Consumer<CordaFuture<V>>)

    /**
     * Convert this future into a {@link CompletableFuture}.
     *
     * @return A new [CompletableFuture] with the same outcome as this [Future].
     */
    fun toCompletableFuture(): CompletableFuture<V>
}