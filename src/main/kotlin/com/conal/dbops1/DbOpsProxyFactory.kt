package com.conal.dbops1

import com.google.common.util.concurrent.SettableFuture
import java.lang.reflect.InvocationHandler
import java.lang.reflect.Method
import java.lang.reflect.Modifier
import java.lang.reflect.Proxy

class DbOpsProxyFactory {

    fun <T : DbOps> proxy(delegate: T, targetInterface: Class<T>): DbOps {
//        require(targetInterface.isInterface) { "Interface is expected instead of $targetInterface" }
        val handler = DbOpsProxyHandler(delegate, targetInterface)
        @Suppress("UNCHECKED_CAST")
        return Proxy.newProxyInstance(delegate::class.java.classLoader, arrayOf(targetInterface), handler) as DbOps
    }

    private class DbOpsProxyHandler(
        val delegate: Any,
        private val clazz: Class<*>,
    ) : InvocationHandler {

        private companion object {
            const val CLASS_METHOD_DIVIDER = "#"
        }

//        @Suspendable
        override fun invoke(proxy: Any, method: Method, args: Array<out Any?>?): Any {
            if(!Modifier.isPublic(method.modifiers)) {
                // there could be private methods in the class we don't want to proxy these too!
                method.invoke(delegate, args)
            }
            val methodFqn = produceMethodFullyQualifiedName(method)
            val argumentsList: List<Any?> = args?.toList() ?: emptyList()

            // todo serialize from environment with correct serialization context
//            val serialisedArguments = argumentsList.serialize()

//            val invocationId = Trace.InvocationId.newInstance()
//            val dto = buildMessageForSubmission(invocationId, methodFqn, serialisedArguments)
//            storageExecutionProducer.submit(dto)

            val replyFuture = SettableFuture.create<Any>()

//            require(requestMap.put(invocationId, replyFuture) == null) {
//                "Generated several RPC requests with same ID $invocationId"
//            }

            return replyFuture
        }

//        private fun buildMessageForSubmission(
//            invocationId: Trace.InvocationId,
//            methodFqn: String,
//            serialisedArguments: SerializedBytes<List<Any?>>
//        ): StorageExecutionProducerDto {
//            return StorageExecutionProducerDtoImpl(invocationId, methodFqn, serialisedArguments)
//        }

        private fun produceMethodFullyQualifiedName(method: Method): String {
            return delegate.javaClass.name + CLASS_METHOD_DIVIDER + method.name
        }
    }
}

