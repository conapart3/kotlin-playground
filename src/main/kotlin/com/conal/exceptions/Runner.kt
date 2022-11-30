package com.conal.exceptions

fun main(){
    try {
        throw CordaRuntimeException("abc")
    } catch (e : CordaRuntimeException) {
        println(e)
    }

}