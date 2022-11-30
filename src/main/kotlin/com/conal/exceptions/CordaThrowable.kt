package com.conal.exceptions

interface CordaThrowable {
    var originalExceptionClassName: String?
    val originalMessage: String?
    fun setMessage(message: String?)
    fun setCause(cause: Throwable?)
    fun addSuppressed(suppressed: Array<Throwable>)
}