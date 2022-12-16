package persistence.virtualnode2

import java.util.Objects
import java.util.UUID
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "holding_identity", schema = "config")
@Suppress("LongParameterList")
internal class HoldingIdentityEntity(
    @Id
    @Column(name = "holding_identity_id", nullable = false)
    val holdingIdentityShortHash: String,
    @Column(name = "holding_identity_full_hash", nullable = false)
    var holdingIdentityFullHash: String,
    @Column(name = "x500_name", nullable = false)
    var x500Name: String,
    @Column(name = "mgm_group_id", nullable = false)
    var mgmGroupId: String,
    @Column(name = "vault_ddl_connection_id", nullable = true)
    var vaultDDLConnectionId: UUID?,
    @Column(name = "vault_dml_connection_id", nullable = true)
    var vaultDMLConnectionId: UUID?,
    @Column(name = "crypto_ddl_connection_id", nullable = true)
    var cryptoDDLConnectionId: UUID?,
    @Column(name = "crypto_dml_connection_id", nullable = true)
    var cryptoDMLConnectionId: UUID?,
    @Column(name = "uniqueness_ddl_connection_id", nullable = true)
    var uniquenessDDLConnectionId: UUID?,
    @Column(name = "uniqueness_dml_connection_id", nullable = true)
    var uniquenessDMLConnectionId: UUID?,
    @Column(name = "hsm_connection_id", nullable = true)
    var hsmConnectionId: UUID?
) {
    fun update(
        vaultDdlConnectionId: UUID?,
        vaultDmlConnectionId: UUID?,
        cryptoDdlConnectionId: UUID?,
        cryptoDmlConnectionId: UUID?,
        uniquenessDDLConnectionId: UUID?,
        uniquenessDMLConnectionId: UUID?
    ) {
        this.vaultDDLConnectionId = vaultDdlConnectionId
        this.vaultDMLConnectionId = vaultDmlConnectionId
        this.cryptoDDLConnectionId = cryptoDdlConnectionId
        this.cryptoDMLConnectionId = cryptoDmlConnectionId
        this.uniquenessDDLConnectionId = uniquenessDDLConnectionId
        this.uniquenessDMLConnectionId = uniquenessDMLConnectionId
    }

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