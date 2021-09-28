package com.conal.jackson

import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.core.SerializableString
import com.fasterxml.jackson.core.io.CharacterEscapes
import com.fasterxml.jackson.databind.JsonSerializer
import com.fasterxml.jackson.databind.SerializerProvider
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import com.fasterxml.jackson.databind.module.SimpleModule
import java.io.IOException
import java.io.Serializable
import java.security.PublicKey
import java.time.Instant
import java.util.*
import javax.persistence.Column
import javax.persistence.EmbeddedId

fun main(){
    Runner4().run()
//    Runner4().hide()
}

class Runner4 {
    fun run(){

        testSerializationOfStateAndRef()


        val message = MessageState("info", "none")

        val tstate = TransactionState(message)
        val stateAndRef = StateAndRef(tstate, StateRef("tx1", 2))

        val string = rpcObjectMapper.writeValueAsString(stateAndRef)
        println(string)

        val output = rpcObjectMapper.readValue(string, MessageStatePojo::class.java)
        println(output)

//        println(rpcObjectMapper.writeValueAsString(RpcVaultStateResponseItem(StateRef("str", 1), "string", Instant.now())))
        val messages = listOf<MessageState>(MessageState("info", "me"), MessageState("info", "me"))
        val nullsa = listOf<MessageState?>(null, null, null)

        println(nullsa.firstOrNull { it != null })
        println(nullsa.firstNonNullOrElseNull())

        println(rpcObjectMapper.writeValueAsString(messages))
        println(rpcObjectMapper.writeValueAsString(nullsa))

        val messageState1 = rpcObjectMapper.readValue("null", MessageState::class.java)
        val messageState2 = rpcObjectMapper.readValue("null", List::class.java)

        println(rpcObjectMapper.writeValueAsString(null))
        val pet = PetState("rex2", "init1", "none")



    }

    private fun testSerializationOfStateAndRef() {
        val message = MessageState("info", "none")
        val tstate = TransactionState(message)
        val stateAndRef = StateAndRef(tstate, StateRef("tx1", 2))

        val string = rpcObjectMapper.writeValueAsString(stateAndRef)
        println(string)

        val pet = PetState("conal", "test1", "some-participants")
        val tstate2 = TransactionState(pet)
        val stateAndRef2 = StateAndRef(tstate2, StateRef("tx2", 1))

        val output = rpcObjectMapper.writeValueAsString(stateAndRef2)
        println(output)

    }

    fun <T> List<T?>.firstNonNullOrElseNull(): T? {
        for (item in this) {
            item?.let { return it }
        }
        return null
    }
    fun hide() {

        val original = listOf(1,2,3,4,5,6,7,8)
        val someMap = mapOf("a" to listOf(0,1,2,3), "b" to listOf(4,5,6,7))

        require(someMap.flatMap { it.value }.size == original.size)
        require(someMap.flatMap { it.value }.maxOf { it } == original.size - 1)
        println("finished")
    }
}


data class PetState(
    val name: String = "Rex",
    val initiatorId: String,
    override val participants: String,
): ContractState


internal val rpcObjectMapper = jacksonObjectMapper().apply {
    val module = SimpleModule()
    module.addSerializer(StateAndRef::class.java, StateAndRefSerializer())
    this.registerModule(module)
/*    this.factory.characterEscapes = object : CharacterEscapes() {
        override fun getEscapeCodesForAscii(): IntArray {
            val asciiEscapes = standardAsciiEscapesForJSON()
            asciiEscapes['"'.toInt()] = ESCAPE_NONE
            return asciiEscapes
        }
        override fun getEscapeSequence(ch: Int): SerializableString? {
            return null
        }
    }*/
}


interface JsonRepresentable {
    fun toJsonString(): String
}
internal class StateAndRefSerializer : JsonSerializer<StateAndRef<*>>() {
    override fun serialize(value: StateAndRef<*>, gen: JsonGenerator, serializers: SerializerProvider) {
        gen.writeStartObject()
/*
        val contractState = value.state.data
        if (contractState is JsonRepresentable) {
            gen.writeObjectField("contractState", contractState.toJsonString())
        } else {
            try {
                gen.writeObject(contractState)
            } catch (e: IOException) {
                throw Exception("Could not serialize ContractState, please provide a serialized representation by implementing JsonRepresentable.", e)
            }
        }

        gen.writeStringField("contract", value.state.contract)
        gen.writeStringField("notary", value.state.notary.toString())
        gen.writeStringField("encumbrance", value.state.encumbrance?.toString() ?: "null")

        gen.writeObjectFieldStart("ref")
        gen.writeStringField("txHash", value.ref.txhash.toString())
        gen.writeNumberField("index", value.ref.index)
*/

        val contractState = value.state.data
        if(contractState is JsonRepresentable) {
            gen.writeStringField("state", contractState.toJsonString())
        } else {
            gen.writeObjectField("state", contractState)
        }

        gen.writeStringField("ref", value.ref.toString())

        gen.writeStringField("contract", value.state.contract)
        gen.writeObject(value.state.notary)

        gen.writeEndObject()
    }
}

@JsonSerialize(using = MessageStateSerializer::class)
data class MessageState(val info: String, override val participants: String) : ContractState, JsonRepresentable {
    override fun toJsonString(): String {
        return """
            {"info":"$info","participants":"$participants"}
        """.trimIndent()
    }
}

data class MessageStatePojo(val info: String, val participants: String)

class MessageStateSerializer : JsonSerializer<MessageState>() {
    override fun serialize(value: MessageState, gen: JsonGenerator, serializers: SerializerProvider) {
        gen.writeStringField("info", value.info)
        gen.writeStringField("participants", value.participants)
    }
}

data class RpcVaultStateResponseItem(
    val ref: StateRef,
    val contractStateClassName: String,
    val eventTimestamp: Instant)

internal object RpcVaultStateResponseItemSerializer : JsonSerializer<RpcVaultStateResponseItem>() {
    override fun serialize(value: RpcVaultStateResponseItem, gen: JsonGenerator, serializers: SerializerProvider) {
        gen.writeStartObject()

        gen.writeObjectField("txHash", value.ref.txhash)
        gen.writeNumberField("index", value.ref.index)
        gen.writeObjectField("contractStateClassName", value.contractStateClassName)
        gen.writeObjectField("eventTimestamp", value.eventTimestamp)

        gen.writeEndObject()
    }
}