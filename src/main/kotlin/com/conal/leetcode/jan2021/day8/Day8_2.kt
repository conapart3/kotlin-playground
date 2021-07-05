package com.conal.leetcode.jan2021.day8

import java.lang.StringBuilder

fun main(){
    println(Day8_2().arrayStringsAreEqual(arrayOf("abc", "d", "defg"), arrayOf("abcddefg")))
}

class Day8_2{
    fun arrayStringsAreEqual(word1: Array<String>, word2: Array<String>): Boolean {
        val a = StringBuilder()
        val b = StringBuilder()

        for(s in word1){
            a.append(s)
        }

        for(s in word2){
            b.append(s)
        }

        if(a.length != b.length)
            return false

        return a.toString().equals(b.toString())
    }
}