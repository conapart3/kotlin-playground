package persistence.cpx

import java.util.UUID
import javax.persistence.EntityManager
import javax.persistence.LockModeType
import javax.persistence.Persistence
import persistence.cpx.dsl.CpiBuilder
import persistence.cpx.dsl.cpi
import persistence.cpx.dsl.cpiBuilder
import persistence.cpx.dsl.cpk

fun withEntityManager(block: (em: EntityManager) -> Any?): Any? {
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

fun main2() {
    val entity = CpkDbChangeLogAuditEntity(
        CpkDbChangeLogKey(
            "cpkname-not-provided", "1.0.1", "", "file"
        ),
        "filechecksum",
        "content",
        "bbb"
    )
    withEntityManager { em ->
        em.persist(entity)
    }
}

fun main() {
//    setup()
//    withEntityManager { em ->
//        em.findAllCpiMetadata().map {
//            println(it)
//        }
//    }
//    testDsl()
    val testId = UUID.randomUUID()
    val cpi = uploadCpi("cpiFileCheck_$testId", "cpkFileCheck_1_$testId")
    val forceUploadedCpi1 = forceUploadDetachedCpiWithNewCpks(cpi, "force-uploaded-cpi-1-$testId")
    forceUploadDetachedCpiWithNewCpks(forceUploadedCpi1, "force-uploaded-cpi-2-$testId")
//    uploadOriginalCpi()

/*    withEntityManager { em ->
        val results = em.createQuery("from ${CpiMetadataEntity::class.java.simpleName} e where e.name = :name and e.version = :version and e.signerSummaryHash = :ssh")
            .setParameter("name", "test-cordapp")
            .setParameter("version", "1.0.0.0-SNAPSHOT")
            .setParameter("ssh", "SHA-256:CDFF8A944383063AB86AFE61488208CCCC84149911F85BE4F0CACCF399CA9903")
            .resultList
        1
    }*/
}

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

fun uploadCpi(fileChecksum: String? = null, cpkFileChecksum: String? = null): CpiMetadataEntity {
    val cpi = cpi {
        cpk {
            cpkFileChecksum?.let { cpkFileChecksum(it) }
        }
        fileChecksum?.let { fileChecksum(it) }
    }
    withEntityManager {
        it.persist(cpi)
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

    val cpi = assertAndBuild(cpiBuilder, cpiFileChecksum)
}

private fun assertAndBuild(cpiBuilder: CpiBuilder, cpiFileChecksum: String): CpiMetadataEntity {
    assert(cpiBuilder.fileChecksum == cpiFileChecksum)
    assert(cpiBuilder.cpks.all { it.cpiFileChecksumSupplier.invoke() == cpiFileChecksum })
    assert(cpiBuilder.cpks.any { it.metadata!!.fileChecksumSupplier.invoke() == "outer-cpk-checksum-1" })
    assert(cpiBuilder.cpks.any { it.metadata!!.fileChecksumSupplier.invoke()?.contains(cpiBuilder.randomId.toString()) ?: false })

    val cpi = cpiBuilder.build()

    assert(cpi.fileChecksum == cpiFileChecksum)
    assert(cpi.cpks.all { it.id.cpiFileChecksum == cpiFileChecksum })
    assert(cpi.cpks.any { it.metadata.cpkFileChecksum == "outer-cpk-checksum-1" })
    assert(cpi.cpks.any { it.metadata.cpkFileChecksum.contains(cpiBuilder.randomId.toString()) })

    return cpi
}

fun setup() {

    val cpkFileName = "cpkFileName-" + UUID.randomUUID().toString()
    val cpiName = "cpiName-" + UUID.randomUUID().toString()
    val cpkVersion = "5.0.0-SNAPSHOT"
    val cpiVersion = "1.0"
    val signerSummaryHash = "hash"
    val cpkFileChecksum = "cpkFileChecksum-" + UUID.randomUUID().toString()
    val data = "data".toByteArray()
    val id = CpkKey(cpkFileName, cpkVersion, signerSummaryHash)

    withEntityManager {
        val cpkMetadataEntity = CpkMetadataEntity(
            id,
            cpkFileChecksum,
            "str2",
            "str"
        )
        println("Persisting cpk metadata")
        it.persist(cpkMetadataEntity)

        val cpkFileEntity = CpkFileEntity(id, cpkFileChecksum, data)
        println("Persisting cpk file")
        val persistedFile = it.persist(cpkFileEntity)
        println("Persisted file: $persistedFile")
    }
    println("persist transaction complete")

    val data2 = "data2".toByteArray()
    val cpkFile2Checksum = "cpkFileChecksum-" + UUID.randomUUID().toString()

    withEntityManager {
//        updateUsingGetReference(it, cpkFileName, cpkVersion, signerSummaryHash, cpkFile2Checksum, data2)
//        updateUsingQuery(it, cpkFile2Checksum, data2, id)

        val query = """
                FROM ${CpkFileEntity::class.java.simpleName}
                WHERE id IN :cpkKeys
            """.trimIndent()

        val existingCpkFileEntities = it.createQuery(query, CpkFileEntity::class.java)
            .setParameter("cpkKeys", setOf(id))
            .setLockMode(LockModeType.OPTIMISTIC_FORCE_INCREMENT)
            .resultList

        println("Found entities: $existingCpkFileEntities")

        val file = existingCpkFileEntities.first()
        if (file.fileChecksum != cpkFile2Checksum) {
            file.fileChecksum = cpkFile2Checksum
            file.data = data2
        }
        println("Merging file: $file")
        it.merge(file)
    }
    println("merge transaction complete")

//    println("Lets load the data again, see whats inside?")
//    withEntityManager {
//        val file = it.find(CpkFileEntity::class.java, id)
//        println(file)
//        val blobLength = file.data.length().toInt()
//        val bytes = file.data.getBytes(1, blobLength)
//        println(String(bytes))
//    }
}

/*private fun updateUsingGetReference(
    it: EntityManager,
    cpkFileName: String,
    cpkVersion: String,
    signerSummaryHash: String,
    cpkFile2Checksum: String,
    data2: ByteArray
) {
    println("Getting file by reference")
    val file = it.getReference(CpkFileEntity::class.java, CpkKey(cpkFileName, cpkVersion, signerSummaryHash))
    println("Got file reference, about to read fileChecksum, will it trigger a select?")
    if (file.fileChecksum != cpkFile2Checksum) {
        file.fileChecksum = cpkFile2Checksum
        file.data = data2
    }
    println("merging file")
    val mergedFile = it.merge(file)
    println("File at merge time: $mergedFile")
}*/

private fun updateUsingQuery(
    it: EntityManager,
    cpkFile2Checksum: String,
    data2: ByteArray,
    id: CpkKey
) {
    val query = """
            update ${CpkFileEntity::class.java.simpleName} 
            set fileChecksum = :fileChecksum, 
            data = :data,
            entityVersion = entityVersion + 1
            where id = :id and entityVersion = :entityVersion
        """
    val numEntitiesUpdated = it.createQuery(query)
        .setParameter("fileChecksum", cpkFile2Checksum)
        .setParameter("data", data2)
        .setParameter("entityVersion", 0)
        .setParameter("id", id)
        .executeUpdate()
    println("Updated $numEntitiesUpdated entities - id= $id")
}

