package com.conal.jackson

import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.*
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import com.fasterxml.jackson.databind.module.SimpleModule
import com.fasterxml.jackson.module.kotlin.KotlinModule
import java.lang.reflect.ParameterizedType
import java.util.*
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule

fun main() {
    Runner3().runWithInterfaceAbstraction()
}

class Runner3 {
    //    private val objectMapper = ObjectMapper().registerModule(KotlinModule())
    fun runWithInterfaceAbstraction() {
        (1..100).map{println(it)}

        val li = listOf(1, 2, 3)
        li[5]
//        val read = objectMapper.readValue<NamedQueryResultHolder<*>>(str)
//        println(read)

        val myState = MyState("conalsthingy", MyObject(123, "name"))
        val rating = Rating(4.5, 1800L)
        val myStateJson = objectMapper.writeValueAsString(myState)
        val ratingJson = objectMapper.writeValueAsString(rating)
        val pairs = listOf(MyState::class.java to myStateJson, Rating::class.java to ratingJson)
        val deserMyState = objectMapper.readValue(pairs[0].second, pairs[0].first)
        val deserRating = objectMapper.readValue(pairs[1].second, pairs[1].first)
        println(deserMyState)
        println(deserRating)


        val states =
            listOf(MyState::class.java to myState, MyState::class.java to myState, MyState::class.java to myState)
        val statesInClass = listOf(
            ContractStateHttpWrapper(MyState::class.java, myState),
            ContractStateHttpWrapper(MyState::class.java, myState),
            ContractStateHttpWrapper(MyState::class.java, myState)
        )
        val json = objectMapper.writeValueAsString(states)
        val json2 = objectMapper.writeValueAsString(statesInClass)
        println(json)
        println(json2)

        val read1 = objectMapper.readValue(json, List::class.java)
        val read2 = objectMapper.readValue(json2, List::class.java)


    }
}

fun jacksonObjectMapper() = ObjectMapper().apply {
    registerModule(KotlinModule())
//    registerModule(JavaTimeModule())
    enable(MapperFeature.BLOCK_UNSAFE_POLYMORPHIC_BASE_TYPES)
    enable(DeserializationFeature.FAIL_ON_READING_DUP_TREE_KEY)
    setTimeZone(TimeZone.getTimeZone("UTC"))
    disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
}

internal val objectMapper = jacksonObjectMapper().apply {
    val module = SimpleModule()
//    module.addDeserializer(DurableQueryCursor.PollResult.PositionedValue::class.java, PositionedValueDeserializer)
//    module.addDeserializer(SecureHash::class.java, SecureHashDeserializer)
//    module.addDeserializer(CordaX500Name::class.java, CordaX500NameDeserializer)
//    module.addDeserializer(CordaX500Name::class.java, CordaX500NameDeserializer)
    registerModule(module)
    registerModule(JavaTimeModule())
    disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
}

data class ContractStateHttpWrapper(
    val clazz: Class<*>,
    val contractState: ContractState)


interface NamedQueryResultHolder<T> {
    val result: T
}

class CustomObjectResult(override val result: Rating) : NamedQueryResultHolder<Rating>

@JsonSerialize(using = MyStateResultSerializer::class)
@JsonDeserialize(using = MyStateResultDeserializer::class)
class MyStateResult(override val result: MyState) : NamedQueryResultHolder<MyState>



//class ContractStateSerializer : JsonSerializer<DeserializableContractState>() {
//    override fun serialize(value: DeserializableContractState, gen: JsonGenerator, serializers: SerializerProvider) {
//        gen.writeStartObject()
//        gen.writeObjectField("class", value.clazz)
//        gen.writeObjectField("contractState", value.contractState)
//        gen.writeEndObject()
//    }
//}
//
//class ContractStateHttpWrapperDeserializer : JsonDeserializer<ContractStateHttpWrapper>() {
//    override fun deserialize(p: JsonParser, ctxt: DeserializationContext): ContractStateHttpWrapper {
//        val parsingContext = p.parsingContext
//        parsingContext.
//        val jsonNode = p.readValueAsTree<JsonNode>()
//        val classNode = jsonNode.get("clazz")
//        classNode
//        val myObjNode = myStateNode.get("myObj")
//        val myObjNameNode = myObjNode.get("name")
//        val myObjNumNode = myObjNode.get("num")
//        return MyStateResult(MyState(myFieldNode.asText(), MyObject(myObjNumNode.asInt(), myObjNameNode.asText())))
//    }
//}

class MyStateResultSerializer : JsonSerializer<MyStateResult>() {
    override fun serialize(value: MyStateResult, gen: JsonGenerator, serializers: SerializerProvider) {
        gen.writeStartObject()
        gen.writeFieldName("myState")
        gen.writeStartObject()
        gen.writeStringField("myField", value.result.myField)
        gen.writeFieldName("myObj")
        gen.writeStartObject("myObj")
        gen.writeStringField("name", value.result.myObj.name)
        gen.writeNumberField("num", value.result.myObj.num)
        gen.writeEndObject()
        gen.writeEndObject()
        gen.writeEndObject()
    }
}

class MyStateResultDeserializer : JsonDeserializer<MyStateResult>() {
    override fun deserialize(p: JsonParser, ctxt: DeserializationContext): MyStateResult {
        val jsonNode = p.readValueAsTree<JsonNode>()
        val myStateNode = jsonNode.get("myState")
        val myFieldNode = myStateNode.get("myField")
        val myObjNode = myStateNode.get("myObj")
        val myObjNameNode = myObjNode.get("name")
        val myObjNumNode = myObjNode.get("num")
        return MyStateResult(MyState(myFieldNode.asText(), MyObject(myObjNumNode.asInt(), myObjNameNode.asText())))
    }
}


enum class HttpVerb {
    GET,
    POST
}

data class WebRequest<T>(val path: String, val body: T? = null, val queryParameters: Map<String, Any?>? = null)
data class WebResponse<T>(
    val body: T?,
    val headers: Map<String, String>,
    val responseStatus: Int,
    val responseStatusText: String?
)

/**
 * [RemoteClient] implementations are responsible for making remote calls to the server and returning the response,
 * after potentially deserializing.
 *
 */
interface RemoteClient {
    fun <T, R : Any> call(
        verb: HttpVerb,
        webRequest: WebRequest<T>,
        responseClass: Class<R>,
        userName: String = "",
        password: String = ""
    ): WebResponse<R>

    fun <T> call(
        verb: HttpVerb,
        webRequest: WebRequest<T>,
        responseType: ParameterizedType,
        userName: String,
        password: String
    ): WebResponse<Any>

    fun <T> call(
        verb: HttpVerb,
        webRequest: WebRequest<T>,
        userName: String = "",
        password: String = ""
    ): WebResponse<String>

    val baseAddress: String
}