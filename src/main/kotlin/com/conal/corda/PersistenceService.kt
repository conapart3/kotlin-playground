package com.conal.corda

import java.util.function.Consumer
import java.util.function.Function
import javax.persistence.EntityManager

interface PersistenceService {
    fun <T : Any?> withEntityManager(block: EntityManager.() -> T): T?
    fun withEntityManager(block: Consumer<EntityManager>)
    fun <T> withTransaction(block: PersistenceService.() -> T) : T?
    fun <R> withNamedQueryService(block: NamedQueryService.() -> R) : R?
//    fun <R> withNamedQueryService(block: Function<NamedQueryService, R>) : R?
}