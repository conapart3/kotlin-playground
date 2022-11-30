package persistence.cpx.dsl

import java.util.UUID
import persistence.cpx.CpkKey
import persistence.cpx.CpkMetadataEntity

fun cpk(init: CpkMetadataBuilder.() -> Unit): CpkMetadataBuilder {
    val cpkBuilder = CpkMetadataBuilder()
    init(cpkBuilder)
    return cpkBuilder
}

class CpkMetadataBuilder(internal var fileChecksumSupplier: () -> String? = { null }, internal val randomId: UUID = UUID.randomUUID()) {

    internal var formatVersion: String? = null
    internal var serializedMetadata: String? = null
    internal var cpkName: String? = null
    internal var cpkVersion: String? = null
    internal var cpkSignerSummaryHash: String? = null

    fun fileChecksum(value: String?): CpkMetadataBuilder {
        fileChecksumSupplier = { value }
        return this
    }

    fun formatVersion(value: String?): CpkMetadataBuilder {
        formatVersion = value
        return this
    }

    fun serializedMetadata(value: String?): CpkMetadataBuilder {
        serializedMetadata = value
        return this
    }

    fun cpkName(value: String?): CpkMetadataBuilder {
        cpkName = value
        return this
    }

    fun cpkVersion(value: String?): CpkMetadataBuilder {
        cpkVersion = value
        return this
    }

    fun cpkSignerSummaryHash(value: String?): CpkMetadataBuilder {
        cpkSignerSummaryHash = value
        return this
    }

    fun build(): CpkMetadataEntity {
        return CpkMetadataEntity(
            CpkKey(
                cpkName ?: "name_$randomId",
                cpkVersion ?: "version_$randomId",
                cpkSignerSummaryHash ?: "ssh_$randomId"
            ),
            fileChecksumSupplier.invoke() ?: "cpk_file_checksum_$randomId",
            formatVersion ?: "format_version_$randomId".take(12),
            serializedMetadata ?: "serialized_metadata_$randomId"
        )
    }
}