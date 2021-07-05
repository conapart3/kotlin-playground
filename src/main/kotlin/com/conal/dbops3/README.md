# Single interface with generics

- DbOps interface that has generics for inputs and outputs
```kotlin
interface DbOps<T, R> {
    // var persistenceService: PersistenceService
    fun execute(args: T?): R?
}
```
- if we remove PersistenceService field this could be generic?
- Proxy created using Java dynamic proxies for the interface (DbOps)
- Registers DbOps into diContainer
- Injects dbOps proxy into flow 
- field declared in flow can have the generic types which give types to the request and response when calling  
- dbops example:
```kotlin
class MyDbOps : DbOps<NamedQueryRequest, List<StateAndRef<ContractState>>> {
    @CordaInject
    override lateinit var persistenceService: PersistenceService

    override fun execute(args: NamedQueryRequest?): List<StateAndRef<ContractState>>? {
        val list: List<StateAndRef<ContractState>> = persistenceService.withNamedQueryService {
            this.query(args!!, IdentityStateAndRefPostProcessor())
        }
        return list
    }
}
```
- flow example:
```kotlin
class MyFlow : Flow<Any> {

    @DbOpsInject(MyDbOps::class)
    private lateinit var myDbOps: DbOps<NamedQueryRequestImpl, List<StateAndRef<ContractState>>>

    fun callInjectedDependency(){
        val request = NamedQueryRequestImpl("query", mapOf(), null, null, 200)
        val execute: List<StateAndRef<ContractState>>? = myDbOps.execute(request)
    }
}
```

What about from Java?
- 