package persistence.cpx

import java.time.Instant
import javax.persistence.Column
import javax.persistence.EmbeddedId
import javax.persistence.Entity
import javax.persistence.EntityManager
import javax.persistence.Table
import javax.persistence.Version

/**
 * Audit representation of a DB ChangeLog (Liquibase) file associated with a CPK.
 */
@Entity
@Table(name = "cpk_db_change_log_audit", schema = "config")
class CpkDbChangeLogAuditEntity(
    @EmbeddedId
    var id: CpkDbChangeLogKey,
    @Column(name = "cpk_file_checksum", nullable = false, unique = true)
    val fileChecksum: String,
    @Column(name = "content", nullable = false)
    val content: String,
    @Column(name = "nullable_field", nullable = true)
    val nullableField: String?
) {
    // Similar to the non audit type, this structure does not distinguish the root changelogs from changelog include
    // files (or CSVs, which we do not need to support). So, to find the root, you need to look for a filename
    // convention. See the comment in the companion object of VirtualNodeDbChangeLog.
    // for the convention used when populating these records.
    @Version
    @Column(name = "entity_version", nullable = false)
    var entityVersion: Int = 0

    // this TS is managed on the DB itself
    @Column(name = "insert_ts", insertable = false, updatable = false)
    val insertTimestamp: Instant? = null
}

/*
 * Find all the audit db changelogs for a CPI
 */
fun findDbChangeLogAuditForCpi(
    entityManager: EntityManager,
    cpiName: String,
    cpiVersion: String,
    cpiSSH: String?,
): List<CpkDbChangeLogAuditEntity> = entityManager.createQuery(
    "SELECT changelog " +
            "FROM ${CpkDbChangeLogAuditEntity::class.simpleName} AS changelog INNER JOIN " +
            "${CpiCpkEntity::class.simpleName} AS cpi " +
            "ON changelog.id.cpkName = cpi.metadata.id.cpkName AND " +
            "   changelog.id.cpkVersion = cpi.id.cpkVersion AND " +
            "   changelog.id.cpkSignerSummaryHash = cpi.id.cpkSignerSummaryHash "+
            "WHERE cpi.id.cpiName = :name AND "+
            "      cpi.id.cpiVersion = :version AND "+
            "      cpi.id.cpiSignerSummaryHash = :signerSummaryHash AND "+
            "      changelog.isDeleted = FALSE",
    // TODO - what order should we return?
    CpkDbChangeLogAuditEntity::class.java
)
    .setParameter("name", cpiName)
    .setParameter("version", cpiVersion)
    .setParameter("signerSummaryHash", cpiSSH ?: "")
    .resultList
