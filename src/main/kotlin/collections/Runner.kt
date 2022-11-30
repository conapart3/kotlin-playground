package collections

fun main() {
    val listClass = emptyList<String>()::class.java
    val setClass = emptySet<String>()::class.java
    val collectionClass = Collection::class.java

    println(collectionClass.isAssignableFrom(listClass))
    println(collectionClass.isAssignableFrom(setClass))
}