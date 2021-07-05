package com.conal.dbops4.storageworker

import com.conal.asyncdbops2.SerializedBytes
import com.conal.corda.PersistenceService
import com.conal.corda.PersistenceServiceImpl
import com.conal.dbops4.DbOps
import com.conal.dbops4.MyDbOps
import java.lang.RuntimeException
import java.lang.reflect.Method

fun main() {

    // simulates loading of some dbOps implementations from OSGi ServiceConsumer
    val dbOpsList = DbOpsClassLoader().loadClasses()
    // simulates a di service on the storage worker
    val dependencyInjectionService = DependencyInjectionServiceImpl()
    dependencyInjectionService.singletonServices[PersistenceService::class.java] = PersistenceServiceImpl()
    // simulates injecting dependencies into the dbOps when they are needed?
    dependencyInjectionService.injectDependenciesIntoDbOps(dbOpsList)
    // add the instances into dbOpsHolder with all dependencies injected which holds map for invocations
    val dbOpsHolder = DbOpsHolder(dbOpsList)

    // simulates request coming in and being handled. It needs to create the class type and get the params
    val request = "com.conal.dbops4.MyDbOps#doSomeReading"
    val (clazz, method) = getClassAndMethodFromRequest(request)
    val dbOps = dbOpsHolder.getDbOps(clazz)
    val args = SerializedBytes<Any>("somearg".toByteArray())
    callMethod(method, dbOps, args)
//    dbOps.execute(NamedQueryRequestImpl("", mapOf(), null, null, 200))
}

private fun callMethod(method: Method, dbOps: DbOps, args: SerializedBytes<*>) {
    // do some metching logic similar to what's found in
    method.invoke(dbOps, args)
}

private fun getClassAndMethodFromRequest(request: String): Pair<Class<out DbOps>, Method> {
    val classLoadedType = MyDbOps::class.java
//    val method = classLoadedType.functions.find { it.name == "execute" }
    val method = classLoadedType.methods.find { it.name == "doSomeReading" } ?: handleNoMethodFound()
    return Pair(classLoadedType, method)
}

fun handleNoMethodFound(): Method {
    throw RuntimeException("No method found")
}

class DbOpsClassLoader(){
    fun loadClasses(): List<DbOps> {
        return listOf(MyDbOps())
    }
}

//class DbOpsMessageHandler{
//    fun handleRequest(message: JMSMessage)
//}