package com.conal.asyncdbops2.impl

import com.conal.asyncdbops2.AsyncDbOps
import com.conal.asyncdbops2.MessageListener
import com.conal.asyncdbops2.MessageProducer
import com.conal.asyncdbops2.SerializedBytes
import com.conal.asyncdbops2.storageworker.AsyncDbOpsRequest


class AsyncDbOpsMessageProducer : MessageProducer {
    override fun <R> send(serializedAsyncDbOps: SerializedBytes<AsyncDbOps<R>>) {
        println("Sending $serializedAsyncDbOps")
    }
}

class AsyncDbOpsMessageListener : MessageListener {
    override fun listen(): AsyncDbOpsRequest {
        println("Listening")
        return AsyncDbOpsRequest(SerializedBytes("somebytes".toByteArray()))
    }
}