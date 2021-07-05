package com.conal.leetcode.jan2021.day8

fun main(){
    println(Day8().arrayStringsAreEqual(arrayOf("abc", "d", "defg"), arrayOf("abcddefg")))
}

class Day8{
    fun arrayStringsAreEqual(word1: Array<String>, word2: Array<String>): Boolean {
        val a = word1.reduce {s1, s2 -> s1 + s2}
        val b = word2.reduce {s1, s2 -> s1 + s2}
        return a == b
    }
}