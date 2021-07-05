package com.conal.asyncdbops2

import com.conal.corda.CordaFuture
import java.util.*

data class AsyncDbOpsExecutionResult<T>(
    val id: UUID,
    val returnValue: CordaFuture<T>
)