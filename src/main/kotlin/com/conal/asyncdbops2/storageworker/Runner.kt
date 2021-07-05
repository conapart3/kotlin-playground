package com.conal.asyncdbops2.storageworker

import com.conal.asyncdbops2.AsyncDbOps
import com.conal.asyncdbops2.MyDbOps2
import com.conal.asyncdbops2.SerializedBytes
import com.conal.asyncdbops2.impl.AsyncDbOpsMessageListener
import com.conal.asyncdbops2.serialize
import com.conal.corda.NamedQueryRequestImpl
import com.conal.corda.PersistenceServiceImpl

fun main() {

    // simulate the receiving of the request
    val request = AsyncDbOpsMessageListener().listen()
    val asyncDbOps = request.serializedBytes.deserialize()

    // simulate diService with the persistence service
//    val dependencyInjectionService = DependencyInjectionServiceImpl()
//    dependencyInjectionService.singletonServices[PersistenceService::class.java] =
//    val persistenceService = dependencyInjectionService.getSingletonService(PersistenceService::class) as PersistenceService

    val response = asyncDbOps.execute(PersistenceServiceImpl())

    if (response != null) {
        println(response.javaClass.typeName)
    }
    // we can do an unchecked cast
    // return response onto queue
    // note cs - type safety we can just do uncheckedCast when deserializing and putting the results into the result object?
    response?.serialize()
}

data class AsyncDbOpsRequest(
    val serializedBytes: SerializedBytes<out AsyncDbOps<*>>
)

fun SerializedBytes<*>.deserialize() : AsyncDbOps<*> {
    // just simulate deserialization
    // in actual fact, we are deserializing the instance and it contains the vals inside
    return MyDbOps2(
        com.conal.asyncdbops2.MyContractState("nobody"),
        NamedQueryRequestImpl("query", mapOf(), null, null, 200)
    )
}