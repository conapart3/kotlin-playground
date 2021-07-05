package com.conal.dbops2

import com.conal.corda.Flow

fun main() {

    val proxyFactory = DbOpsCGLIBProxyFactory()
    val myDbOps = MyDbOps()
    val dbOpsDiContainer = DbOpsDiContainer()

    // create proxy and register with di container
    val proxy = proxyFactory.proxy(myDbOps)
    val realWithProxy = DbOpsImplWithProxy(myDbOps, proxy)
    dbOpsDiContainer.registerDbOps(realWithProxy)

    // inject dependency into flow
    val myFlow = MyFlow()
    dbOpsDiContainer.injectDbOpsProxies(myFlow)

    // call function that uses the dependency
    myFlow.call()
}


open class MyDbOps : DbOps {
    fun doSomething(){
        println("doing something")
    }
}

class MyFlow : Flow<Any> {

    @DbOpsInject
    private lateinit var myDbOps: MyDbOps

    override fun call() : Any {
        myDbOps.doSomething()
        return Any()
    }

}