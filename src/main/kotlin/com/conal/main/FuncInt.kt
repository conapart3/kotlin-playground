package com.conal.main

import java.io.ByteArrayOutputStream
import java.io.ObjectInputStream
import java.io.ObjectOutputStream
import java.io.Serializable

@FunctionalInterface
interface VaultQueryPostProcessor<T : ContractState, R> : Serializable {
    fun postProcess(inputs: Sequence<T>): Sequence<R>
}

interface MyAPI{
    fun <T : ContractState, R> deserializeInputsAndQueryVault(first: ByteArray, second: ByteArray): ByteArray
    fun <T : ContractState, R> queryVault(str: String, postProcessor: VaultQueryPostProcessor<T, R>) : List<R>
    fun <T : ContractState, R> queryVault2(str: String, postProcessorFunction: Sequence<T>.() -> Sequence<R>) : List<R>
}

class MyImpl : MyAPI{

    override fun <T : ContractState, R> deserializeInputsAndQueryVault(first: ByteArray, second: ByteArray) : ByteArray {
        return queryVault2(first.deserialize(), second.deserialize<Sequence<T>.() -> Sequence<R>>()).serializeToBytes()
    }

    override fun <T : ContractState, R> queryVault(str: String, postProcessor: VaultQueryPostProcessor<T, R>) : List<R> {
        val contractStates = doSomethingWithStringAndReturnContractStates(str)
        return postProcessor.postProcess(contractStates as Sequence<T>).toList()
    }

    override fun <T : ContractState, R> queryVault2(str: String, postProcessorFunction: Sequence<T>.() -> Sequence<R>) : List<R> {
        val contractStates = doSomethingWithStringAndReturnContractStates(str)
        return postProcessorFunction(contractStates as Sequence<T>).toList()
    }

    private fun doSomethingWithStringAndReturnContractStates(str: String): Sequence<ContractState> {
        return sequenceOf<GridState>(GridState("1", str), GridState("2", str), GridState("3", str))
    }

}

interface ContractState {
    val participants: String
}

class GridState(val grid: String, override val participants: String) : ContractState {
    override fun toString(): String {
        return "GridState(grid='$grid', name='$participants')"
    }
}

class Client(){
    val api: MyAPI = MyImpl()

    fun <T : ContractState, R> callQueryVault(str: String, postProcessor: (Sequence<T>) -> Sequence<R>): List<R> {
        val serializedPostProcessor = postProcessor.serializeToBytes()
        val serializedStr = str.serializeToBytes()

        return api.deserializeInputsAndQueryVault<GridState, GridState>(serializedStr, serializedPostProcessor).deserialize()
    }
}

fun main() {

    val api: VaultQueryRPCOps = VaultQueryRPCOpsImpl()
//    api.queryVaultByNamedQuery(RpcVaultNamedQueryRequest("string", mutableMapOf()) {s: Sequence<GridState> -> s.filter { it.grid == "1" }})
//    api.queryVaultByNamedQuery(RpcVaultNamedQueryRequest("string", mutableMapOf())
//        { inputs: Sequence<GridState> -> inputs.filter { it.grid == "1" } } as (Serializable, (Sequence<ContractState>) -> Sequence<Any>)?)


//    val api: MyAPI = MyImpl()
//
//
//    println(Client().callQueryVault("str") { seq: Sequence<GridState> -> seq.filter { it.grid == "1" } })
//
//
//    println(api.queryVault("str", object : VaultQueryPostProcessor<GridState, GridState> {
//        override fun postProcess(inputs: Sequence<GridState>): Sequence<GridState> {
//            return inputs.filter { it.grid == "1"}
//        }
//    }))

//    api.queryVault<GridState, GridState>("str") { inputs ->
//        inputs.filter { it.grid == "1"}
//    }
}

fun Any.serializeToBytes(): ByteArray = ByteArrayOutputStream().use { it -> ObjectOutputStream(it).writeObject(this); it }.toByteArray()
inline fun <reified T: Any> ByteArray.deserialize(): T = ObjectInputStream(inputStream()).readObject() as T
inline fun <reified T: Any> T.serializeDeserialize() = serializeToBytes().deserialize<T>()


