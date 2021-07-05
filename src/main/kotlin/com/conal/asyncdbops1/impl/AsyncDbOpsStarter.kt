package com.conal.asyncdbops1.impl

import com.conal.asyncdbops1.AsyncDbOps
import com.conal.asyncdbops1.AsyncDbOpsExecutionResult
import java.util.*

class AsyncDbOpsStarter {

    // mirrors FlowStarterImpl
    // this is on the internal side - storage worker side
    fun <T : AsyncDbOps> invokeDbOpsAsync(
        dbOpsClass: Class<T>, vararg args: Any?
    ): AsyncDbOpsExecutionResult<T> {
        // what does counterpart in FlowStarterImpl do?
        // basically builds a flowLogicReference and creates an event with a cordaFuture inside to listen to responses
        // calls SingleThreadedStateMachineManager which has event handling for externalStartFlow to deliver the external event
        // loads the flow checkpoint by the flowId
        // it either creates a new checkpoint or reuses a copy of the existing one
        // it creates the state machine state using the flow fiber and other data
        // then it calls flow.fiber.start() or Fiber.unparkDeserialized
        // so the flow will be executed in a separate fiber.
        // the future is passed back up to be added into the event thats passed back to the caller.

        // So what should this do?
        // basically serialize the dbOpsClass object with the arguments

        // this is different from the proxy approach.
        // the FlowStarterImpl reinflates a flow from the database checkpoint or creates a new one, it doesn't just call
        // a class

        // serialize the DbOps
        // send it over the wire
        // suspend and wait for the result

        return AsyncDbOpsExecutionResult(UUID.randomUUID())
    }

    fun <T : AsyncDbOps> startDbOperationDynamic(java: Class<T>, vararg args: Any?): AsyncDbOpsExecutionResult<T> {
        return invokeDbOpsAsync(java, args)
    }
}


/**
 * Extension functions for type safe invocation of dbOps from Kotlin, for example:
 *
 * asyncDbOpsStarter.invokeDbOperation(::MyDbOps, namedQueryRequest, postProcessor)
 */
inline fun <reified T : AsyncDbOps> AsyncDbOpsStarter.invokeDbOperation(
    constructor: () -> T
) : AsyncDbOpsExecutionResult<T> = startDbOperationDynamic(T::class.java)

// need @Suspend on these extension functions?
inline fun <A, reified T : AsyncDbOps> AsyncDbOpsStarter.invokeDbOperation(
    constructor: (A) -> T,
    arg0: A
) : AsyncDbOpsExecutionResult<T> = startDbOperationDynamic(T::class.java, arg0)

inline fun <A, B, reified T : AsyncDbOps> AsyncDbOpsStarter.invokeDbOperation(
    constructor: (A, B) -> T,
    arg0: A,
    arg1: B
) : AsyncDbOpsExecutionResult<T> = startDbOperationDynamic(T::class.java, arg0, arg1)

