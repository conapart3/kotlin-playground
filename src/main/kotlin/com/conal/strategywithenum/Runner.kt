package com.conal.strategywithenum

fun main(){
    val context = Context(StrategyA())
    context.executeStrategy()

    context.strategy = StrategyB()
    context.executeStrategy()

}




interface ClassicStrategy{
    fun execute();
}

class StrategyA() : ClassicStrategy {
    override fun execute() {
        println("A")
    }
}

class StrategyB() : ClassicStrategy {
    override fun execute() {
        println("B")
    }
}

class Context(var strategy: ClassicStrategy){
    fun executeStrategy(){
        strategy.execute()
    }
}


