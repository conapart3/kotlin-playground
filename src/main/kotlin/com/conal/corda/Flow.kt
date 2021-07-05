package com.conal.corda

interface Flow<out T> {
    fun call(): T
}