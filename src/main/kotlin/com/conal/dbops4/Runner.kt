package com.conal.dbops4

import com.conal.corda.*
import com.conal.main.ContractState

fun main() {

    val proxyFactory = DbOpsProxyFactory()
    val myDbOps = MyDbOps()
    val dbOpsDiContainer = DbOpsDiContainer()

    // in corda we'll load instances of the dbOps implementations,
    // create proxy and register with di container
    // get the interfaces that extend DbOps, we will use these as the proxies
    val interfacesThatExtendDbOps = myDbOps.javaClass.interfaces.filter { DbOps::class.java.isAssignableFrom(it) }
    val proxy = proxyFactory.proxy(myDbOps, uncheckedCast(interfacesThatExtendDbOps))
    val realWithProxy = DbOpsImplWithProxy(myDbOps, proxy)
    dbOpsDiContainer.registerDbOps(realWithProxy)

    // inject dependency into flow
    val myFlow = MyFlow()
    // node will inject the proxies after loading the classes from the CPK
    dbOpsDiContainer.injectDbOpsProxies(myFlow)

    // call function that uses the dependency
    myFlow.call()
}

class MyFlow : Flow<Any> {

    @DbOpsInject(MyDbOps::class)
    private lateinit var myDbOps: MyIntermediateDbOps

    @DbOpsInject(MyDbOps::class)
    private lateinit var myDbOps2: MyIntermediateDbOps2

    @DbOpsInject(MyContractStateReader::class)
    private lateinit var contractStateReader: IMyContractStateReader

    override fun call() : Any {
        val request = NamedQueryRequestImpl("query", mapOf(), null, null, 200)
        val listOfAny = myDbOps.doSomeReading(request)
        val list2 = myDbOps.doSomeMoreReading(request)

//        (myDbOps2 as MyDbOps).someOtherFunction() // classCastException - myDbOps2 is a proxy - cant be cast to a MyDbOps
        myDbOps2.doSomeWriting(request)

        var states: List<ContractState> = contractStateReader.findContractStates(request)
        return Any()
    }
}

interface SomeOtherInterface {
    fun someOtherFunction()
}

interface IMyContractStateReader : DbOps {
    fun findContractStates(request: NamedQueryRequest) : List<ContractState>
}

interface MyIntermediateDbOps : DbOps {
    fun doSomeReading(request: NamedQueryRequest): List<Any>
    fun doSomeMoreReading(request: NamedQueryRequest): List<Any>
}

interface MyIntermediateDbOps2 : DbOps {
    fun doSomeWriting(entity: Any)
}

class MyContractStateReader : IMyContractStateReader {
    @CordaInject
    private lateinit var persistenceService: PersistenceService

    override fun findContractStates(request: NamedQueryRequest): List<ContractState> {
        return persistenceService.withNamedQueryService {
            query(request, IdentityContractStatePostProcessor())
        }!!
    }
}

class MyDbOps : MyIntermediateDbOps, SomeOtherInterface, MyIntermediateDbOps2 {
    @CordaInject
    private lateinit var persistenceService: PersistenceService

    override fun doSomeReading(request: NamedQueryRequest) : List<Any> {
        persistenceService.withNamedQueryService {
            query(request)
        }
        return emptyList()
    }

    override fun doSomeMoreReading(request: NamedQueryRequest): List<Any> {
        persistenceService.withNamedQueryService {
            query(request)
        }
        return emptyList()
    }

    override fun someOtherFunction() {
        println("do nothing")
    }

    override fun doSomeWriting(entity: Any) {
        persistenceService.withEntityManager {
            persist(entity)
        }
        // these do not go through the proxy
        someOtherFunction()
        doSomeMoreReading(NamedQueryRequestImpl("", mapOf(), null, null, 200))
    }
}
