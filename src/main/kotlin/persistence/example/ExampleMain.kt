package persistence.example

import java.util.UUID
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.EntityManagerFactory
import javax.persistence.Id
import javax.persistence.Persistence
import javax.persistence.Table
import kotlin.concurrent.thread

fun main() {
    ExampleMain().start()
}

class ExampleMain {
    fun start() {
        val emf = Persistence.createEntityManagerFactory("example-unit")

        println("before run: " + Thread.currentThread().name)
        val t = thread {
            println("Inside run: " + Thread.currentThread().name)
            Thread.sleep(1000)
            println("Finished sleeping ${Thread.currentThread().name}")
        }
        t.run()
        println("after run: " + Thread.currentThread().name)

        try {
            val id = UUID.randomUUID().toString()
            persistData("some-data", emf, id)
            val result = readData(emf, id)
            assert(result != null)
            assert(result!!.id == id)
        } finally {
            emf.close()
        }
    }

    private fun readData(emf: EntityManagerFactory, id: String): DataEvent? {
        val em = emf.createEntityManager()
        return try {
            em.find(DataEvent::class.java, id)
        } finally {
            em.close()
        }
    }

    private fun persistData(s: String, emf: EntityManagerFactory, id: String) {
        val data = DataEvent(id, s)
        val em = emf.createEntityManager()
        try {
            em.transaction.begin()
            em.persist(data)
            em.transaction.commit()
        } finally {
            em.close()
        }
    }
}

@Entity
@Table(name = "DATA_EVENT")
class DataEvent {

    constructor() {}
    constructor(id: String, name: String) {
        this.id = id
        this.name = name
    }

    @Id
    @Column(name = "id")
    lateinit var id: String

    @Column(name = "name")
    lateinit var name: String
}

