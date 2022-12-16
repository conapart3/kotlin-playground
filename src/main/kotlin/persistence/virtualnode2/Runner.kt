package persistence.virtualnode2

import liquibasetransactions.withEntityManager
import persistence.cpx.CpiMetadataEntity
import persistence.cpx.dsl.cpi

fun main() {
//    withEntityManager { em ->
//        em.persist(
//            cpi {
//                name("cpiname")
//                version("cpiversion")
//                signerSummaryHash("cpissh")
//            }
//        )
//        em.persist(
//            VirtualNodeEntity(
//                HoldingIdentityEntity(
//                    "hash", "hashFull", "x500name", "group1",
//                    null, null, null, null, null, null, null
//                ),
//                "cpiname",
//                "cpiversion",
//                "cpissh",
//                "ACTIVE"
//            )
//        )
//    }
    withEntityManager {
        it.createQuery("from ${VirtualNodeEntity::class.java.simpleName}")
            .singleResult
    }
}