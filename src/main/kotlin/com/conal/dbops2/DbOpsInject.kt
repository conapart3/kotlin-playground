package com.conal.dbops2

import kotlin.annotation.AnnotationTarget.FIELD

/**
 * This annotation can be used to inject a dbOps dependencies for database execution.
 */
@Target(FIELD)
@MustBeDocumented
annotation class DbOpsInject
