package com.conal.dbops3

interface DbOps<T, R> {
    fun execute(args: T?): R?
}