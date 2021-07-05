package com.conal.proxy

import java.lang.reflect.InvocationHandler
import java.lang.reflect.Method
import java.lang.reflect.Proxy

fun main(){
    val p1 = ConcretePlayer(100.9)
    val p2 = ConcretePlayer(102.2)

    val players = mutableMapOf<String, Player>("a" to p1, "b" to p2)
    val proxiedPlayers = mutableMapOf<String, Player>()
    for((name, player) in players) {
        proxiedPlayers[name] = Proxy.newProxyInstance(ClassLoader.getSystemClassLoader(), arrayOf<Class<*>>(Player::class.java), PlayerProxy(player)) as Player
    }
    while(p1.health > 0 || p2.health > 0){
        val random1 = (0..20).random()
        println("P1 go: damage = $random1")
        proxiedPlayers["a"].apply { this!!.damagePlayer(random1.toDouble()) }

        val random2 = (0..20).random()
        println("P2 go: damage = $random2")
        proxiedPlayers["b"].apply { this!!.damagePlayer(random2.toDouble()) }
    }
}

data class ConcretePlayer(override var health: Double = 100.0) : Player {
    override fun damagePlayer(damage: Double) {
        println("Concrete damage done")
        health -= damage
    }
}

interface Player {
    var health: Double
    //This method handles all damage! Hooray!
    fun damagePlayer(damage: Double)
}

class PlayerProxy(private val src: Player) : InvocationHandler {

    override fun invoke(proxy: Any, method: Method, args: Array<out Any>): Any {
        //Proceed to call the original Player object to adhere to the API
        val back = method.invoke(this.src, args);
        if (method.name == "damagePlayer" && args.size == 1) {
            println("Proxy damage done")

            //Add our own effects!
            //Alternatively, add a hook so you can register multiple things here, and avoid coding directly inside a Proxy
//            if (/* 50% random chance */) {
                //double damage!
//            args[1] as Double
//            args[1] = args[1] + args[1]
                //or perhaps use `source`/args[0] to add to a damage count?
//            }
        }
        return back
    }
}