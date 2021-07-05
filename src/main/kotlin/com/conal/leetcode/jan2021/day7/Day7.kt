package com.conal.leetcode.jan2021.day7

import kotlin.math.max

fun main(){
    println(Day7().lengthOfLongestSubstring("pwwkew"))
}

class Day7 {
    fun lengthOfLongestSubstring(s: String): Int {
        var ans = 0
        val n = s.length
        var i = 0
        var j = 0
        val encounteredSet = mutableSetOf<Char>()
        while (i < n && j < n) {
            if (encounteredSet.contains(s[j])) {
                encounteredSet.remove(s[i])
                i += 1
            } else {
                encounteredSet.add(s[j])
                j += 1 // increment before doing the max check
                ans = Math.max(ans, (j - i))
            }
        }
        return ans
    }
}