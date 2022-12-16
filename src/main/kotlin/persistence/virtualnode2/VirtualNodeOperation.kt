package persistence.virtualnode2

import java.time.Instant
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Lob
import javax.persistence.Table
import javax.persistence.Version

@Entity
@Table(name = "virtual_node_operation", schema = "config")
class VirtualNodeOperation(
    @Id
    @Column(name = "id", nullable = false)
    var id: String,

    @Lob
    @Column(name = "data", nullable = false)
    var data: ByteArray,

    @Column(name = "start_timestamp", insertable = false, updatable = true)
    var startTimestamp: Instant? = null,

    @Column(name = "completed_timestamp")
    var completedTimestamp: Instant? = null,

    @Column(name = "state", nullable = false)
    var state: VirtualNodeOperationState,

    @Version
    @Column(name = "entity_version", nullable = false)
    var entityVersion: Int = 0,
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is VirtualNodeOperation) return false

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }
}

enum class VirtualNodeOperationState {
    IN_PROGRESS, COMPLETED, ABORTED
}