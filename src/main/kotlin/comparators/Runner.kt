package comparators

fun main() {
    val time = System.currentTimeMillis()
    val intTime = time.toInt()
    println(time)
    println(intTime)
}
/*

interface Identifier {
    val name: String
    val version: String
    val signerSummaryHash: String?
}

data class CpkIdentifierWithEntityVersion(
    val cpkIdentifier: CpkIdentifier,
    // if entityVersion is null it is effectively ignored. This is required because VirtualNodeService
    val entityVersion: Int? = null
) : Identifier by cpkIdentifier, Comparable<CpkIdentifierWithEntityVersion> {

    override fun compareTo(other: CpkIdentifierWithEntityVersion) = entityVersionComparator.compare(this, other)
}

private val entityVersionComparator = Comparator.comparing(CpkIdentifierWithEntityVersion::cpkIdentifier, identifierComparator)
    .thenComparing(CpkIdentifierWithEntityVersion::entityVersion, Comparator.nullsFirst(Comparator.naturalOrder<Int?>()))

data class CpkIdentifier(
    override val name: String,
    override val version: String,
    override val signerSummaryHash: String?
) : Identifier, Comparable<CpkIdentifier> {
    override fun compareTo(other: CpkIdentifier) = identifierComparator.compare(this, other)

}

internal val secureHashComparator = Comparator.nullsFirst(Comparator.naturalOrder<String>())

internal val identifierComparator = Comparator.comparing(Identifier::name)
    .thenComparing(Identifier::version, VersionComparator())
    .thenComparing(Identifier::signerSummaryHash, secureHashComparator)*/
