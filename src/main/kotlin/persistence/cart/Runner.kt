package persistence.cart

import javax.persistence.EntityManager
import javax.persistence.Persistence

fun main() {

    fun withEntityManager(block: (em: EntityManager) -> Any?): Any? {
        val emf = Persistence.createEntityManagerFactory("example-unit")
        val em = emf.createEntityManager()
        val result = try {
            em.transaction.begin()
            val temp = block.invoke(em)
            em.transaction.commit()
            temp
        } finally {
            em.close()
            emf.close()
        }
        return result
    }

    withEntityManager {
        val persistentUser = PersistentUser("userId1", "userName1")
        persistentUser.cart = PersistentCart("cartId1", "userId1", "tx1", 0)
        it.persist(persistentUser)
    }
}