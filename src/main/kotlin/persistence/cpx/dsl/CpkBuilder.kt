package persistence.cpx.dsl

import java.util.UUID
import persistence.cpx.CpkMetadataEntity

class CpkBuilder(private val randomUUID: UUID = UUID.randomUUID()) {
    private var name: String? = null
    private var version: String? = null
    private var signerSummaryHash: String? = null
    private var fileChecksum: String? = null
    private var formatVersion: String? = null
    private var serializedMetadata: String? = null

    fun name(value: String): CpkBuilder {
        name = value
        return this
    }

    fun version(value: String): CpkBuilder {
        version = value
        return this
    }

    fun signerSummaryHash(value: String): CpkBuilder {
        signerSummaryHash = value
        return this
    }

    fun fileChecksum(value: String): CpkBuilder {
        fileChecksum = value
        return this
    }

    fun formatVersion(value: String): CpkBuilder {
        formatVersion = value
        return this
    }

    fun serializedMetadata(value: String): CpkBuilder {
        serializedMetadata = value
        return this
    }

    fun build(): CpkMetadataEntity {
        return CpkMetadataEntity(
            fileChecksum ?: "file_checksum_$randomUUID",
            name ?: "name_$randomUUID",
            version ?: "version_$randomUUID",
            signerSummaryHash ?: "ssh_$randomUUID",
            formatVersion ?: "2",
            serializedMetadata ?: "serialized_metadata_$randomUUID"
        )
    }
}