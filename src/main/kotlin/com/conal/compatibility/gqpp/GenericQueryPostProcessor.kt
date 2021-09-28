package com.conal.compatibility.gqpp

import java.util.stream.Stream

interface GenericQueryPostProcessor<I, R> {
    /**
     * Name of this post-processor implementation, used to identify post-processor instances.
     */
    val name: String

    /**
     * If false, implementation will not be usable from RPC APIs.
     */
    val availableForRPC: Boolean get() = false

    /**
     * Lazily post-process a [Stream] of inputs of type [I] and return [Stream] of type [R].
     */
    fun postProcess(inputs: Stream<I>): Stream<R>
}