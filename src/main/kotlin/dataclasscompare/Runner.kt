package dataclasscompare

import java.nio.ByteBuffer
import java.util.Arrays

fun main() {
    val first = VirtualNodeContext(setOf(
        CpkIdentifierWithHash(CpkIdentifier("name", "vers"), "def"),
    ))
    val second = VirtualNodeContext(setOf(
        CpkIdentifierWithHash(CpkIdentifier("name", "vers"), "abc"),
    ))

    val map = mutableMapOf<VirtualNodeContext, String>()
    map[first] = "calculator"

    println(map.size)

    map[second] = "balculator"

    println(map.size)

    println(first == second)
}

data class VirtualNodeContext(
    val cpkIdentifiers: Set<CpkIdentifierWithHash>,
)

data class CpkIdentifierWithHash(
    val cpkIdentifier: CpkIdentifier,
    val contentHash: String
) : Comparable<CpkIdentifierWithHash> {
    override fun compareTo(other: CpkIdentifierWithHash): Int {
        return cpkIdentifier.compareTo(other.cpkIdentifier)
    }
}

data class CpkIdentifier(
    override val name: String,
    override val version: String,
) : Identifier, Comparable<CpkIdentifier> {
    override fun compareTo(other: CpkIdentifier) = identifierComparator.compare(this, other)
}
interface Identifier {
    val name: String
    val version: String
}

internal val identifierComparator = Comparator.comparing(Identifier::name)
