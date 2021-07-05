package com.conal.asyncdbops1

import com.conal.corda.PersistenceService

interface AsyncDbOps {
    fun execute(persistenceService: PersistenceService)
}