package persistence.virtualnode

import java.time.Instant
import java.util.UUID
import liquibasetransactions.withEntityManager
import persistence.cpx.dsl.cpi

fun main() {
    val uuid = UUID.randomUUID().toString().take(12)
    persistCpi(uuid)
    persistVnodeEntity(uuid)
    changeOperation(uuid, VirtualNodeOperationState.IN_PROGRESS)
    changeOperation(uuid, VirtualNodeOperationState.ABORTED)
    removeOperation(uuid)
}

fun removeOperation(s: String) {
    withEntityManager {
        val e = it.find(VirtualNodeEntity::class.java, s)
        e.operationInProgress = null
    }
}

fun persistCpi(cpiName: String) {
    val cpi = cpi {
        name(cpiName)
        version("1")
        signerSummaryHash("1")
    }
    withEntityManager {
        it.persist(cpi)
    }
}

private fun persistVnodeEntity(s: String) {
    withEntityManager { em ->
//        val ref = em.getReference(HoldingIdentityEntity::class.java, "hash1")
        em.persist(
            VirtualNodeEntity(
                cpiName = s,
                cpiVersion = "1",
                cpiSignerSummaryHash = "1",
//                currentOperation = VirtualNodeOperation("req2", "".toByteArray(), VirtualNodeOperationState.IN_PROGRESS),
                holdingIdentityId = s,
                holdingIdentity = HoldingIdentityEntity(s, "hash2", "2", "2", null)
            )
        )
    }
}

private fun changeOperation(s: String, operationState: VirtualNodeOperationState) {
    withEntityManager { em ->
        val hie = em.find(HoldingIdentityEntity::class.java, s)
        val vne = em.find(VirtualNodeEntity::class.java, hie.holdingIdentityShortHash)
        vne.operationInProgress = VirtualNodeOperationEntity(
            "id1", "req1", "null", operationState, Instant.now()
        )
        em.merge(vne)
    }
}