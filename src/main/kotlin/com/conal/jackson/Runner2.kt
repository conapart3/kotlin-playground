package com.conal.jackson

import com.fasterxml.jackson.annotation.JsonTypeInfo
import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.*
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.fasterxml.jackson.module.kotlin.readValue
import java.io.File

fun main() {
    Runner2().run()
}

class Runner2 {
    private val objectMapper = ObjectMapper().registerModule(KotlinModule())

    fun run(){
        println("/conal/vault-queries/def".replace("/", File.separator))
        println("\\conal\\vault-queries\\def".replace("\\", File.separator))

        val req = Request<Rating>("name", mapOf(), null, null, Rating::class.java)
        val reqSerialized = objectMapper.writeValueAsString(req)
        println(reqSerialized)
        val reqDeserialized = objectMapper.readValue<Request<Rating>>(reqSerialized)
        println(reqDeserialized)
        val reqDeserializedWithClass = objectMapper.readValue(reqSerialized, Request::class.java)
        println(reqDeserializedWithClass) // works!

        val req2 = Request<StateAndRef<ContractState>>("name", mapOf(), null, null, StateAndRef::class.java)
        val reqSerialized2 = objectMapper.writeValueAsString(req2)
        println(reqSerialized2)
        // Exception in thread "main" com.fasterxml.jackson.module.kotlin.MissingKotlinParameterException: Instantiation of [simple type, class com.conal.jackson.StateAndRef] value failed for JSON property state due to missing (therefore NULL) value for creator parameter state which is a non-nullable type
//        val reqDeserialized2 = objectMapper.readValue<StateAndRef<ContractState>>(reqSerialized2) // this doesn't work runtime excpeiton!
//        println(reqDeserialized2)
//        val reqDeserializedWithClass2 = objectMapper.readValue(reqSerialized2, StateAndRef::class.java) // same as this
//        println(reqDeserializedWithClass2)

        // this shows how using the Class<out T> can help jackson deserialize into the correct object.
        // edit - it actually can't help with this.
        val req3 = RpcGenericNamedQueryResponseItem<Rating>(Rating(2.0, 214124, "conalsthingy"), Rating::class.java)
        val reqSerialized3 = objectMapper.writeValueAsString(req3)
        println(reqSerialized3)
        // this works - maybe only because Rating has a custom serializer / deserializer?
        val reqDeserialized3 = objectMapper.readValue<RpcGenericNamedQueryResponseItem<*>>(reqSerialized3)
        println(reqDeserialized3)
        val reqDeserializedWithClass3 = objectMapper.readValue(reqSerialized3, RpcGenericNamedQueryResponseItem::class.java)
        println(reqDeserializedWithClass3)

        // this shows how jackson cannot work out the class type T without Class<out T> as a field in the response item.
        val req4 = RpcGenericNamedQueryResponseItemWithoutResultClass<Rating>(Rating(2.0, 214124, "conalsthingy"))
        val reqSerialized4 = objectMapper.writeValueAsString(req4)
        println(reqSerialized4)
        val reqDeserialized4 = objectMapper.readValue<RpcGenericNamedQueryResponseItemWithoutResultClass<*>>(reqSerialized4)
        println(reqDeserialized4)
        val reqDeserializedWithClass4 = objectMapper.readValue(reqSerialized4, RpcGenericNamedQueryResponseItemWithoutResultClass::class.java)
        println(reqDeserializedWithClass4)


        // this shows how jackson cannot work out the class type T without Class<out T> as a field in the response item.
        val req5 = RpcVaultQueryContractStateResponseItem<MyState>(MyState("conalsthingy", MyObject(123, "name")))
        val reqSerialized5 = objectMapper.writeValueAsString(req5)
        println(reqSerialized5)
        val reqDeserialized5 = objectMapper.readValue<RpcVaultQueryContractStateResponseItem<MyState>>(reqSerialized5)
        println(reqDeserialized5)
        // doesn't work runtime cannot construct instance no createors...
//        val reqDeserializedWithClass5 = objectMapper.readValue(reqSerialized5, RpcVaultQueryContractStateResponseItem::class.java)
//        println(reqDeserializedWithClass5)


        // this shows how jackson cannot work out the class type T without Class<out T> as a field in the response item.
        val req6 = RpcVaultQueryContractStateResponseItemWithResultClass<MyState>(MyState("conalsthingy", MyObject(123, "name")), MyState::class.java)
        val reqSerialized6 = objectMapper.writeValueAsString(req6)
        println(reqSerialized6)
        // doesn't work unless a custom deserializer for myState?
        val reqDeserialized6 = objectMapper.readValue<RpcVaultQueryContractStateResponseItemWithResultClass<MyState>>(reqSerialized6)
        println(reqDeserialized6)
        // doesn't work
//        val reqDeserializedWithClass6 = objectMapper.readValue(reqSerialized6, RpcVaultQueryContractStateResponseItemWithResultClass::class.java)
//        println(reqDeserializedWithClass6)

        val req7 = RpcGenericNamedQueryResponseItem<MyState>(MyState("conalsthingy", MyObject(123, "name")), MyState::class.java)
        val reqSerialized7 = objectMapper.writeValueAsString(req7)
        println(reqSerialized7)
        // doesn't work unless a custom deserializer for myState?
        val reqDeserialized7 = objectMapper.readValue<RpcGenericNamedQueryResponseItem<*>>(reqSerialized7)
        println(reqDeserialized7)
        val reqDeserializedWithClass7 = objectMapper.readValue(reqSerialized7, RpcGenericNamedQueryResponseItem::class.java)
        println(reqDeserializedWithClass7)
    }
}

data class Request<T>(
    val queryName: String,
    val namedParameters: Map<String, Any>,
    val postFilter: RpcVaultQueryFilterRequest?,
    val postProcessorName: String?,
    val resultType: Class<out T>)

data class RpcVaultQueryFilterRequest(val name: String)
data class StateAndRef<out T : ContractState>(val state: TransactionState<T>, val ref: StateRef)
typealias ContractStateName = String
data class TransactionState<out T : ContractState>(val data: T, val contract: ContractStateName = "contract", val encumbrance: Int? = null, val notary: String = "notary")
// using this works when deserializing Rpc..Item<*> because * is same as Any?
data class RpcGenericNamedQueryResponseItem<T : Any>(val queryResult: T, val resultClass: Class<T>)
data class RpcGenericNamedQueryResponseItemWithoutResultClass<out T : Any>(val queryResult: T)
data class RpcVaultQueryContractStateResponseItem<out T : ContractState>(val queryResult: T)
data class RpcVaultQueryContractStateResponseItemWithResultClass<T : ContractState>(val queryResult: T, val resultClass: Class<T>)
data class StateRef(val txhash: String, val index: Int)


///////////////// CUSTOM SERIALIZERS

class MyStateSerializer : JsonSerializer<MyState>() {
    override fun serialize(value: MyState, gen: JsonGenerator, serializers: SerializerProvider) {
        gen.writeStartObject()
        gen.writeStringField("myField", value.myField)
        gen.writeFieldName("myObj")
        gen.writeStartObject("myObj")
        gen.writeStringField("name", value.myObj.name)
        gen.writeNumberField("num", value.myObj.num)
        gen.writeEndObject()
        gen.writeEndObject()
    }
}

class MyStateDeserializer : JsonDeserializer<MyState>() {
    override fun deserialize(p: JsonParser, ctxt: DeserializationContext): MyState {
        val jsonNode = p.readValueAsTree<JsonNode>()
        val myFieldNode = jsonNode.get("myField")
        val myObjNode = jsonNode.get("myObj")
        val myObjNameNode = myObjNode.get("name")
        val myObjNumNode = myObjNode.get("num")
        return MyState(myFieldNode.asText(), MyObject(myObjNumNode.asInt(), myObjNameNode.asText()))
    }
}

