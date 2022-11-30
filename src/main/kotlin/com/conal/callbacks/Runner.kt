package com.conal.callbacks

fun main() {
    val dbConnectionManager = DbConnectionManager()
    val psrs = FakePermissionStorageReaderService(
        "a",
        "b"
    ) {
        println("db connection manager function")
        dbConnectionManager.getOrCreateEmf()
    }

    println("Instantiated psrs now call doSomethingFake")
    psrs.doSomethingFake()
}

class DbConnectionManager() {

    init {
        println("INIT dbConnectionManager")
    }

    fun getOrCreateEmf(): FakeEntityManagerFactory {
        println("Getting or creating EMF")
        return FakeEntityManagerFactory()
    }
}

class FakePermissionStorageReaderService(
    private val a: String,
    private val b: String,
    private val func: () -> FakeEntityManagerFactory
) {
    fun doSomethingFake() {
        func.invoke()
    }

    init {
        println("INIT FakePSRS")
    }
}

class FakeEntityManagerFactory() {
    init {
        println("INIT FakeEMF")
    }
}