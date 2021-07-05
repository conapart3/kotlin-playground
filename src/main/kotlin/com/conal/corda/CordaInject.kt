package com.conal.corda

import kotlin.annotation.AnnotationTarget.FIELD

/**
 * This annotation can be used with [FlowLogic] to indicate which dependencies should be injected.
 * The dependencies which can be injected are limited to the interfaces in the [net.corda.core.flows.flowservices] package.
 */
@Target(FIELD)
@MustBeDocumented
annotation class CordaInject