package com.conal.collections

fun main(){

    val nullArray = arrayOfNulls<SomeObject>(3)
    nullArray[2] = SomeObject(3, "third")
    nullArray[1] = SomeObject(2, "second")
    nullArray[0] = SomeObject(1, "first")

    val new2 = nullArray.map { it }

    println(new2)
    println(nullArray)

    // if I go to change the original array will it change the new2 list?
    nullArray[2] = SomeObject(5, "fifth!")
    println(new2)
}

data class SomeObject(val num: Int, val name: String)