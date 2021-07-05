package com.conal.leetcode.jan2021.day3

fun main() {
    println(BASolution().countArrangement(15))
}

class BASolution {

    val permutations = mutableListOf<IntArray>()

    fun countArrangement(n: Int): Int {
        var orig = IntArray(n) { i -> i + 1 }
        permutations(orig)
        return countBeautifulPermutations(permutations)
    }

    private fun permutations(arr: IntArray) {
        permutations(intArrayOf(), arr)
    }

    private fun permutations(prefix: IntArray, arr: IntArray) {
        val n = arr.size
        if (n == 0) permutations.add(prefix)
        else {
            for (i in 0 until n) {
                val newPrefix = prefix + arr[i]
                if(isBeautiful(newPrefix))
                    permutations(newPrefix, arr.copyOfRange(0, i) + arr.copyOfRange(i + 1, n))
            }
        }
    }

    private fun isBeautiful(arr: IntArray): Boolean {
        for (i in arr.indices) {
            if ((arr[i]) % (i + 1) != 0 && (i + 1) % (arr[i]) != 0) return false
        }
        return true
    }

    private fun countBeautifulPermutations(permutations: MutableList<IntArray>): Int {
        var count = 0
        perm@ for (arr in permutations) {
            if(!isBeautiful(arr)) continue@perm
            count++
        }
        return count
    }
}

