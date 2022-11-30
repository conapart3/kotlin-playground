package persistence.cart

import java.io.Serializable
import javax.persistence.Column
import javax.persistence.Embeddable
import javax.persistence.EmbeddedId
import javax.persistence.MappedSuperclass

@MappedSuperclass
open class PersistentState(@EmbeddedId override var stateRef: PersistentStateRef?) : DirectStatePersistable {

    constructor() : this(stateRef = null)
}

@Embeddable
//@Immutable
data class PersistentStateRef(
    @Suppress("MagicNumber") // column width
    @Column(name = "transaction_id", length = 144, nullable = false)
    var txId: String,

    @Column(name = "output_index", nullable = false)
    var index: Int
) : Serializable

interface DirectStatePersistable : StatePersistable {
    val stateRef: PersistentStateRef?
}

interface StatePersistable
