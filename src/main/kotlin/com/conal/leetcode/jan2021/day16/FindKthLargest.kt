package com.conal.leetcode.jan2021.day16

fun main(){
    println(FindKthLargest().findKthLargest(intArrayOf(3,2,1,5,6,4), 2))
    println(FindKthLargest().findKthLargest(intArrayOf(3,2,3,1,2,4,5,5,6), 4))
}
class FindKthLargest{
    fun findKthLargest(nums: IntArray, k: Int): Int {
        nums.sortDescending()
        return nums[k-1]
    }
}