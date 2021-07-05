package com.conal.jackson

import com.conal.general.StateStatus
import com.fasterxml.jackson.annotation.JsonTypeInfo
import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.*
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.fasterxml.jackson.module.kotlin.readValue
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.util.*

fun main(){

//    testOutWritingAnEnum()

//    testOutWritingAClass()

//    testOutWritingAnInstant()

//    testOutPetStatePojo()

//    testOutHttpItem()

//    testWhatJavaclassDotNameDoesOnConstructorReference()

//    testUnmarshallUUID()

    testJsonObjectAsString()

    val accumulator = mutableListOf<Any>()
//            var poll: Cursor.PollResult<ShareState>?
    do {
        val poll = fetch()
        accumulator.addAll(poll)
    } while (poll.size < 3)

    Runner().doSmallerTests()
}

fun testJsonObjectAsString() {
    val pet = PetStatePojo("abc", "def", "ghi", "jkl")
    val mapping = mapOf("petName" to pet)
    val written = objectMapper.writeValueAsString(mapping)
    println(written)

    val read = objectMapper.readValue<Map<String, Any>>(written)
    println(read)

    val pojo = MyCustomObjectPojo("8726660-7cdb-4fee-b8c9-c9fea484651d", 1, "someCustomField")
    val pojoStr = objectMapper.writeValueAsString(pojo)
    println(pojoStr)
    val myEmbeddedId = """
        {
            "myId": "8726660-7cdb-4fee-b8c9-c9fea484651d",
            "myIndex": 1,
            "myName": "someCustomField"
        }
    """.trimIndent()
    val id = objectMapper.readValue<MyCustomObjectPojo>(myEmbeddedId)
    println(id)


}

data class MyCustomObjectPojo(val id: String, val index: Int, val name: String)

fun testUnmarshallUUID(){
    val str = objectMapper.writeValueAsString(UUID.randomUUID())
//    val uuid = UUID.fromString("f89d24s5-124d-49e3-a903-f40ed2d21e8f")
//    println(uuid.toString())
    println(str)
    val obj = objectMapper.readValue<UUID>("\"751a6660-7cdb-4fee-b8c9-c9fea484651d\"")
//    val obj = objectMapper.readValue<UUID>(str)
    println(obj)
}

fun testWhatJavaclassDotNameDoesOnConstructorReference() {
    println(RecordFlow::Initiator::javaClass.name)
    println(RecordFlow.Initiator::class.java.name)
}

data class PetStatePojo(
    val name: String,
    val initiatorId: String,
    val owner: String,
    val linearId: String)

data class HttpItem(
    var id: String,
    var name: String?,
    var initiatorId: String,
    var timestamp: java.time.Instant
)

private fun testOutHttpItem() {
    val item = objectMapper.readValue(
        "{\"id\":\"c62115f3-1d22-40b9-b5af-8b6d70fc4302\",\"name\":\"seeded-with-name-8949c9fa-42f5-4a79-8ed3-766ebb817db5\",\"initiatorId\":\"8949c9fa-42f5-4a79-8ed3-766ebb817db5\",\"timestamp\":\"2021-06-22T21:19:05.444747Z\"}",
        HttpItem::class.java)
    println(item)
}

private fun testOutPetStatePojo() {
    val petState = objectMapper.readValue(
        "{\"name\":\"Rex 0\",\"initiatorId\":\"6b4d3f62-7206-487c-8720-e3b4a7dde253\",\"owner\":\"O=alice, L=London, C=GB\",\"linearId\":\"7f1bb200-ff66-4dc0-8351-3810162fe0b6\"}",
        PetStatePojo::class.java)
    println(petState)
}

private fun testOutWritingAnInstant() {
    val first = objectMapper.writeValueAsString(Instant.now())
    println(first)

//    val stateStatusRead = objectMapper.readValue(first, Instant::class.java)
//    val stateStatusRead = objectMapper.readValue("\"1984-04-17T00:30:00.00Z\"", Instant::class.java)
    val stateStatusRead = objectMapper.readValue("\"2021-06-23T05:24:39.322390Z\"", Instant::class.java)
    println(stateStatusRead)
}

private fun testOutWritingAClass() {
    val first = objectMapper.writeValueAsString(PetState::class.java.name)
    println(first)

    val stateStatusRead = objectMapper.readValue(first, String::class.java)
    println(stateStatusRead)
}

private fun testOutWritingAnEnum() {
    val first = objectMapper.writeValueAsString(StateStatus.UNCONSUMED)
    println(first)

    val stateStatusRead = objectMapper.readValue(first, StateStatus::class.java)
    val stateStatusRead2 = objectMapper.readValue("\"UNCONSUMED\"", StateStatus::class.java)
    println(stateStatusRead)

    val list = objectMapper.writeValueAsString(listOf(StateStatus.UNCONSUMED, StateStatus.ALL))
//    val listRead = objectMapper.re(list, StateStatus::class.java)
}

fun fetch(): List<Any> {
    return listOf(Any())
}

class Runner {

    private val objectMapper = ObjectMapper().registerModule(KotlinModule())

    fun doThis(){
        val robertDowneyJrActor = Actor(
            "Rober Downey Jr",
            1965,
            mapOf(
                Movie("Iron Man", 2008, "Action") to Rating(4.9, 2000L),
                Movie("The Avengers", 2012, "Action") to Rating(4.5, 1800L),
                Movie("Avengers: Endgame", 2019, "Action") to Rating(4.3, 1300L)
            )
        )
        val serializedRDJ = objectMapper.writeValueAsString(robertDowneyJrActor)
        println(serializedRDJ)
    }

    fun doSmallerTests(){

        println(UUID.randomUUID().toString())

        val listA = listOf("A", "B", "C")
        val listB = listOf("A", "B", "C")
        val listC = listOf("A", "C", "B")

        println(listA == listC)
        println(listA == listB)
        println(listA.containsAll(listC))

    }

    fun doThat(){

        val ratingTest = objectMapper.readValue<Rating>(objectMapper.writeValueAsString(Rating(11.1, 123L)))

        val before = RpcNamedQueryRequest("name", mapOf("stateStatus" to RpcVaultStateStatus.CONSUMED,
            "rating" to Rating(4242.0, 2121215215), "contractStateClassNames" to setOf("ContractState", "CustomState")),
            "strongstring")
        println(before)
        val json = objectMapper.writeValueAsString(before)
        println(json)
        val after = objectMapper.readValue<RpcNamedQueryRequest>(json)
        println(after) // UNCONSUMED is a String not the enum

        /*val before2 = RpcNamedQueryRequest2("name", mapOf("this" to RpcVaultStateStatus.CONSUMED, "thon" to "then"), "strongstring")
        println(before)
        val json2 = objectMapper.writeValueAsString(before)
        println(json)
        val after2 = objectMapper.readValue<RpcNamedQueryRequest>(json)
        println(after) // UNCONSUMED is a String not the enum*/

//        val namedParameters = listOf(NamedQueryParameter<RpcVaultStateStatus>("name1", RpcVaultStateStatus.UNCONSUMED),
//            NamedQueryParameter<Rating>("name2", Rating(45.0, 211123123)))
//        val before2 = RpcNamedQueryRequest2("name",
//            namedParameters,
//            RpcVaultStateStatus.UNCONSUMED,
//            "strongstring")
//        println(before2)
//        val json2 = objectMapper.writeValueAsString(before2)
//        println(json2)
//        val after2 = objectMapper.readValue<RpcNamedQueryRequest2>(json)
//        println(after2)

        // try where we have some type that takes a T and we try serialize
        val item = RpcNamedQueryResponseItem<Rating>(Rating(24.0, 1111111111))
        val itemSerialized = objectMapper.writeValueAsString(item)
        println(itemSerialized)
        val readBackIn = objectMapper.readValue<RpcNamedQueryResponseItem<Rating>>(itemSerialized)
        println(readBackIn)

        // try where we have some type Any
        val itemAny = RpcNamedQueryResponseItemAny(Rating(33.0, 333333333))
        val itemAnySerialized = objectMapper.writeValueAsString(itemAny)
        println(itemAnySerialized)
        val readAnyBackIn = objectMapper.readValue<RpcNamedQueryResponseItemAny>(itemAnySerialized)
        println(readAnyBackIn)

        // try where we have some type T out Any
        val itemOut = RpcNamedQueryResponseItemOut(Rating(33.0, 333333333))
        val itemOutSerialized = objectMapper.writeValueAsString(itemOut)
        println(itemOutSerialized)
        val readOutBackInStar = objectMapper.readValue<RpcNamedQueryResponseItemOut<*>>(itemOutSerialized)
        println(readOutBackInStar)
        val readOutBackInRating = objectMapper.readValue<RpcNamedQueryResponseItemOut<Rating>>(itemOutSerialized)
        println(readOutBackInRating)

        // deserializing a ContractState implementation
        val myState: ContractState = MyState("adowakd", MyObject(1, "myname"))
        val myStateSerialized = objectMapper.writeValueAsString(myState)
        println(myStateSerialized)
        val myStateDeserialized = objectMapper.readValue<MyState>(myStateSerialized)
        println(myStateDeserialized)

        // using response item with ContractState interface
        val myStateResponseItem = ContractStateResponseItem(MyState("adowakd", MyObject(1, "myname")))
        val myResponseItemSerialized = objectMapper.writeValueAsString(myStateResponseItem)
        println(myResponseItemSerialized)
        val myResponseItemDeserialized = objectMapper.readValue<ContractStateResponseItem>(myResponseItemSerialized)
        println(myResponseItemDeserialized)

        // using generic type parameter response item and using * in deserialzer with type info on interface
        val myStateResponseItemTyped = ContractStateResponseItemTyped<MyState>(MyState("adowakd", MyObject(1, "myname")))
        val myStateResponseItemTypedSerialized = objectMapper.writeValueAsString(myStateResponseItemTyped)
        println(myStateResponseItemTypedSerialized)
        val myStateResponseItemTypedDeserialized = objectMapper.readValue<ContractStateResponseItemTyped<MyState>>(myStateResponseItemTypedSerialized)
        println(myStateResponseItemTypedDeserialized)
    }
}

data class RpcNamedQueryResponseItem<T : Any>(val queryResult: T)
data class RpcNamedQueryResponseItemOut<out T : Any>(val queryResult: T)
data class RpcNamedQueryResponseItemAny(val queryResult: Any)

data class ContractStateResponseItem(val state: ContractState)
data class ContractStateResponseItemTyped<T : ContractState>(val state: T)

//@JsonTypeInfo(use=JsonTypeInfo.Id.CLASS, include=JsonTypeInfo.As.PROPERTY, property="@class")
interface ContractState {
    val participants: String
}

@JsonSerialize(using = MyStateSerializer::class)
@JsonDeserialize(using = MyStateDeserializer::class)
class MyState(val myField: String, val myObj: MyObject) : ContractState {
    override val participants = "dwaodwa"
}

data class MyObject (val num: Int, val name: String)

enum class RpcVaultStateStatus{
    CONSUMED, UNCONSUMED
}

class RatingSerializer : JsonSerializer<Rating>() {
    override fun serialize(value: Rating, gen: JsonGenerator, serializers: SerializerProvider) {
        gen.writeStartObject()
        gen.writeNumberField("stars", value.stars)
        gen.writeNumberField("votes", value.votes)
        gen.writeStringField("conal", value.conal)
        gen.writeEndObject()
    }
}

class RatingDeserializer : JsonDeserializer<Rating>() {
    override fun deserialize(p: JsonParser, ctxt: DeserializationContext): Rating {
        val jsonNode = p.readValueAsTree<JsonNode>()
        val starsNode = jsonNode.get("stars")
        val vtoesNode = jsonNode.get("votes")
        val conalNode = jsonNode.get("conal")
        val stars = starsNode.asDouble()
        val votes = vtoesNode.asLong()
        val conal = conalNode.asText()
        return Rating(stars, votes, conal)
    }
}

data class Actor(
    val name: String,
    val bornYear: Int,
    val movies: Map<Movie, Rating>
)

data class Movie(
    val title: String,
    val releasingYear: Int,
    val genre: String
)

//@JsonSerialize(using = RatingSerializer::class)
//@JsonDeserialize(using = RatingDeserializer::class)
data class Rating (
    val stars: Double,
    val votes: Long,
    val conal: String = "default-name-by-conal"
)

data class RpcNamedQueryRequest(
    val queryName: String,
    val namedParameters: Map<String, *>?,
    val postProcessorName: String?)

data class RpcNamedQueryRequest2(
    val queryName: String,
    val namedParameters: List<NamedQueryParameter<*>>,
    val stateStatus: RpcVaultStateStatus,
    val postProcessorName: String?)

data class NamedQueryParameter<T : Any>(val name: String, val parameter: T)

object RecordFlow {
    class Initiator constructor(private val params: String) {
        fun call() {}
    }
}