package com.conal.leetcode.jan2021

class ListNode(var `val`: Int) {
    var next: ListNode? = null
}
fun createNode(value: Int, next: ListNode.() -> ListNode?): ListNode {
    val listNode = ListNode(value)
    listNode.next = listNode.next()
    return listNode
}