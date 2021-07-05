package com.conal.asyncdbops1

import java.util.*

data class AsyncDbOpsExecutionResult<T>(
    val id: UUID,
//    val returnValue: CordaFuture<T>
)