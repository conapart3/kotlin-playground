package com.conal.visibilitytest.somepackage

fun main(){
//    MyClass().doSomething() // doesn't compile
//    MyClass().MyInnerClass().doSomething() // doesn't compile
}

class MyClass {

    fun doSomethingBetter(){
        MyInnerClass().doSomething()
    }

    private inner class MyInnerClass {
        fun doSomething() {
            println("something")
        }
    }
}