package copy

fun main() {
    val a = ClassWithDefaults(22, "twenty-two")
    val b = a.copy(a = 33)
    println(a)
    println(b)

}

data class ClassWithDefaults(
    val a: Int = 0,
    val b: String = "zero"
)

data class ClassWithoutDefaults(
    val a: Int,
    val b: Int
)