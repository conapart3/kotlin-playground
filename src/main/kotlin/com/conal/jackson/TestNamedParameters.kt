package com.conal.jackson

import com.google.gson.Gson
import java.time.Instant
import java.util.UUID

fun main() {
    val jsonMarshallingService = JsonMarshallingServiceImpl(objectMapper)
    testMap(jsonMarshallingService)
    testWithInstant(jsonMarshallingService)
    testWithUUIDs(jsonMarshallingService)
    testWithCustomDataClass(jsonMarshallingService)
}

fun testWithCustomDataClass(jsonMarshallingService: JsonMarshallingServiceImpl) {

    val someList = listOf<Any?>("str1", null, "str2", null)
    someList.map {  }



    val nullJson = jacksonObjectMapper().writeValueAsString(emptyList<PetStatePojo>())
    println(nullJson)
    val nullPet = jacksonObjectMapper().readValue(nullJson, PetStatePojo::class.java)

    val gsonPet = Gson().fromJson(nullJson, PetStatePojo::class.java)

    println("[\"${listOf("first", "second").joinToString("\", \"")}\"]")

    val myCustomPojo = MyCustomObjectPojo("8726660-7cdb-4fee-b8c9-c9fea484651d", 1, "someCustomField")
    val pojoStr = objectMapper.writeValueAsString(myCustomPojo)
    println(pojoStr)

    val map = mapOf(
        "myCustomObj" to "{\"id\":\"8726660-7cdb-4fee-b8c9-c9fea484651d\",\"index\":1,\"name\":\"someCustomField\"}"
    )

    val pojo = jsonMarshallingService.parseJson(map["myCustomObj"] ?: throw Exception("didn't exist"), MyCustomObjectPojo::class.java)
    println(pojo)

    val myCustomPojo2 = MyCustomObjectPojo("6245660-7cdb-4fee-b8c9-c9fea484651d", 2, "someCustomField2")
    val pojoListStr = objectMapper.writeValueAsString(listOf(myCustomPojo, myCustomPojo2))
    println(pojoListStr)

    val map2 = mapOf("myCustomObj" to """
        [{
            \"id\":\"8726660-7cdb-4fee-b8c9-c9fea484651d\",
            \"index\":1,
            \"name\":\"someCustomField1\"
        },
        {
            \"id\":\"6245660-7cdb-4fee-b8c9-c9fea484651d\",
            \"index\":2,
            \"name\":\"someCustomField2\"
        }
        ]""".trimIndent())

//    val pojoList = jsonMarshallingService.parseJsonCollection(map2["myCustomObj"] ?: throw Exception("didn't exist"), MyCustomObjectPojo::class.java)
//    println(pojoList)

    val manMadeString2 = "[{\"id\":\"8726660-7cdb-4fee-b8c9-c9fea484651d\",\"index\":1,\"name\":\"someCustomField\"},{\"id\":\"6245660-7cdb-4fee-b8c9-c9fea484651d\",\"index\":2,\"name\":\"someCustomField2\"}]"
    val manMadeMap = mapOf("two" to manMadeString2)
    val pojoList2 = jsonMarshallingService.parseJsonCollection(manMadeMap["two"] ?: throw Exception("didn't exist"), MyCustomObjectPojo::class.java)
    println(pojoList2)
}

fun testWithUUIDs(jsonMarshallingService: JsonMarshallingServiceImpl) {
    val map = mapOf(
        "uuids" to "[\"531a6660-7cdb-4fee-b8c9-c9fea484651d\", \"751a6660-7cdb-4fee-b8c9-c9fea484651d\"]"
    )

    val uuids = jsonMarshallingService.parseJsonCollection(map["uuids"] ?: throw Exception("didn't exist"), UUID::class.java)
    println(uuids)
}
fun testWithInstant(jsonMarshallingService: JsonMarshallingServiceImpl) {
    val map = mapOf(
        "startInstant" to "\"2021-06-23T05:24:39.322390Z\""
    )

    val instant = jsonMarshallingService.parseJson(map["startInstant"] ?: throw Exception("didn't exist"), Instant::class.java)
    println(instant)
}

fun testMap(jsonMarshallingService: JsonMarshallingServiceImpl) {
    val map = mapOf(
        "stateStatus" to "\"UNCONSUMED\"",
        "contractStateClassNames" to "[\"com.mycordapp.MyState\", \"com.mycordapp.MyOtherState\"]"
    )

    val stateStatus = jsonMarshallingService.parseJson(map["stateStatus"] ?: throw Exception("didn't exist"), StateStatus::class.java)
    val contractStateClassNames = jsonMarshallingService.parseJsonCollection(map["contractStateClassNames"] ?: throw Exception("didn't exist"), String::class.java)

    println(stateStatus)
    println(contractStateClassNames)
}

enum class StateStatus { UNCONSUMED, CONSUMED }
