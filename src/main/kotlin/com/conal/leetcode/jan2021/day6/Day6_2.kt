package com.conal.leetcode.jan2021.day6

class Day6_2 {
    fun findKthPositive2(arr: IntArray, k: Int): Int {
        val missing = IntArray(arr.size)

        arr.forEachIndexed{ index, value ->
            missing[index] = value - (index+1)
        }

        return arr.last() - (missing.last() - k + 1)
    }
}

fun main() {
    println(Day6_2().findKthPositive2(intArrayOf(2, 3, 4, 7, 11, 12), 1))
}