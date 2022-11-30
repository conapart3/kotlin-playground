package postprocessors

import kotlin.reflect.KClass
import kotlin.reflect.full.createInstance

fun main() {
    val clazz = OilPreBurnPostProcessor::class.java
    val instance = clazz.kotlin.objectOrNewInstance()
    println(instance)
}

fun <T : Any> KClass<T>.objectOrNewInstance(): T {
    return this.objectInstance ?: this.createInstance()
}