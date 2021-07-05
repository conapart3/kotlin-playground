package com.conal.deepimmutability

import com.conal.corda.NonEmptySet
import com.conal.deepimmutability.persistence.FiniteNamedQueryIterator
import java.time.Instant
import java.util.*

fun main() {
    // Q1: can we create a stream with nulls? - YES
    val list = listOf(1, null, 3)
    list.stream()
    println(list)

    // Q2: can you cast a stream with nulls into a non nullable list type? - NO
    val nonNullableList: MutableList<Int> = mutableListOf()
//    nonNullableList.addAll(list)

    // Q3: queue of non-nullable by generic type, trying to add list with nulls. It adds nulls, and gets exception at runtime!
//    val iterator = FiniteNamedQueryIterator<Int>(LinkedList(), Int::class.java)
//    iterator.repopulateQueue(list)
//    println(iterator.queue)
//    iterator.forEach {
//        println(it)
//    }

    // Q4 - what about putting stuff in of a different type? Runtime exception when using forEach
    val iterator = FiniteNamedQueryIterator<Int>(LinkedList(), Int::class.java)
    val otherList = listOf(1, "two", 3, "four")
    println("Repopulating queue, an exception here indicates error when repopulating queue")
    iterator.repopulateQueue(otherList)
    println(iterator.queue)

    println("Printing using forEach: Exception below indicates runtime when accessing elements")
    iterator.myForEach {
        println(it)
    }

    // Q5 = what about stuff of different types if
    val numberIterator = FiniteNamedQueryIterator<Number>(LinkedList(), Number::class.java)
    val numbers = listOf(1, 2L, 3.0, 4f)
    numberIterator.repopulateQueue(numbers)
    println(numberIterator.queue)
    println("Printing using forEach: Exception below indicates runtime when accessing elements")
    numberIterator.myForEach {
        println(it)
    }

    val inheritanceList = listOf(A("A"), B(21), C("oval"), D("round"))
    val inheritanceIterator = FiniteNamedQueryIterator<A>(LinkedList(), A::class.java)
    inheritanceIterator.repopulateQueue(inheritanceList)
    println(inheritanceIterator.queue)
    println("Printing using forEach: Exception below indicates runtime when accessing elements")
    inheritanceIterator.myForEach {
        println(it)
    }
}

open class A(val name: String)
class B(val age: Int): A("B")
open class C(val face: String): A("C")
class D(val favourite: String): C("Dface")


data class SetBasedVaultQueryFilter(
    val txIds: NonEmptySet<String>? = null,
    val startTimestamp: Instant? = null,
    val endTimestamp: Instant? = null,
    val contractStateClassNames: NonEmptySet<String>? = null
) {
    class Builder private constructor(private val instanceBeingBuilt: SetBasedVaultQueryFilter) {

        constructor() : this(
            SetBasedVaultQueryFilter(
                null, null, null,
                null
            )
        )

        fun withTxIds(txIds: NonEmptySet<String>): Builder =
            Builder(instanceBeingBuilt.copy(txIds = txIds))

        fun withStartTimestamp(startTimestamp: Instant): Builder =
            Builder(instanceBeingBuilt.copy(startTimestamp = startTimestamp))

        fun withEndTimestamp(endTimestamp: Instant): Builder =
            Builder(instanceBeingBuilt.copy(endTimestamp = endTimestamp))

        fun withContractStateClassNames(contractStateClassNames: NonEmptySet<String>): Builder =
            Builder(instanceBeingBuilt.copy(contractStateClassNames = contractStateClassNames))

        fun build(): SetBasedVaultQueryFilter {
            return instanceBeingBuilt
        }
    }
}

