package persistence.cpx.dsl

import java.util.UUID
import persistence.cpx.CpkFileEntity

fun cpkFile(init: CpkFileBuilder.() -> Unit): CpkFileEntity {
    val cpkFileBuilder = CpkFileBuilder()
    init(cpkFileBuilder)
    return cpkFileBuilder.build()
}

class CpkFileBuilder(internal var fileChecksumSupplier: () -> String? = { null }, private val randomUUID: UUID = UUID.randomUUID()) {

    fun fileChecksum(value: String): CpkFileBuilder {
        fileChecksumSupplier = { value }
        return this
    }

    fun build(): CpkFileEntity {
        return CpkFileEntity(
            fileChecksumSupplier.invoke() ?: "file_checksum_$randomUUID",
            "data_$randomUUID".toByteArray(),
        )
    }
}