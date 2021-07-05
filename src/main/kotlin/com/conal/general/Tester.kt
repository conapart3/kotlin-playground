package com.conal.general

import java.lang.IllegalArgumentException
import java.util.*

fun main(){

    val startOfFirstBatch = 1
    val sizeOfFirstBatch = 10

    println("first")
    (startOfFirstBatch..sizeOfFirstBatch).map { println(it) }
    // Create a few more pets
    val sizeOfSecondBatch = 8
    val startOfSecondBatch = sizeOfFirstBatch + 1
    println("second")
    (startOfSecondBatch until startOfSecondBatch + sizeOfSecondBatch).map { println(it) }
//    val secondBatchOfTxIds = alice.issuePets(startOfSecondBatch, startOfSecondBatch + sizeOfSecondBatch)


    val queryName = "VaultStates.findByStateStatus"
    val expectedParameters = listOf("stateStatus" to StateStatus::class.java, "name" to "java.lang.String")
    val namedParameters = mapOf<String, Any>("stateStatus" to StateStatus.UNCONSUMED, "name" to "Conal")
    println("Executing $queryName, expected named parameters are: ${expectedParameters.joinToString { "${it.first} [${it.second}]" }}")
    println("Executing $queryName, expected named parameters are: ${expectedParameters.joinToString { "${it.first} [${it.second}]" }}. Received: ${namedParameters.map {it.key}.joinToString { it }}.")

    val first = setOf("4", "2", "3", "5", "1")
    val second = setOf("4", "3", "5", "1", "2")

    println(first==second)
    println(second==first)

    val result1 = arrayOf(Object(), Object())
    val result2 = arrayOf(Object(), Object())
    val result3 = arrayOf(Object(), Object())

    val results = listOf(result1, result2, result3)

    val listOfAny: List<Any>
    listOfAny = results

    println(listOfAny)

    val u1 = UUID.randomUUID()

    val u2 = UUID.fromString(u1.toString())

    println(u1 == u2)
    /*try {
        someMethodCalls()
    } catch (e: Exception){
        throw UpperException("Upper exception caught", e)
    }*/
}

class UpperException(msg: String, cause: Throwable) : java.lang.Exception(msg, cause)
class InnerException(msg: String, cause: Throwable) : java.lang.Exception(msg, cause)

fun someMethodCalls(){
    try {
        someMethodThrows()
    }catch (e: Exception) {
        println("I caught an exception calling someMethodThrows")
        throw InnerException("I caught an exception calling someMethodThrows", e)
    }
}

fun someMethodThrows(){
    throw IllegalArgumentException()
}

enum class StateStatus{
    UNCONSUMED, ALL
}

class Tester {

}