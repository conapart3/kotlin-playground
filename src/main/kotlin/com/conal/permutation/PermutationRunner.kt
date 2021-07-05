package com.conal.permutation

fun main(){
    PermutationRunner().permutation("abc")
}

/**
 * To do so, permutation takes the index of the current element current_indexcurrent index as one of the arguments.
 * Then, it swaps the current element with every other element in the array, lying towards its right,
 * so as to generate a new ordering of the array elements. After the swapping has been done, it makes another call to
 * permute but this time with the index of the next element in the array.
 * While returning back, we reverse the swapping done in the current function call.
 */
class PermutationRunner{
    fun permutation(str: String) {
        permutation("", str)
    }

    fun permutation(prefix: String, str: String) {
        val n = str.length
        if(n==0) println(prefix)
        else {
            for (i in 0 until n){
                permutation(prefix + str[i], str.substring(0, i) + str.substring(i+1, n))
            }
        }
    }
}