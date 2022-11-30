package typecheck

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import persistence.permissions.model.User

fun Any.contextLogger(): Logger = LoggerFactory.getLogger(javaClass.enclosingClass)

fun main() {
    val clazz = User::class.java
    println("${clazz.simpleName} 'id' not found.")


}