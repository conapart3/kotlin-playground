/*
package com.conal.leetcode.sept2021.day6

import java.util.Comparator

fun main() {
    println(SlowestKey().slowestKey(intArrayOf(9, 29, 49, 50), "cbcd"))
}

class SlowestKey {

    fun slowestKey(releaseTimes: IntArray, keysPressed: String): Char {
        return bruteforceApproach(releaseTimes, keysPressed)
    }

    private fun bruteforceApproach(releaseTimes: IntArray, keysPressed: String): Char {
        val keyTimes = mutableMapOf(keysPressed[0] to releaseTimes[0])
        for(i in 1 until keysPressed.length) {
            val key = keysPressed[i]
            val time = keysPressed[i] - keysPressed[i-1]

            val timeIfKeyAlreadyPressed = keyTimes[key]
            if(timeIfKeyAlreadyPressed == null) {
                keyTimes[key] = time
            } else {
                keyTimes[key] = maxOf(timeIfKeyAlreadyPressed, time)
            }
        }
        val max = keyTimes.toList().sortedBy { (_, value) -> value }.toMap()
        return max.key
    }
}*/
