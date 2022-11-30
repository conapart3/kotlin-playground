package stdlib

fun main() {
    ClassAccessors(ClassAccessors::class.java)
        .go()
}

class ClassAccessors<T : Any>(private val endpointClass: Class<T>) {

    fun go() {
        println("$endpointClass")
    }
}