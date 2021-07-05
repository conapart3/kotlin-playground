# Async DBOps execution service similar to AsyncFlowStarter
- AsyncDbOps interface with one function
- caller does not need to worry about injecting a persistence service.
- MyAsyncDbOps takes parameters in the constructor
- No proxies needed
- Corda provides a class `AsyncDbOpsStarterService` similar to `AsyncFlowStarter` 
- Flows call the function in this class with an instance of their AsyncDbOps implementation
- AsyncDbOps implementation doesn't have to inject a PersistenceService

```kotlin
interface AsyncDbOps<R> {
    fun execute(persistenceService: PersistenceService): R
}
```

## What does the function do?
- serializes the AsyncDbOps implementation
- builds request 
- sends request onto kafka
- suspends the flow fiber

## What should the storage worker do?
- receives request
- calls execute function providing a persistenceService

## What will it do with the results?
- serialize and put onto kafka queue 
- result object needs an ID to send back to origin flow

## What will node do when it receives response
- read message from queue
- awaken flow fiber with given ID
- resume flow with the results


