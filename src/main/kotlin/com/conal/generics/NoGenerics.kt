package com.conal.generics

class NoGenerics {

    companion object {
        fun main(){
            val cage1: Cage = Cage(Dog(1, "rosie", FurColor.BLACK), 10.0)
            val cage2: Cage = Cage(Cat(4, "pepper", EyeColor.GREEN), 5.0)

            val dogB: Dog = Dog(2, "goldie", FurColor.BROWN)
            val catA: Cat = Cat(3, "kumi", EyeColor.BLUE)

            cage1.animal = dogB
            cage1.animal = catA

            println(cage1)
            println(cage2)
        }
    }
    private enum class FurColor { BLACK, BROWN }
    private enum class EyeColor { GREEN, BLUE }

    private open class Animal(val id: Int, val name: String) {
        override fun toString(): String {
            return "Animal(id=$id, name='$name')"
        }
    }

    private class Dog(id: Int, name: String, val furColor: FurColor) : Animal(id, name) {
        override fun toString(): String {
            return "Dog(id=$id, name='$name', furColor=$furColor)"
        }
    }

    private class Cat(id: Int, name: String, val eyeColor: EyeColor) : Animal(id, name) {
        override fun toString(): String {
            return "Cat(id=$id, name='$name', eyeColor=$eyeColor)"
        }
    }


    private class Cage(var animal: Animal, val size: Double) {
        override fun toString(): String {
            return "Cage(animal=$animal, size=$size)"
        }
    }
}

