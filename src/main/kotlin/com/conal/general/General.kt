package com.conal.general

import com.conal.corda.*
import java.util.stream.Stream
import kotlin.streams.toList





fun main() {
    fun <I, R> postProcess(queryResult: List<Any>, postProcessor: GenericQueryPostProcessor<I, R>): Stream<R> {
        return if (postProcessor is StateAndRefPostProcessor) {
            postProcessor.postProcess(uncheckedCast(queryResult.stream()))
        } else {
            Stream.empty()
        }
    }

    val queryResult = listOf(
        StateAndRef(TransactionState(MyState("first"))),
        StateAndRef(TransactionState(PetState("second", "pet2"))),
        StateAndRef(TransactionState(MyState("third"))))
    val result = postProcess(queryResult, PetPostProcessor())
    println(result.toList())

    /////////////////////////////////////////////////////

    val kclass = StateAndRefPostProcessor::class
    val ppp = PetPostProcessor()
    val it = ppp::class

    println(kclass::class.java)
    println(ppp::class.java)
    println(it::class.java)
    println(kclass::class.java.isAssignableFrom(ppp::class.java))
    println(kclass::class.java.isAssignableFrom(it::class.java))

    tryInstantiateClassesImplementingWithClassVersionCheckAndIgnoreNonPublicOrInnerClasses(StateAndRefPostProcessor::class.java)
}


inline fun <reified T : Any> tryInstantiateClassesImplementingWithClassVersionCheckAndIgnoreNonPublicOrInnerClasses(type: Class<T>) {
    val loaded = PetPostProcessor()::class.java
    if(type::class.java.isAssignableFrom(loaded::class.java)){
        println("Conal - `${type}` assignable from `${loaded::class.java.name}`")
    } else {
        println("Conal - `${type}` not assignable from `${loaded::class.java.name}`")
    }
}