package com.conal.generics

fun main(){
//    Generics.run()

    val long1 = 2147483647L + 1L
    val int1 = long1.toInt()
    println("long: $long1")
    println("int: $int1")

    var start = -1

    val result = mutableListOf<String>("first", "second", "third", "fourth")
    println(result.map { Pair(++start, it) })

    var seqNo = 2054
    println(result.associateBy { ++seqNo } )
}

class Generics {
//
//    companion object {
//        fun run(){
//            val cage1: Cage<Animal> = Cage(Dog(1, "rosie", FurColor.BLACK), 10.0)
//            val cage2: Cage<Animal> = Cage(Cat(4, "pepper", EyeColor.GREEN), 5.0)
////            val cage3: Cage<String> = Cage(animal = "asdadw", 5.0)
//
//            val dogB: Animal = Dog(2, "goldie", FurColor.BROWN)
//            val catA: Animal = Cat(3, "kumi", EyeColor.BLUE)
//
//            cage1.animal = dogB
//            cage1.animal = catA
//
//            println(cage1)
//            println(cage2)
////            println(cage3)
////            cage3.animal = dogB
//
//        }
//    }
//    private enum class FurColor { BLACK, BROWN }
//    private enum class EyeColor { GREEN, BLUE }
//
//    private open class Animal(val id: Int, val name: String) {
//        override fun toString(): String {
//            return "Animal(id=$id, name='$name')"
//        }
//    }
//
//    private class Dog(id: Int, name: String, val furColor: FurColor) : Animal(id, name) {
//        override fun toString(): String {
//            return "Dog(id=$id, name='$name', furColor=$furColor)"
//        }
//    }
//
//    private class Cat(id: Int, name: String, val eyeColor: EyeColor) : Animal(id, name) {
//        override fun toString(): String {
//            return "Cat(id=$id, name='$name', eyeColor=$eyeColor)"
//        }
//    }
//

//    private class Cage<out T : Animal>(var animal: T, val size: Double) {
//        override fun toString(): String {
//            return "Cage(animal=$animal, size=$size)"
//        }
//        fun getContent(): T
//    }
}

