package com.conal.leetcode.jan2021.day5

import com.conal.leetcode.jan2021.ListNode
import com.conal.leetcode.jan2021.createNode

fun main() {
    val l1 = createNode(1) {
        createNode(1) {
            createNode(2) {
                createNode(2) {null}
            }
        }
    }
    Solution2().deleteDuplicates(l1)
}


class Solution2() {
    fun deleteDuplicates(head: ListNode?): ListNode? {
        // create a sentinel starter node
        val sentinel = ListNode(-1)
        sentinel.next = head

        var pred = sentinel
        var current = head

        while(current != null){
            // if beginning of some duplicates, skip them all
            if(current.next != null && current.`val` == current.next!!.`val`){
                // move until end of sublist
                while(current!!.next != null && current.`val` == current.next!!.`val`){
                    current = current.next
                }
                // skip all duplicates
                pred.next = current!!.next
            } else {
                pred = pred.next!!
            }
            // move forward
            current = current.next
        }
        return sentinel.next
    }
}