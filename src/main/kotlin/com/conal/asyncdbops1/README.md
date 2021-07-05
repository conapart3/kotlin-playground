- DbOps interface with one function

```kotlin
interface AsyncDbOps {
    fun execute(persistenceService: PersistenceService)
}
```

- No proxies needed
- AsyncDbOpsStarterService code that suspends the flow and sends serialized MyDbOps
- storageWorker calls the execute() function with the PersistenceService as a parameter.
- caller does not need to worry about injecting a persistence service.
- MyDbOps takes parameters in the constructor

```kotlin
val response1 = AsyncDbOpsStarter.invokeDbOperation(
    ::AsyncDbOpsReaderImpl,
    NamedQueryRequestImpl("query", mapOf(), null, null, 200)
)
    .returnValue
    .get(10, TimeUnit.SECONDS)
```
- Java callers have no type safety there
```java
??
```
