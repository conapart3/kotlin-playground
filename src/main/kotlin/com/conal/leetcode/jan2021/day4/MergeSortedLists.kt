package com.conal.leetcode.jan2021.day4

import com.conal.leetcode.jan2021.ListNode


fun main() {

    val mergeTwoLists1 = Runner().mergeTwoLists(
        ListNode(1).apply { next = ListNode(2) },
        ListNode(3).apply { next = ListNode(4) })

    val mergeTwoLists2 = Runner().mergeTwoLists(
        ListNode(3).apply { next = ListNode(4) },
        ListNode(1).apply { next = ListNode(5) })

    val mergeTwoLists3 = Runner().mergeTwoLists(
        ListNode(1).apply { next = ListNode(3) },
        ListNode(2).apply { next = ListNode(4) })

    val mergeTwoLists4 = Runner().mergeTwoLists(
        null,
        ListNode(0)
    )

    println(mergeTwoLists1)
    println(mergeTwoLists2)
    println(mergeTwoLists3)
    println(mergeTwoLists4)
}

private class Runner {
    fun mergeTwoLists(l1: ListNode?, l2: ListNode?): ListNode? {
        if (l1 == null && l2 == null) {
            return null
        }
        if (l2 == null) {
            return l1
        }
        if (l1 == null) {
            return l2
        }

        val newRoot: ListNode

        if (l1.`val` <= l2.`val`) {
            newRoot = l1
            merge(l1, l2)
        } else {
            newRoot = l2
            merge(l2, l1)
        }

        return newRoot
    }

    fun merge(head: ListNode, otherNode: ListNode?) {
        if(head.next == null){
            if(otherNode == null){
                return
            } else {
                head.next = otherNode
                return
            }
        } else {
            if(otherNode == null){
                // head.next is all that's left, other list is finished, so we can just return as all the rest of the pointers are correct
                return
            }
        }
        if(head.next!!.`val` <= otherNode.`val`){
            // keep head.next
            merge(head.next!!, otherNode)
        } else {
            val temp = head.next
            head.next = otherNode
            merge(head, temp)
        }
    }
}
