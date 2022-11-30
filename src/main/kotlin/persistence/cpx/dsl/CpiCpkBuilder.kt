package persistence.cpx.dsl

import java.lang.Exception
import java.util.UUID
import persistence.cpx.CpiCpkEntity
import persistence.cpx.CpiCpkKey
import persistence.cpx.CpkMetadataEntity

//fun cpiCpk(init: CpiCpkBuilder.() -> Unit): CpiCpkBuilder {
//    val cpiCpkBuilder = CpiCpkBuilder()
//    init(cpiCpkBuilder)
//    return cpiCpkBuilder
//}

class CpiCpkBuilder(internal var cpiFileChecksumSupplier: () -> String?, internal val randomId: UUID = UUID.randomUUID()) {

    constructor(cpk: CpkMetadataBuilder, cpiFileChecksumSupplier: () -> String?) : this(cpiFileChecksumSupplier) {
        name = cpk.cpkName
        version = cpk.cpkVersion
        signerSummaryHash = cpk.cpkSignerSummaryHash
        formatVersion = cpk.formatVersion
        serializedMetadata = cpk.serializedMetadata
        metadata = cpk
        cpkFileChecksum = cpk.fileChecksumSupplier.invoke()
    }

    // cpk
    internal var name: String? = null
    internal var version: String? = null
    internal var signerSummaryHash: String? = null

    // cpicpk
    internal var fileName: String? = null
    internal var metadata: CpkMetadataBuilder? = null
    internal var cpkFileChecksum: String? = null

    //metadata
    internal var formatVersion: String? = null
    internal var serializedMetadata: String? = null

    fun name(value: String): CpiCpkBuilder {
        name = value
        return this
    }

    fun version(value: String): CpiCpkBuilder {
        version = value
        return this
    }

    fun signerSummaryHash(value: String): CpiCpkBuilder {
        signerSummaryHash = value
        return this
    }

    fun fileName(value: String): CpiCpkBuilder {
        fileName = value
        return this
    }

    fun metadata(init: CpkMetadataBuilder.() -> Unit): CpiCpkBuilder {
        val cpkMetadata = CpkMetadataBuilder(::supplyCpkFileChecksum, randomId)
        init(cpkMetadata)
        metadata = cpkMetadata
        return this
    }

    fun metadata(value: CpkMetadataBuilder): CpiCpkBuilder {
        metadata = value
        return this
    }

    fun cpkFileChecksum(value: String): CpiCpkBuilder {
        cpkFileChecksum = value
        return this
    }

    fun serializedMetadata(value: String): CpiCpkBuilder {
        serializedMetadata = value
        return this
    }

    fun supplyCpkFileChecksum() = cpkFileChecksum

    fun build(): CpiCpkEntity {
        if (cpkFileChecksum == null) cpkFileChecksum = "cpk_file_checksum_$randomId"
        val cpk: CpkMetadataEntity = metadata?.build() ?: CpkMetadataBuilder(::supplyCpkFileChecksum, randomId)
            .cpkName(name)
            .cpkVersion(version)
            .cpkSignerSummaryHash(signerSummaryHash)
            .formatVersion(formatVersion)
            .serializedMetadata(serializedMetadata)
            .build()

        return CpiCpkEntity(
            CpiCpkKey(
                cpiFileChecksumSupplier.invoke() ?: throw Exception("CpiCpkBuilder.cpiFileChecksum is mandatory"),
                supplyCpkFileChecksum()!!
            ),
            fileName ?: "cpk_filename_$randomId",
            cpk
        )
    }
}