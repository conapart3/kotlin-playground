package com.conal.leetcode.jan2021.day20

import java.util.*

fun main(){
    println(Day20().isValid("]"))
}

class Day20 {
    fun isValid(s: String): Boolean {
        val bracketQueue: Stack<Char> = Stack<Char>()
        for(char in s) {
            if(char == '{') bracketQueue.push(char)
            if(char == '[') bracketQueue.push(char)
            if(char == '(') bracketQueue.push(char)
            if(char == '}') {
                if(bracketQueue.isEmpty() || bracketQueue.pop() != '{'){
                    return false
                }
            }
            if(char == ']') {
                if(bracketQueue.isEmpty() || bracketQueue.pop() != '['){
                    return false
                }
            }
            if(char == ')') {
                if(bracketQueue.isEmpty() || bracketQueue.pop() != '('){
                    return false
                }
            }
        }
        return bracketQueue.isEmpty()
    }
}