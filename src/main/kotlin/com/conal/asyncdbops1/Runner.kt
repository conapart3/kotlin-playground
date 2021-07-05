package com.conal.asyncdbops1

import com.conal.asyncdbops1.impl.AsyncDbOpsStarter
import com.conal.asyncdbops1.impl.invokeDbOperation
import com.conal.corda.Flow
import com.conal.corda.PersistenceService
import com.conal.jackson.ContractState

fun main() {

    val myFlow = MyFlow()

    myFlow.call()



}

// this should be CordaSerializable because we will send the entire object over the wire...
class MyDbOps(private val item: ContractState) : AsyncDbOps {
    override fun execute(persistenceService: PersistenceService) {
        persistenceService.withEntityManager {
            persist(item)
        }
    }

}

class MyFlow : Flow<Any> {

    override fun call(): Any {
        AsyncDbOpsStarter().invokeDbOperation(
            ::MyDbOps,
            MyContractState("nobody")
        )

//        AsyncDbOpsStarter().invokeDbOpsAsync(MyDbOps(MyContractState("nobody")))

        return Any()
    }

}

data class MyContractState(override val participants: String) : ContractState