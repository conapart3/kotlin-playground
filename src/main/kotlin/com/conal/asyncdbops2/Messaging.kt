package com.conal.asyncdbops2

import com.conal.asyncdbops2.storageworker.AsyncDbOpsRequest


interface MessageProducer {
    fun <R> send(serializedAsyncDbOps: SerializedBytes<AsyncDbOps<R>>)
}

interface MessageListener {
    fun listen(): AsyncDbOpsRequest
}

class SerializedBytes<T : Any>(bytes: ByteArray){
    val summary: String get() = "SerializedBytes(size = )"
}

fun <T : Any> T.serialize(): SerializedBytes<T> {
    return SerializedBytes(this.toString().encodeToByteArray())
}