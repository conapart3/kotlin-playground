package com.conal.dbops4.storageworker

import com.conal.corda.uncheckedCast
import com.conal.dbops4.DbOps
import com.conal.dbops4.DbOpsException


// on storage worker side we need a
class DbOpsHolder(val dbOpsList: List<DbOps>) {

//    private data class InvocationTarget(val method: Method, val instance: DbOps)

    // holds method fully qualified name and the actual instance
    // used to service the actual call
    // className: (method, instance)
    private val dbOpsTable: Map<Class<out DbOps>, DbOps>

    companion object {
        private const val CLASS_METHOD_DIVIDER = "#"
    }

    init {
        val mutableMethodTable = mutableMapOf<Class<out DbOps>, DbOps>()
        dbOpsList.forEach { dbOps ->
            mutableMethodTable[dbOps.javaClass] = dbOps
        }
        dbOpsTable = mutableMethodTable
    }

    // this should set up listeners and stuff to listen to kafka queue

    // when a message comes in, it should get it, match up with te map and call the actual implementation

    // also needs to produce and consume messages

    // See RPCServer

    //

//    fun <T, R> invoke(clazz: Class<out DbOps<T,R>>, args: T) {
//        val (className, methodName) = message.split('#')
//
//        val invocationTarget = dbOpsTable[className] ?: throw DbOpsException("Received DBOps request for unknown method")
//        invocationTarget.execute()
//    }

    fun <T : DbOps> getDbOps(classLoadedType: Class<T>) : T {
        return uncheckedCast(dbOpsTable[classLoadedType]
            ?: throw DbOpsException("DbOps ${classLoadedType.name} not registered in DbOpsHolder.")
        )
    }
}