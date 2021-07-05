package com.conal.dbops3

import com.conal.corda.*
import com.conal.dbops3.java.MyJavaFlow
import com.conal.main.ContractState

fun main() {

    val proxyFactory = DbOpsProxyFactory()
    val myDbOps = MyDbOps()
    val dbOpsDiContainer = DbOpsDiContainer()

    // create proxy and register with di container
    val proxy = proxyFactory.proxy(myDbOps, MyDbOps::class.java)
    val realWithProxy = DbOpsImplWithProxy(myDbOps, proxy)
    dbOpsDiContainer.registerDbOps(realWithProxy)

    // inject dependency into flow
    val myFlow = MyFlow()
    dbOpsDiContainer.injectDbOpsProxies(myFlow)

    // call function that uses the dependency
    myFlow.call()

    val javaFlow = MyJavaFlow()
    dbOpsDiContainer.injectDbOpsProxies(javaFlow)
    javaFlow.call()
}

class MyFlow : Flow<StateAndRef<ContractState>> {
    @DbOpsInject(MyDbOps::class)
    private lateinit var myDbOps: DbOps<NamedQueryRequestImpl, List<StateAndRef<ContractState>>>

    override fun call() : StateAndRef<ContractState> {
        val request = NamedQueryRequestImpl("query", mapOf(), null, null, 200)
        val execute: List<StateAndRef<ContractState>>? = myDbOps.execute(request)
        return execute!!.first()
    }
}

class MyDbOps : DbOps<NamedQueryRequest, List<StateAndRef<ContractState>>> {
    @CordaInject
    private lateinit var persistenceService: PersistenceService

    override fun execute(args: NamedQueryRequest?): List<StateAndRef<ContractState>>? {
        val list: List<StateAndRef<ContractState>>? = persistenceService.withNamedQueryService {
            this.query(args!!, IdentityStateAndRefPostProcessor())
        }
        return list
    }
}

class MyDbOpsPersistOnly : DbOps<List<ContractState>, Unit> {
    @CordaInject
    private lateinit var persistenceService: PersistenceService

    override fun execute(args: List<ContractState>?): Unit? {
        persistenceService.withEntityManager {
            args!!.forEach {
                persist(it)
            }
        }
        return null
    }
}