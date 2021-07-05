package com.conal.filereader

import com.conal.main.CordaNamedQuery
import java.io.BufferedReader
import java.io.File
import java.io.InputStreamReader
import java.net.URI
import java.nio.file.FileSystems

fun main() {
    println(FileReader().loadNamedQueriesFromFolder())
}

class FileReader {

    fun loadNamedQueriesFromFolder(): List<CordaNamedQuery> {
        println("Attempting to load Corda named queries from 'CordaNamedQueryDefinitions' file.")
        val loadedQueries = mutableListOf<CordaNamedQuery>()
        val definitionsStream = this::class.java.classLoader.getResourceAsStream("vault-named-queries${File.separator}CordaNamedQueryDefinitions")
        definitionsStream?.let { inputStream ->
            BufferedReader(InputStreamReader(inputStream)).lines().forEach { name ->
                // if the file isn't found, file is null and is effectively skipped
                val file = this::class.java.classLoader.getResourceAsStream("vault-named-queries${File.separator}$name")
                file?.let { namedQueryFile ->
                    val query = BufferedReader(InputStreamReader(namedQueryFile)).readText()
                    println("Found Corda named query '$name'.")
                    loadedQueries.add(CordaNamedQuery(name, query))
                }
            }
        }
        return loadedQueries
    }
}