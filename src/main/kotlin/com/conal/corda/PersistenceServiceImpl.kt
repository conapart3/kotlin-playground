package com.conal.corda

import java.util.function.Consumer
import javax.persistence.EntityManager

class PersistenceServiceImpl : PersistenceService {
    override fun <T> withEntityManager(block: EntityManager.() -> T): T? {
        println("WithEntityManager")
        return null
    }

    override fun withEntityManager(block: Consumer<EntityManager>) {
        println("WithEntityManager")
    }

    override fun <T> withTransaction(block: PersistenceService.() -> T): T? {
        println("withTransaction")

        return null
    }

    override fun <R> withNamedQueryService(block: NamedQueryService.() -> R): R? {
        println("withNamedQUeryService")
        return null
    }
}