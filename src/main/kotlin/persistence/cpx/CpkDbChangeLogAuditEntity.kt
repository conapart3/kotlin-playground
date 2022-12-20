package persistence.cpx

import java.io.Serializable
import java.time.Instant
import java.util.UUID
import javax.persistence.Column
import javax.persistence.Embeddable
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
    var id: CpkDbChangeLogAuditKey,
    @Column(name = "content", nullable = false)
    val content: String,
    @Column(name = "is_deleted", nullable = false)
    var isDeleted: Boolean = false
) {
    // this TS is managed on the DB itself
    @Column(name = "insert_ts", insertable = false, updatable = false)
    val insertTimestamp: Instant? = null
}

@Embeddable
data class CpkDbChangeLogAuditKey(
    @Column(name = "cpk_file_checksum", nullable = false)
    val fileChecksum: String,
    @Column(name = "changeset_id", nullable = false)
    val changesetId: UUID,
    @Column(name = "entity_version", nullable = false)
    var entityVersion: Int,
    @Column(name = "file_path", nullable = false)
    val filePath: String,
) : Serializable