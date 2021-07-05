package com.conal

import com.conal.corda.MyState
import com.conal.corda.uncheckedCast
import com.conal.main.ContractState
import java.time.Instant
import kotlin.reflect.full.isSubclassOf

class ClassCasting<T : Any>(
    private val resultClass: Class<T>,
    private var resultCounter: Long
) {

    fun createPositionedValuesAndIncrementResultCounter(results: List<Any?>): List<FlowPositionedValueImpl<T>> {
        val effectiveClass = resultClass.kotlin.javaObjectType
        return results
            .map {
                /*if (it != null && !effectiveClass.isInstance(it)) {
                    val message = "Expected query results to be of type $resultClass but found ${it::class.java}."
                    println(message)
                    throw Exception(message)
                }
                it::class.isSubclassOf(resultClass)*/
                val positionedValue = FlowPositionedValueImpl<T>(uncheckedCast(it), resultCounter)
                resultCounter++
                positionedValue
            }
    }
}

fun main() {
    val now = Instant.now()
    val list = listOf(now.minusSeconds(4001), now.minusSeconds(2002), now.minusSeconds(3003))
    println(list)
    println(list.maxOf { it })

    val wrappedItems = runTest(SomeOtherClass::class.java, listOf(SomeClass(), SomeClass(), null), "SomeOtherClass with someClasses")
    wrappedItems.forEach { it.value.doSomething() }


    runTest(Boolean::class.java, listOf(null, false), "Boolean with true and false")
    runTest(Long::class.java, listOf(Long.MAX_VALUE, Long.MIN_VALUE, 3L), "Long with Longs")
    runTest(Int::class.java, listOf(1, null, 3), "Int with ints")
    runTest(String::class.java, listOf("1", "2L", ""), "String with stringss")
    runTest(ContractState::class.java, listOf(MyState("1"), MyState("2")), "ContractState with MyState")
    runTest(Array::class.java, listOf(null, arrayOf(1, 2), arrayOf("3", "4")), "Array with arrays of ints")
    runTest(IntArray::class.java, listOf(intArrayOf(1, 2), intArrayOf(3, 4)), "IntArray with intArrays of ints")
}

fun <T : Any> runTest(resultClass: Class<T>, list: List<Any?>, name: String? = ""): List<FlowPositionedValueImpl<T>> {

    val runner = ClassCasting(resultClass, 0)
    println("$name test: ")
    val successfully = runner.createPositionedValuesAndIncrementResultCounter(list)
    successfully.forEach {
        println(it)
    }
    println("")
    return successfully
}

data class FlowPollResultImpl<R>(
    val positionedValues: List<FlowPositionedValueImpl<R>>,
    val rawItems: List<R>,
    val isEmpty: Boolean,
    val firstPosition: Long,
    val lastPosition: Long,
    val remainingElementsCountEstimate: Long?,
    val lastResult: Boolean
)

data class FlowPositionedValueImpl<R>(
    val value: R,
    val position: Long
)

class SomeClass() {
    fun doSomething() {
        println("doing something")
    }
}
class SomeOtherClass() {
    fun doSomething() {
        println("doing something else")
    }
}

/*val effectiveClass = resultClass.kotlin.javaObjectType
val filteredByEffectiveClass = results.filterIsInstance(effectiveClass)
if(filteredByEffectiveClass.size == results.size){
    println("Filtered by effective class ${resultClass.kotlin.javaObjectType}")
    return results.map {
        val positionedValue = FlowPositionedValueImpl<T>(uncheckedCast(it), resultCounter)
        resultCounter++
        positionedValue
    }
}*/

/*val filteredByInstance = results.filterIsInstance(resultClass)
if(filteredByInstance.size == results.size){
    println("Effective class filtering didn't work, filtered by resultClass $resultClass")
    return results.map {
        val positionedValue = FlowPositionedValueImpl<T>(uncheckedCast(it), resultCounter)
        resultCounter++
        positionedValue
    }
}*/