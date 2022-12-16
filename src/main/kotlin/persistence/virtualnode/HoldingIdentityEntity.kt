package persistence.virtualnode

import java.io.Serializable
import java.util.Objects
import java.util.UUID
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "holding_identity", schema = "config")
class HoldingIdentityEntity(
    @Id
    @Column(name = "holding_identity_id", nullable = false)
    val holdingIdentityShortHash: String,
    @Column(name = "holding_identity_full_hash", nullable = false)
    var holdingIdentityFullHash: String,
    @Column(name = "x500_name", nullable = false)
    var x500Name: String,
    @Column(name = "mgm_group_id", nullable = false)
    var mgmGroupId: String,
    @Column(name = "hsm_connection_id", nullable = true)
    var hsmConnectionId: UUID?
) : Serializable {
    override fun equals(other: Any?): Boolean {
        if(this === other) return true
        if (javaClass != other?.javaClass) return false

        other as HoldingIdentityEntity

        return Objects.equals(holdingIdentityShortHash, other.holdingIdentityShortHash)
    }

    override fun hashCode(): Int {
        return Objects.hashCode(holdingIdentityShortHash)
    }
}