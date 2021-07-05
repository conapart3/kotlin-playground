package com.conal.dbops4.storageworker

import com.conal.asyncdbops2.SerializedBytes
import com.conal.dbops4.DbOps
import java.lang.reflect.Method

interface DbOpsRequestHandler {
    fun handleDbOpsRequest(request: DbOpsRequest)
}

data class DbOpsRequest(
    val method: Method,
    val dbOps: DbOps,
    val args: SerializedBytes<*>
)