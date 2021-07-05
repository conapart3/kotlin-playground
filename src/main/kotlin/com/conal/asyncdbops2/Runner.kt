package com.conal.asyncdbops2

import com.conal.asyncdbops2.impl.AsyncDbOpsStarter
import com.conal.asyncdbops2.impl.AsyncDbOpsStarterImpl
import com.conal.asyncdbops2.impl.DependencyInjectionServiceImpl
import com.conal.corda.*
import com.conal.main.ContractState

fun main() {

    val myFlow = MyFlow()

    val dependencyInjectionService = DependencyInjectionServiceImpl()
    dependencyInjectionService.singletonServices[AsyncDbOpsStarter::class.java] = AsyncDbOpsStarterImpl()
    dependencyInjectionService.injectDependencies(myFlow)


    myFlow.call()



}

class MyFlow : Flow<Any> {

    @CordaInject
    private lateinit var asyncDbOpsStarter: AsyncDbOpsStarter

    override fun call(): Any {

        val states: List<StateAndRef<ContractState>> =
            asyncDbOpsStarter.invokeDbOpsAsync(
                MyDbOps2(MyContractState("nobody"),
                    NamedQueryRequestImpl("query", mapOf(), null, null, 200))
            )

        asyncDbOpsStarter.invokeDbOpsAsync(
            MyDbOps(MyContractState("nobody"))
        )

        val contractState = states.first().state.data

        return Any()
    }

}

// this should be CordaSerializable because we will send the entire object over the wire...
// @CordaSerializable
class MyDbOps(private val item: ContractState) : AsyncDbOps<Unit> {
    override fun execute(persistenceService: PersistenceService) {
        persistenceService.withEntityManager {
            persist(item)
        }
    }
}

// this should be CordaSerializable because we will send the entire object over the wire...
// @CordaSerializable
class MyDbOps2(private val item: ContractState, private val namedQueryRequest: NamedQueryRequest) : AsyncDbOps<List<StateAndRef<ContractState>>> {
    override fun execute(persistenceService: PersistenceService) : List<StateAndRef<ContractState>> {
        persistenceService.withEntityManager {
            persist(item)
        }
        return persistenceService.withNamedQueryService {
            query(namedQueryRequest, IdentityStateAndRefPostProcessor())
        } ?: listOf()
    }
}

data class MyContractState(override val participants: String) : ContractState