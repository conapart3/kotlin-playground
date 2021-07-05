package com.conal.dbops2

import com.conal.corda.uncheckedCast
import com.google.common.util.concurrent.SettableFuture
import net.sf.cglib.proxy.Enhancer
import net.sf.cglib.proxy.MethodInterceptor
import net.sf.cglib.proxy.MethodProxy
import java.lang.reflect.Method

class DbOpsCGLIBProxyFactory {

    fun <T : DbOps, R : T> proxy(delegate: T): R {
        val enhancer = Enhancer()
        enhancer.setSuperclass(delegate::class.java)
        enhancer.setCallback(DbOpsMethodInterceptor(delegate::class.java))
        val proxy = enhancer.create()
        return uncheckedCast(proxy)
    }

    private class DbOpsMethodInterceptor(val delegateClass: Class<out DbOps>) : MethodInterceptor {

        private companion object {
            const val CLASS_METHOD_DIVIDER = "#"
        }

//        @Suspendable
        override fun intercept(obj: Any, method: Method, args: Array<out Any>, proxy: MethodProxy): Any {

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
            return delegateClass.javaClass.name + CLASS_METHOD_DIVIDER + method.name
        }

    }
}

