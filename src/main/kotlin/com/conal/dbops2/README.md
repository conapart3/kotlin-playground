# CGLib subclass proxies 

- NO DbOps interface
- Proxy created is using CGLIB to create proxy by subclass
- User annotates their MyDbOps with @DbOpsInjectable
- CGLIB scans for impls of DbOps - **must be open!**
- Register DbOps into diContainer and store in map <Class<out DbOps>, DbOpsImplWithProxy<out DbOps>>
- Inject dbOps proxy into flow 
- Flow must cast to the implementation type

```kotlin
class MyFlow : Flow<Any> {

//    @DbOpsInject
//    private lateinit var myDbOps: MyDbOps // does not work...

    @DbOpsInject
    private lateinit var myDbOps: MyDbOps // works

    fun callInjectedDependency(){
        (myDbOps as MyDbOps).doSomething()
    }
}
```
