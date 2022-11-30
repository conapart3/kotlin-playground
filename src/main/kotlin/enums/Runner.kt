package enums

fun main(){
    println("Down is ${LifecycleStatus.DOWN}")
    println("Down.name is ${LifecycleStatus.DOWN.name}")

    val noLife = MyRpcopsImpl()
    val life = MyRpcopsWithLifecycleImpl()
    val lifeAtClass = MyRpcopsLifeAtClassImpl()

    val list = listOf<RPCOps>(noLife, life, lifeAtClass)

    val filtered = list.filterIsInstance<Lifecycle>()
    println(filtered)
}

enum class LifecycleStatus {
    UP, DOWN, ERROR
}


interface Lifecycle

interface RPCOps

interface MyRPCOpsWithLifecycle : RPCOps, Lifecycle

interface MyRPCOps : RPCOps

class MyRpcopsImpl : MyRPCOps

class MyRpcopsLifeAtClassImpl : MyRPCOps, Lifecycle

class MyRpcopsWithLifecycleImpl : MyRPCOpsWithLifecycle
