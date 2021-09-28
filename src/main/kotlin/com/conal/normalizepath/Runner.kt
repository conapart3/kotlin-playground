package com.conal.normalizepath

import com.conal.main.serializeToBytes
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.nio.charset.Charset
import java.nio.file.Paths
import java.util.jar.JarEntry
import java.util.jar.JarFile
import java.util.jar.JarInputStream
import java.util.jar.JarOutputStream

fun main() {
    val inputs = listOf(
        "..\\..\\src\\main\\kotlin\\com\\conal\\normalizepath\\Runner.kt",
        "../../src/main/kotlin/com/conal/normalizepath/Runner.kt",
        "/foo",
        "\\foo\\",
        "/foo/bar",
        "\\foo\\bar"
    )

    inputs.forEach {
        val path = Paths.get(it)
        println("$path - Absolute: ${path.isAbsolute}")
        val normalized = path.normalize()
        println("-> $normalized - Absolute: ${normalized.isAbsolute}\"")
        println()
    }

    val byteArrayOutputStream = ByteArrayOutputStream()
    val jarOutputStream = JarOutputStream(byteArrayOutputStream)
    val attack = JarEntry("../attack")
    jarOutputStream.putNextEntry(attack)
    jarOutputStream.write("some-text".toByteArray())
    jarOutputStream.closeEntry()
    jarOutputStream.close()

    val bytes = byteArrayOutputStream.toByteArray()
    println(String(bytes, Charset.defaultCharset()))
    val inputSTream = ByteArrayInputStream(bytes)

    val jarInput = JarInputStream(inputSTream, true)
    println(jarInput)
}

