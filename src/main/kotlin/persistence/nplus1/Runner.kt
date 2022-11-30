package persistence.nplus1

import java.util.UUID
import javax.persistence.EntityManager
import javax.persistence.Persistence

fun main() {
    Runner().doIt()
}

class Runner {
    fun doIt() {

        val emf = Persistence.createEntityManagerFactory("example-unit")
        val em = emf.createEntityManager()

        try {
            val id = UUID.randomUUID().toString()
            val wheels = persistWheels(em, id)
            persistCar("some-data", em, id, wheels)
            val result = readData(em, id)
            assert(result != null)
            assert(result!!.id == id)
        } finally {
            em.close()
            emf.close()
        }

        println("Finished")
    }

    private fun persistWheels(em: EntityManager, id: String) : List<Wheel> {
        val first = Wheel(id + "_1")
        val second = Wheel(id + "_2")
        val third = Wheel(id + "_3")
        val fourth = Wheel(id + "_4")
        em.transaction.begin()
        em.persist(first)
        em.persist(second)
        em.persist(third)
        em.persist(fourth)
        em.transaction.commit()
        return listOf(first, second, third, fourth)
    }

    private fun persistCar(carName: String, em: EntityManager, id: String, wheels: List<Wheel>) {
        val car = Car(id, carName, wheels)
        em.transaction.begin()
        em.persist(car)
        em.transaction.commit()
    }

    private fun readData(em: EntityManager, id: String): Car? {
        println("Reading car")
        return em.find(Car::class.java, id)
    }
}