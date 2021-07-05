package com.conal.asyncdbops2.impl

import co.paralleluniverse.fibers.Suspendable
import com.conal.asyncdbops2.*
import com.conal.corda.uncheckedCast

interface AsyncDbOpsStarter {
    fun <R> invokeDbOpsAsync(asyncDbOps: AsyncDbOps<R>) : R
}

class AsyncDbOpsStarterImpl(private val messageProducer: MessageProducer = AsyncDbOpsMessageProducer(),
                            private val messageListener: MessageListener = AsyncDbOpsMessageListener()) : AsyncDbOpsStarter {

    // note cs - we will suspend in here similar to mechanism in proxy based approach
    @Suspendable
    override fun <R> invokeDbOpsAsync(asyncDbOps: AsyncDbOps<R>): R {

        // serialize the DbOps
        // send it over the wire
        // suspend and wait for the result
        val serializedAsyncDbOps = asyncDbOps.serialize()

        val implClass = asyncDbOps.javaClass

        // note cs - but we send the full serialized object over the wire
        messageProducer.send(serializedAsyncDbOps)

//        Fiber.parkAndSerialize { _, _ ->
//            println("Suspended on invokeDbOpsAsync")
            //
//        }

//        val response = messageListener.listen<R>()
        // wait for a response

        // start up the fiber again with the response

        // any results that are to be passed from storageworker to corda node need to also be @CordaSerializable.

        return deserializeResult()
    }

    private fun <R> deserializeResult(): R {
        return uncheckedCast(Any())
    }
}
