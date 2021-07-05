package com.conal.corda

import com.conal.corda.NamedQueryFilter
import com.conal.corda.NamedQueryRequest


class NamedQueryRequestImpl(
    override val queryName: String,
    override val namedParameters: Map<String, Any>,
    override val filter: NamedQueryFilter?,
    override val startPositionExclusive: Long?,
    override val maxCount: Int
) : NamedQueryRequest {
}