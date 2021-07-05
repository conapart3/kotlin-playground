package com.conal.asyncdbops2

import com.conal.corda.PersistenceService

interface AsyncDbOps<R> {
    fun execute(persistenceService: PersistenceService): R
}