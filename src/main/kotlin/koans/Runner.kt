package koans

fun main() {

}

// write an extension function for Dog to feed him
// create a Dog and walk him and feed him

data class Dog(
    private val name: String,
    private val colour: String,
) {
    fun goForWalk() {
        println("Walking $name")
    }
}