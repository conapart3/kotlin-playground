package com.conal.dbops1

import com.conal.corda.Flow

fun main() {

    val proxyFactory = DbOpsProxyFactory()
    val myDbOps = MyDbOps()
    val dbOpsDiContainer = DbOpsDiContainer()

    // create proxy and register with di container
    val proxy = proxyFactory.proxy(myDbOps, DbOps::class.java)
    val realWithProxy = DbOpsImplWithProxy(myDbOps, proxy)
    dbOpsDiContainer.registerDbOps(realWithProxy)

    // inject dependency into flow
    val myFlow = MyFlow()
    dbOpsDiContainer.injectDbOpsProxies(myFlow)

    // call function that uses the dependency
    myFlow.call()
}

class MyDbOps : DbOps{
    fun doSomething(){
        println("doing something")
    }
}

interface SomeInterface

class MyFlow : Flow<Any> {

//    @DbOpsInject(MyDbOps::class)
//    private lateinit var myDbOps: SomeInterface // does not work... must inject proxy into an interface that it proxies

//    @DbOpsInject
//    private lateinit var myDbOps: MyDbOps // does not work... must inject proxy into an interface

    @DbOpsInject(MyDbOps::class)
    private lateinit var myDbOps: DbOps // works

    override fun call() : Any {
//        myDbOps.??? // nothing we can do here, can't even cast it because this is a proxy
        // (myDbOps as MyDbOps) // throws exception:
        // Exception in thread "main" java.lang.ClassCastException: class com.sun.proxy.$Proxy0 cannot be cast to class com.conal.dbops1.MyDbOps (com.sun.proxy.$Proxy0 and com.conal.dbops1.MyDbOps are in unnamed module of loader 'app')
        return Any()
    }

}