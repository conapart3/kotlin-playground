package com.conal.leetcode.jan2021

import com.conal.leetcode.jan2021.day3.BASolution
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class BASolutionTest {

    @Test
    fun countArrangement() {
        assertEquals(2, BASolution().countArrangement(2))
        assertEquals(1, BASolution().countArrangement(1))
        assertEquals(2, BASolution().countArrangement(15))
    }
}