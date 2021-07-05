# Single DbOps interface requiring casting

- Empty DbOps interface
- Proxy created is using Java dynamic proxy using interface (DbOps)
- Register DbOps into diContainer and store in map <Class<out DbOps>, DbOpsImplWithProxy<out DbOps>>
- Inject dbOps proxy into flow 
- Flow must cast to the implementation type

```kotlin
class MyFlow : Flow<Any> {

//    @DbOpsInject
//    private lateinit var myDbOps: MyDbOps // does not work...

    @DbOpsInject(MyDbOps::class)
    private lateinit var myDbOps: DbOps // works

    fun callInjectedDependency(){
        (myDbOps as MyDbOps).doSomething()
    }
}
```
