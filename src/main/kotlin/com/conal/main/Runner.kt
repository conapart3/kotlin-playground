package com.conal.main

import com.conal.internal.OperationService
import com.conal.public.Operations
import com.conal.public.Service
import java.io.BufferedReader
import java.io.File
import java.io.InputStreamReader
import java.net.URI
import java.nio.file.FileSystems
import java.nio.file.Path

class Runner {

    fun main() {
        val opsService = OperationService()
        val serviceOperationsRunner = ServiceOperationsRunner(opsService, opsService)

    }
}

class ServiceOperationsRunner(val ops: Operations, val service: Service) {
    fun callOpsNamedQuery() {
        ops.queryByNamedQuery()
    }

    fun callOpsVault() {
        ops.queryVault()
    }

    fun callServiceByCriteria() {
        service.queryByCriteria()
    }

    fun callServiceNamedQuery() {
        service.queryByNamedQuery()
    }
}

fun main2() {
    val str = """
                        SELECT new (
                                event.aaa,
                                event.aaa,
                                state.aaa,
                                event.aaa,
                                event.aaa,
                                state.aaa,
                                state.aaa
                        )
                        FROM aaa event
                        WHERE event.aaa > :seqStartNoExclusive
                        AND event.aaa <= :maxSeqNoInclusive
                        INNER JOIN aaa state
                        ON state.aaa
                        AND state.aaa
                        ORDER BY event.aaa ASC
                    """
    println("none")
    println(str)
    println("trim indent")
    println(str.trimIndent())
    println("trim margin")
    println(str.trimMargin())
    println("trim")
    println(str.trim())
    println("trim start")
    println(str.trimStart())
}

class CordaNamedQuery(val name: String, val query: String)