package com.conal.internal

import com.conal.public.Operations
import com.conal.public.Service

class OperationService : Operations, Service {
    override fun queryVault() {
        println("Query vault")
    }

    override fun queryByCriteria() {
        println("Query by criteria")
    }

    override fun queryByNamedQuery() {
        println("Query named query")
    }
}