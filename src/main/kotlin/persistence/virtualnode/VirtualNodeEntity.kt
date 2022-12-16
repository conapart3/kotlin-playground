package persistence.virtualnode

import java.io.Serializable
import java.time.Instant
import java.util.UUID
import java.util.stream.Stream
import javax.persistence.CascadeType
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.EntityManager
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.persistence.FetchType
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.MapsId
import javax.persistence.OneToOne
import javax.persistence.PrimaryKeyJoinColumn
import javax.persistence.Table
import javax.persistence.Version

@Entity
@Table(name = "virtual_node", schema = "config")
class VirtualNodeEntity(
    @Id
    @Column(name = "holding_identity_id")
    val holdingIdentityId: String,

    @MapsId
    @OneToOne(cascade = [CascadeType.ALL])
    @JoinColumn(name = "holding_identity_id")
    val holdingIdentity: HoldingIdentityEntity,

    @Column(name = "cpi_name", nullable = false)
    var cpiName: String,

    @Column(name = "cpi_version", nullable = false)
    var cpiVersion: String,

    @Column(name = "cpi_signer_summary_hash", nullable = false)
    var cpiSignerSummaryHash: String,

    @Enumerated(value = EnumType.STRING)
    @Column(name = "flow_p2p_operational_status", nullable = false)
    var flowP2pOperationalStatus: OperationalStatus = OperationalStatus.ACTIVE,

    @Enumerated(value = EnumType.STRING)
    @Column(name = "flow_start_operational_status", nullable = false)
    var flowStartOperationalStatus: OperationalStatus = OperationalStatus.ACTIVE,

    @Enumerated(value = EnumType.STRING)
    @Column(name = "flow_operational_status", nullable = false)
    var flowOperationalStatus: OperationalStatus = OperationalStatus.ACTIVE,

    @Enumerated(value = EnumType.STRING)
    @Column(name = "vault_db_operational_status", nullable = false)
    var vaultDbOperationalStatus: OperationalStatus = OperationalStatus.ACTIVE,

    @OneToOne(cascade = [CascadeType.MERGE], fetch = FetchType.LAZY)
    @JoinColumn(name = "operation_in_progress")
    var operationInProgress: VirtualNodeOperationEntity? = null,

    @Column(name = "vault_ddl_connection_id")
    var vaultDDLConnectionId: UUID? = null,

    @Column(name = "vault_dml_connection_id")
    var vaultDMLConnectionId: UUID? = null,

    @Column(name = "crypto_ddl_connection_id")
    var cryptoDDLConnectionId: UUID? = null,

    @Column(name = "crypto_dml_connection_id")
    var cryptoDMLConnectionId: UUID? = null,

    @Column(name = "uniqueness_ddl_connection_id")
    var uniquenessDDLConnectionId: UUID? = null,

    @Column(name = "uniqueness_dml_connection_id")
    var uniquenessDMLConnectionId: UUID? = null,

    @Column(name = "insert_ts", insertable = false)
    var insertTimestamp: Instant? = null,

    @Column(name = "is_deleted", nullable = false)
    var isDeleted: Boolean = false,

    @Version
    @Column(name = "entity_version", nullable = false)
    var entityVersion: Int = 0,
): Serializable {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as VirtualNodeEntity

        if (holdingIdentity != other.holdingIdentity) return false

        return true
    }

    override fun hashCode(): Int {
        return holdingIdentity.hashCode()
    }
}

enum class OperationalStatus {
    ACTIVE, INACTIVE
}

/**
 * If you change this function ensure that you check the generated SQL from
 * hibnernate in the "virtual node entity query test" in
 * [net.corda.libs.configuration.datamodel.tests.VirtualNodeEntitiesIntegrationTest]
 */
fun EntityManager.findAllVirtualNodes(): Stream<VirtualNodeEntity> {
    val query = criteriaBuilder!!.createQuery(VirtualNodeEntity::class.java)!!
    val root = query.from(VirtualNodeEntity::class.java)
    root.fetch<Any, Any>("holdingIdentity")
    query.select(root)

    return createQuery(query).resultStream
}

fun EntityManager.findVirtualNode(holdingIdentityShortHash: String): VirtualNodeEntity? {
    val queryBuilder = with(criteriaBuilder!!) {
        val queryBuilder = createQuery(VirtualNodeEntity::class.java)!!
        val root = queryBuilder.from(VirtualNodeEntity::class.java)
        root.fetch<Any, Any>("holdingIdentity")
        queryBuilder.where(
            equal(
                root.get<HoldingIdentityEntity>("holdingIdentity").get<String>("holdingIdentityShortHash"),
                parameter(String::class.java, "shortId")
            )
        ).orderBy(desc(root.get<String>("cpiVersion")))
        queryBuilder
    }

    return createQuery(queryBuilder)
        .setParameter("shortId", holdingIdentityShortHash)
        .setMaxResults(1)
        .resultList
        .singleOrNull()
}