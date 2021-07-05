package com.conal.leetcode.jan2021.day23

import java.util.*
import kotlin.collections.HashMap

fun main() {
    val matrix: Array<IntArray> = arrayOf(intArrayOf(3, 3, 1, 1), intArrayOf(2, 2, 1, 2), intArrayOf(1, 1, 1, 2))
    println(Day23().diagonalSort(matrix))
}

class Day23 {
    // use a hashmap of heaps
    fun diagonalSort(mat: Array<IntArray>): Array<IntArray> {
        val n = mat.size
        val m = mat[0].size
        // hashmap of min heaps, (diagonal index : min heap with the values)
        val diagonals: HashMap<Int, PriorityQueue<Int>> = HashMap()
        // build min heaps
        for(i in 0 until n) {
            for(j in 0 until m) {
                //00 | 01 | 02 | 10 | 11 | 12 | 20 | 21 | 22 | 30 | 31 | 32
                //0:3 | 1:2 | 2:1 | 1:3 | 0:2 | 1:1 | 2:1 | 1:1 | 0:1 | 3:1 | 2:2 | 1:2 |
                diagonals.putIfAbsent(i-j, PriorityQueue())
                diagonals[i-j]!!.add(mat[i][j])
            }
        }
        // build output
        for(i in 0 until n) {
            for (j in 0 until m) {
                mat[i][j] = diagonals[i-j]!!.poll()
            }
        }
        return mat
    }
}