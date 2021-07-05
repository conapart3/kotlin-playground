package com.conal.leetcode.jan2021.day17

fun main() {
    println(CountSortedVowelStrings().countVowelStrings(3))
}

class CountSortedVowelStrings {
    fun countVowelStrings(n: Int): Int {
        return ((n+1) * (n+2) * (n+3) * (n+4) ) / 24
    }
    /*val vowels = charArrayOf('a', 'e', 'i', 'o', 'u')

    fun countVowelStrings(n: Int): Int {
        return count(n, 'a')
    }

    private fun count(n:Int, lastChar: Char): Int {
        val vowelsRemaining = (vowels.size - 1) - vowels.indexOf(lastChar)
        val currentVowelIndex = vowels.indexOf(lastChar)
        var count = 0
        if(vowelsRemaining == 0){
            return count
        } else {
            for(i in currentVowelIndex until vowels.size) {
                return count(n-1, vowels[i])
            }
        }
    }*/
}
/*

fun permutation(str: String) {
    permutation("", str)
}

fun permutation(prefix: String, str: String) {
    val n = str.length
    if(n==0) println(prefix)
    else {
        for (i in 0 until n){
            permutation(prefix + str[i], str.substring(0, i) + str.substring(i+1, n))
        }
    }
}*/
