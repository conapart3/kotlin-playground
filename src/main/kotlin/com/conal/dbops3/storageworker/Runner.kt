package com.conal.dbops3.storageworker

import com.conal.dbops3.MyDbOps
import com.conal.corda.PersistenceService
import com.conal.corda.PersistenceServiceImpl
import com.conal.corda.NamedQueryRequestImpl

fun main() {

    val myDbOps = MyDbOps()

    // mocked persistence service that will exist on storage worker side
    val dependencyInjectionService = DependencyInjectionServiceImpl()
    dependencyInjectionService.singletonServices[PersistenceService::class.java] = PersistenceServiceImpl()

    // simulate injecting dependencies into the dbOps
    dependencyInjectionService.injectDependenciesIntoDbOps(listOf(myDbOps))

    // add the instances into dbOpsHolder with all dependencies injected which holds map for invocations
    val dbOpsHolder = DbOpsHolder(listOf(myDbOps))

    // simulate request coming in. It needs to create the class type and get the params
    val request = "com.conal.dbops3.MyDbOps#execute"
    val classLoadedType = MyDbOps::class.java
//    val method = classLoadedType.functions.find { it.name == "execute" }
    val method = classLoadedType.methods.find { it.name == "execute" }

    val dbOps = dbOpsHolder.getDbOps(classLoadedType)
    dbOps.execute(NamedQueryRequestImpl("", mapOf(), null, null, 200))
}