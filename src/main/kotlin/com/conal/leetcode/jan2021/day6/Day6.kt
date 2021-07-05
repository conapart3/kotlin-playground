package com.conal.leetcode.jan2021.day6

class Day6 {
    fun findKthPositive(arr: IntArray, k: Int): Int {
        val missing: MutableList<Int> = mutableListOf()

        var pred = 0
        var i = 0
        while (missing.size < k) {
            if (i == arr.size) {
                // last item, and k >= missing.size, so work out kth element
                // arr=[1 2 3 5] k=2 missing=[4], 5+(2-1)=6
                // arr=[2 5] k=6 missing=[1 3 4], 5+(6-3)=8
                return arr.last() + (k - missing.size)
                //todo what if arr.last()=1000
            }
            if (arr[i] - pred > 1) {
                // numbers missing. Find them
                var temp = pred + 1
                while (temp != arr[i]) {
                    missing.add(temp)
                    temp++
                }
            }
            pred = arr[i]
            i++
        }
        return missing[k-1]
    }
}

fun main() {
    println(Day6().findKthPositive(intArrayOf(2,5,9), 1))
}