package com.conal.dbops4

import kotlin.annotation.AnnotationTarget.FIELD
import kotlin.reflect.KClass

/**
 * This annotation can be used to inject a dbOps dependencies for database execution.
 */
@Target(FIELD)
@MustBeDocumented
annotation class DbOpsInject(val impl: KClass<out DbOps>)
