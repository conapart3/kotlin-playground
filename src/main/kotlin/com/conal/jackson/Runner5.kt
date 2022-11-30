package com.conal.jackson

import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.core.SerializableString
import com.fasterxml.jackson.core.io.CharacterEscapes
import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.JsonSerializer
import com.fasterxml.jackson.databind.SerializerProvider
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import com.fasterxml.jackson.databind.module.SimpleModule
import java.io.IOException
import java.io.Serializable
import java.lang.Exception
import java.security.PublicKey
import java.time.Instant
import java.util.*
import javax.persistence.Column
import javax.persistence.EmbeddedId

fun main(){

    val value1 = rpcObjectMapper.readValue("null", String::class.java)
    val value2 = rpcObjectMapper.readValue("\"null\"", String::class.java)
    println(value1 == null)
    println(value2 == null)

//    Runner5().run()
//    Runner4().hide()
//    val json = """
//        {
//            "id": "conal1",
//            "number": 1234
//        }
//    """.trimIndent()
//
//    val type = rpcObjectMapper.readValue(json, MyCreateUserType::class.java)
//
//    println(type)
}

data class MyCreateUserType(
    val id: String,
    val number: Int
) {
    init {
        println("We called the init function!.")
        if(number > 1) throw Exception("No!")
    }
}

class Runner5 {
    fun run(){

        val msg = """
            {
              "rpcStartFlowRequest": {
                "clientId": "launchpad-122111",
                "flowName": "net.corda.c5template.flows.CreateCartFlow",
                "parameters": {
                  "parametersInJson": {
                      "cartName":"cartName6", 
                      "userName": "itemName4", 
                      "userId":"item6", 
                      "buyer":"C=GB, L=London, O=PartyB, OU=INC"
                      }
                }
              }
            }
        """.trimIndent()

        val messageState1 = rpcObjectMapper.readValue(msg, JsonNode::class.java)
        println(messageState1["rpcStartFlowRequest"].toString())
        println(messageState1["rpcStartFlowRequest"]["parameters"]["parametersInJson"].toString())

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

