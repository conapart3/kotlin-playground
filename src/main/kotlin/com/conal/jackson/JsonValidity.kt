package com.conal.jackson

import com.fasterxml.jackson.module.kotlin.readValue
import com.google.gson.Gson

class JsonValidity {
}

fun main() {
    val json = """
            |{ 
            | "address" : "add", 
            | "valuation" : { 
            | "amount" : ${123L * 123.45}, 
            | "type" : "usd" 
            | } ,
            | "linearId" : "myId"
            | }
            | """.trimMargin()

    val housePojo = objectMapper.readValue<HousePojo>(json)
    println(housePojo)

    val gsonHouse = Gson().fromJson(json, HousePojo::class.java)
    println(gsonHouse)
}

data class HouseValuationPojo(
    val amount: Double,
    val type: String
)

data class HousePojo(
    val address: String,
    val valuation: HouseValuationPojo,
    val linearId: String
)