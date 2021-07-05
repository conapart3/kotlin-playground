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
    Solution().deleteDuplicates(l1)
}


class Solution() {
    fun deleteDuplicates(head: ListNode?): ListNode? {
        if (head == null) {
            return null
        }
        if (head.next == null) {
            return head
        }
        val root = findRoot(head)
        if (root == null) {
            return null
        }
        if (root.next == null) {
            return root
        }
        searchAndRepoint(root)
        return root
    }

    private fun findRoot(root: ListNode): ListNode? {
        if (root.next == null) {
            // only 1 node in the list
            return root
        }
        var next = root.next
        var skipOriginalRoot = false // because original root is duplicated
        while (root.`val` == next!!.`val`) {
            if (next.next == null) {
                // e.g. 1->1->1 returns null
                return null
            }
            skipOriginalRoot = true
            next = next.next
        }
        if (skipOriginalRoot) {
            // 1->1->2 returns 2
            return findRoot(next)
        } else {
            // 1->2->3 returns 1
            return root
        }
    }


    private fun searchAndRepoint(lastSafeNode: ListNode) {
        if (lastSafeNode.next == null) {
            // end of list
            return
        }
        var next = lastSafeNode.next!!
        if (next.next == null) {
            // 1 -> 2 -> null so return
            return
        }
        var duplicated = false
        while (next.`val` == next.next!!.`val`) {
            duplicated = true
            if (next.next!!.next == null) {
                lastSafeNode.next = null
                return
            }
            next = next.next!!
        }
        if(duplicated){
            // lastsafenode remains the same, but pointed to skip over the duplicates. Run search again to check if lastSafeNode.next is safe.
            lastSafeNode.next = next.next
            searchAndRepoint(lastSafeNode)
        } else {
            // next is only changed when duplicates happen, therefore if no duplicates, the next is safe
            searchAndRepoint(next)
        }
    }
}