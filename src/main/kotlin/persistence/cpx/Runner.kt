package persistence.cpx

import java.util.UUID
import javax.persistence.EntityManager
import javax.persistence.Persistence
import persistence.cpx.dsl.CpkFileBuilder
import persistence.cpx.dsl.cpi
import persistence.cpx.dsl.cpiBuilder
import persistence.cpx.dsl.cpk
import persistence.cpx.dsl.cpkFile

fun <T : Any?> withEntityManager(block: (em: EntityManager) -> T): T {
    val emf = Persistence.createEntityManagerFactory("example-unit")
    val em = emf.createEntityManager()
    val result = try {
        em.transaction.begin()
        val temp = block.invoke(em)
        em.transaction.commit()
        temp
    } finally {
        em.close()
        emf.close()
    }
    return result
}

fun main() {
    val testId = UUID.randomUUID()
    println("Test ID: $testId")

    val cpiName = "cpiName_$testId"
    val cpiVersion = "cpiVersion_$testId"
    val cpiSsh = "cpiSsh_$testId"
    val cpkName = "cpkName_$testId"
    val cpkVersion = "cpkVersion_$testId"
    val cpkSsh = "cpkSsh_$testId"

    val originalCpi = cpi {
        cpk {
            cpkFileChecksum("cpkFileChecksum-original_$testId")
            name(cpkName)
            version(cpkVersion)
            signerSummaryHash(cpkSsh)
        }
        name(cpiName)
        version(cpiVersion)
        signerSummaryHash(cpiSsh)
    }

    withEntityManager {
        it.persist(originalCpi)
    }

    val cpkFileChecksum1 = "aaa1_$testId"
    val cpkFileChecksum2 = "aaa2_$testId"
    val cpi = cpi {
        entityVersion(0)
        name(cpiName)
        version(cpiVersion)
        signerSummaryHash(cpiSsh)
        cpk {
            name(cpkName)
            version(cpkVersion)
            signerSummaryHash(cpkSsh)
            cpkFileChecksum(cpkFileChecksum1)
        }
        cpk {
            name(cpkName)
            version(cpkVersion)
            signerSummaryHash(cpkSsh)
            cpkFileChecksum(cpkFileChecksum2)
        }
    }

    withEntityManager { it.merge(cpi) }

    val cpis = withEntityManager { em ->
        em.findAllCpiMetadata()
    }
    cpis.map { println(it) }

    withEntityManager { em ->
        em.persist(cpkFile { fileChecksum(cpkFileChecksum1) })
        em.persist(cpkFile { fileChecksum(cpkFileChecksum2) })
    }

    val newRand = UUID.randomUUID().toString()
    val forcedUploadCpi = cpi {
        entityVersion(1)
        name(cpiName)
        version(cpiVersion)
        signerSummaryHash(cpiSsh)
        cpk {
            name("forced_1_$newRand")
            version(cpkVersion)
            signerSummaryHash(cpkSsh)
            cpkFileChecksum("forced_check_1_$newRand")
        }
        cpk {
            name("forced_2_$newRand")
            version(cpkVersion)
            signerSummaryHash(cpkSsh)
            cpkFileChecksum("forced_check_2_$newRand")
        }
        // this CPK already exists - what happens here?
        cpk {
            name(cpkName)
            version(cpkVersion)
            signerSummaryHash(cpkSsh)
            cpkFileChecksum(cpkFileChecksum1)
        }
    }
    withEntityManager { em ->
        em.merge(forcedUploadCpi)
    }

    println("Test ID: $testId finished.")
}



/*
fun forceUploadDetachedCpiWithNewCpks(cpi: CpiMetadataEntity, newCpiFileChecksum: String): CpiMetadataEntity {
    val randomizer = UUID.randomUUID()
    val cpi = cpi {
        name(cpi.name)
        version(cpi.version)
        signerSummaryHash(cpi.signerSummaryHash)
        fileChecksum(newCpiFileChecksum)
        groupId(cpi.groupId)
        groupPolicy(cpi.groupPolicy)
        fileUploadRequestId(cpi.fileUploadRequestId)
        fileName(cpi.fileName)
        cpk {
            fileChecksum("force-uploaded-cpk-1-$randomizer")
        }
        cpk {
            fileChecksum("force-uploaded-cpk-2-$randomizer")
        }
        cpk {
            fileChecksum("force-uploaded-cpk-3-$randomizer")
        }
    }
    withEntityManager {
        it.merge(cpi)
    }
    return cpi
}

fun testDsl() {
    val cpiFileChecksum = "cpi1"
    val outerCpk = cpk {
        fileChecksum("outer-cpk-checksum-1")
    }
    val cpiBuilder = cpiBuilder {
        name("name")
        version("version")
        signerSummaryHash("signerSummaryHash")
        fileName("fileName")
        groupPolicy("groupPolicy")
        groupId("groupId")
        fileUploadRequestId("fileUploadRequestId")
        cpk {
            name("cpkname")
            version("cpkversion")
            signerSummaryHash("cpksignerSummaryHash")
            fileName("cpk1.cpk")
//            cpiFileChecksum("1.0")
            metadata {
                formatVersion("format")
            }
            cpkFileChecksum("cpk1")
        }
        cpk(outerCpk) {
            fileName("added-filename")
        }
        fileChecksum(cpiFileChecksum)
    }
    println(cpiBuilder)

//    val cpi = assertAndBuild(cpiBuilder, cpiFileChecksum)
}*/

/*
private fun assertAndBuild(cpiBuilder: CpiBuilder, cpiFileChecksum: String): CpiMetadataEntity {
    assert(cpiBuilder.fileChecksum == cpiFileChecksum)
    assert(cpiBuilder.cpks.all { it.cpiNameSupplier.invoke() == cpiFileChecksum })
    assert(cpiBuilder.cpks.any { it.metadata!!.fileChecksumSupplier.invoke() == "outer-cpk-checksum-1" })
    assert(cpiBuilder.cpks.any { it.metadata!!.fileChecksumSupplier.invoke()?.contains(cpiBuilder.randomId.toString()) ?: false })

    val cpi = cpiBuilder.build()

    assert(cpi.fileChecksum == cpiFileChecksum)
    assert(cpi.cpks.all { it.id.cpkFileChecksum == cpiFileChecksum })
    assert(cpi.cpks.any { it.metadata.cpkFileChecksum == "outer-cpk-checksum-1" })
    assert(cpi.cpks.any { it.metadata.cpkFileChecksum.contains(cpiBuilder.randomId.toString()) })

    return cpi
}
*/
