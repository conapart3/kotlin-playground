package com.conal.corda

/**
 * Base request interface for making named query requests.
 */
interface NamedQueryRequest {
    /**
     * Name of the named query registered in the persistence context.
     */
    val queryName: String

    /**
     * Map of named parameters to set as Parameters for the named query
     */
    val namedParameters: Map<String, Any>

    /**
     * Filter to be applied to the results.
     */
    val filter: NamedQueryFilter?

    /**
     * Starting position of the query, not including this value (setting as -1 will obtain results from the beginning).
     */
    val startPositionExclusive: Long?

    /**
     * Max number of results to obtain.
     */
    val maxCount: Int
}