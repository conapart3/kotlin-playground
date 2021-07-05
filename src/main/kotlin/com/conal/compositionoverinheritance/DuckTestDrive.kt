package com.conal.compositionoverinheritance

fun main() {
    val md = MallardDuck()
    md.display()
    md.quack()
    md.fly()
    md.swim()

    val da = DuckAdaptor(md)
    da.fly()
    da.gobble()

    val ta = TurkeyAdaptor(object : TurkeyLike {
        override fun gobble() {
            println("gobble goblle ")
        }
        override fun fly() {
            println("flapflapflap")
        } })
    ta.fly()
    ta.quack()

    val decoyDuck = DecoyDuck(FlyRocketPowered(), Squeak())
    decoyDuck.display()
    decoyDuck.swim()
    decoyDuck.quack()
    decoyDuck.fly()

    val decoyAsTurkey = DuckAdaptor(decoyDuck)
    decoyAsTurkey.gobble()
    decoyAsTurkey.fly()
}

interface Flyable{
    fun fly()
}

class FlyWithWings : Flyable {
    override fun fly() {
        println("Fly with wings")
    }
}
class CannotFly : Flyable {
    override fun fly() {
        println("Cannot fly")
    }
}
class FlyRocketPowered : Flyable {
    override fun fly() {
        println("Fly rocket powered")
    }
}

interface TurkeyLike : Flyable {
    fun gobble()
}
interface DuckLike : Flyable, Quackable { }

interface Quackable {
    fun quack()
}

class Squeak() : Quackable {
    override fun quack() {
        println("Squeak")
    }
}
class DuckCall() : Quackable {
    override fun quack() {
        println("DuckCall")
    }
}
class CannotQuack() : Quackable {
    override fun quack() {
        println("CannotQuack")
    }
}
class Quack() : Quackable {
    override fun quack() {
        println("Quack")
    }
}

// Duck adaptor takes a duck and give it turkey functions
class DuckAdaptor(val duckBehavior: DuckLike) : TurkeyLike {
    override fun gobble() {
        duckBehavior.quack()
    }
    override fun fly() {
        duckBehavior.fly()
    }
}

// Turkey adaptor takes a turkey and gives it duck functions
class TurkeyAdaptor(val turkeyBehavior: TurkeyLike) : DuckLike {
    override fun fly() {
        turkeyBehavior.fly()
    }
    override fun quack() {
        turkeyBehavior.gobble()
    }
}

abstract class Duck(var flyBehavior: Flyable, var quackBehavior: Quackable) : DuckLike {
    abstract fun display()
    override fun fly() {
        flyBehavior.fly()
    }
    override fun quack() {
        quackBehavior.quack()
    }
    fun swim(){
        println("Duck Swim")
    }
}

class MallardDuck : Duck(FlyWithWings(), Quack()) {
    override fun display() {
        println("Display MallardDuck")
    }
}

class RedHeadDuck : Duck(FlyWithWings(), Squeak()) {
    override fun display() {
        println("Display RedHeadDuck")
    }
}

class RubberDuck : Duck(CannotFly(), CannotQuack()) {
    override fun display() {
        println("Display RubberDuck")
    }
}

class DecoyDuck(flyBehavior: Flyable, quackBehavior: Quackable) : Duck(flyBehavior, quackBehavior) {
    override fun display() {
        println("Display DecoyDuck")
    }
}