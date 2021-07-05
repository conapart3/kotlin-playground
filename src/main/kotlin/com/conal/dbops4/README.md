# Intermediate interfaces 

- Empty DbOps interface
- CorDapp developer will create intermediate interfaces that extend DbOps
- Proxies created for interfaces that extend DbOps
- If an implementation implements multiple intermediate interfaces one proxy will work for both
- Proxy created is using Java dynamic proxy
- We need a DbOpsDIContainer to hold DBOps and their proxies (identified by the implementation class) Map<Class<out DbOps>, DbOpsImplWithProxy<out DbOps>>
- We inject the proxy into the flow 

## What should the proxy do?
- build the request to be put on Kafka
    - FQN for the class and method (`net.corda.MyDbOps#execute`) 
    - serialized args for the function call
- it provides type safety  
- suspend the flow fiber and await for results
- Q. Flow fiber suspension will use a different mechanism than writing via storage worker?

## What should the storage worker do?
- Has a list of DbOps implementations 
- Has dependency injection mechanism similar to node for injecting PersistenceService into DbOps
- receive request
- match FQN with DbOps map
- match method with method in DbOps class
- set parameters
- invoke method

## What will it do with the results?
- serialize and put onto kafka queue 
- result object needs an ID to send back to origin flow

## What will node do when it receives response
- read message from queue
- awaken flow fiber with given ID
- resume flow with the results