/**
 * HTTP RPC demo
 * Exposing RPCOps interfaces as webservices
 *
 * OpenAPI spec version: 1
 * 
 *
 * NOTE: This class is auto generated by the swagger code generator program.
 * https://github.com/swagger-api/swagger-codegen.git
 * Do not edit the class manually.
 */
package io.swagger.client.models

import io.swagger.client.models.DurableStreamContext
import io.swagger.client.models.RpcNamedQueryRequest

/**
 * PersistenceQueryRequest
 * @param request 
 * @param context 
 */
data class PersistenceQueryRequest (

    val request: RpcNamedQueryRequest,
    val context: DurableStreamContext
) {
}