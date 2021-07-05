package com.conal.asyncdbops1.storageworker

import com.conal.asyncdbops1.AsyncDbOps
import com.conal.asyncdbops1.MyContractState
import com.conal.asyncdbops1.MyDbOps
import java.lang.reflect.Method

fun main() {

    val fqn = "com.conal.asyncdbops1.MyDbOps#execute"

    // some logic picks out the class from fqn and the method
    val clazz: Class<out AsyncDbOps> = MyDbOps::class.java
    val method: Method = clazz.methods.first { it.name == "execute" }

    val dbOpsRef = DbOpsRefFactoryImpl().createDbOps(clazz, MyContractState("nobody"))

    println()

}
