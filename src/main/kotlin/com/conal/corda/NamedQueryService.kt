package com.conal.corda

interface NamedQueryService {
    fun <I, R> query(
        namedQueryRequest: NamedQueryRequest,
        postProcessor: GenericQueryPostProcessor<I, R>
    ): List<R>

    fun query(
        namedQueryRequest: NamedQueryRequest
    ): List<Any?>
}