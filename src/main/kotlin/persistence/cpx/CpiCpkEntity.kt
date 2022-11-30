package persistence.cpx

import java.io.Serializable
import java.time.Instant
import javax.persistence.CascadeType
import javax.persistence.Column
import javax.persistence.Embeddable
import javax.persistence.EmbeddedId
import javax.persistence.Entity
import javax.persistence.JoinColumn
import javax.persistence.OneToOne
import javax.persistence.PreUpdate
import javax.persistence.Table
import javax.persistence.Version

/**
 * Cpi/cpk mapping table.
 */
@Entity
@Table(name = "cpi_cpk", schema = "config")
data class CpiCpkEntity(
    @EmbeddedId
    val id: CpiCpkKey,
    @Column(name = "cpk_file_name", nullable = false)
    var cpkFileName: String,
    @OneToOne(cascade = [CascadeType.MERGE, CascadeType.PERSIST])
    @JoinColumn(name = "cpk_file_checksum", referencedColumnName = "file_checksum", insertable = false, updatable = false)
    var metadata: CpkMetadataEntity,
    @Version
    @Column(name = "entity_version", nullable = false)
    var entityVersion: Int = 0
) {
    // Initial population of this TS is managed on the DB itself
    @Column(name = "insert_ts", insertable = false, updatable = true)
    var insertTimestamp: Instant? = null
    @Column(name = "is_deleted", nullable = false)
    var isDeleted: Boolean = false

    @PreUpdate
    fun onUpdate() {
        insertTimestamp = Instant.now()
    }
}

@Embeddable
data class CpiCpkKey(
    @Column(name = "cpi_file_checksum")
    val cpiFileChecksum: String,
    @Column(name = "cpk_file_checksum")
    val cpkFileChecksum: String
): Serializable