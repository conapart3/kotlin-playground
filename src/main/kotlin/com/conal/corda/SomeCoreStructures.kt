package com.conal.corda

import com.conal.main.ContractState
import java.util.stream.Stream

@Suppress("UNCHECKED_CAST")
fun <T, U : T> uncheckedCast(obj: T) = obj as U

interface GenericQueryPostProcessor<I, R> {
    /**
     * Name of this post-processor implementation, used to identify post-processor instances.
     */
    val name: String

    /**
     * If false, implementation will not be usable from RPC APIs.
     */
    val availableForRPC: Boolean get() = false

    /**
     * Lazily post-process a [Stream] of inputs of type [I] and return [Stream] of type [R].
     */
    fun postProcess(inputs: Stream<I>): Stream<R>
}

interface CustomQueryPostProcessor<R> : GenericQueryPostProcessor<Any, R>
interface StateAndRefPostProcessor<R> : GenericQueryPostProcessor<StateAndRef<ContractState>, R>

data class StateAndRef<out T : ContractState>(val state: TransactionState<T>)
data class TransactionState<out T : ContractState>(val data: T)

class MyState(override val participants: String) : ContractState
class PetState(override val participants: String, val petName: String) : ContractState

class PetPostProcessor() : StateAndRefPostProcessor<MyPet> {
    override fun postProcess(inputs: Stream<StateAndRef<ContractState>>): Stream<MyPet> {
        return inputs
            .map { it.state.data }
            .filter { it is PetState }
            .map { MyPet(it.participants, "123") }
    }

    override val name: String
        get() = "PetPostProcesor"
}
data class MyPet(val name:String, val stateId: String)

/**
 * Identity function post-processor that returns [ContractState]s with no side-effects.
 *
 * Corda attempts to load [StateAndRef]s from the named query results and applies this pass-through post-processor to return `ContractStates`.
 */
class IdentityContractStatePostProcessor : StateAndRefPostProcessor<ContractState> {
    companion object {
        const val POST_PROCESSOR_NAME = "Corda.IdentityContractStatePostProcessor"
    }
    override fun postProcess(inputs: Stream<StateAndRef<ContractState>>): Stream<ContractState> {
        return inputs.map { it.state.data }
    }
    override val name = POST_PROCESSOR_NAME
    override val availableForRPC = true
}

/**
 * Simple post-processor that just returns [StateAndRef]s with no side-effects.
 *
 * Corda attempts to load [StateAndRef]s from the named query results and applies this pass-through post-processor to return the `StateAndRef`s.
 */
class IdentityStateAndRefPostProcessor : StateAndRefPostProcessor<StateAndRef<ContractState>> {
    companion object {
        const val POST_PROCESSOR_NAME = "Corda.IdentityStateAndRefPostProcessor"
    }
    override fun postProcess(inputs: Stream<StateAndRef<ContractState>>): Stream<StateAndRef<ContractState>> {
        return inputs
    }
    override val name = POST_PROCESSOR_NAME
    override val availableForRPC = true
}
