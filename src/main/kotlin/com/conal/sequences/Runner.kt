package com.conal.sequences

import com.conal.delegation.Animal
import com.conal.delegation.GeneticExperiment
import com.conal.delegation.Human


fun main() {
    var count = 10
    val sequence = generateSequence {
        (count--).takeIf { it > 0 }
    }

//    println(sequence)
//    println(sequence.toList())

    // index does NOT always contain 0
    sequence.forEachIndexed { index, element ->
        println("Index: $index, element: $element")
    }
}