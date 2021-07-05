package com.conal.delegation

/**
 * Favour composition over inheritance is the new IN trend!
 * Kotlin doesn't like Inheritance.
 * It is possible to use delegation instead of inheritance
 */

interface Human {
    fun speak()
}

interface Animal {
    fun hibernate()

}

class GeneticExperiment(human: Human, animal: Animal) : Human by human, Animal by animal {

}

fun main() {
    val human = object : Human {
        override fun speak() {
            fun speak() {
                println("human speaking")
            }
        }
    }
    val exp = GeneticExperiment(human, object : Animal {
        override fun hibernate() {
            println("Animal hibernate")
        }
    })
    exp.hibernate()
    exp.speak()
}