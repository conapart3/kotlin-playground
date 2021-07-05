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

import io.swagger.client.models.RpcStartFlowRequestParameters

/**
 * Parameters required for starting the flow.
 * @param clientId 
 * @param flowName 
 * @param parameters 
 */
data class RpcStartFlowRequest (

    val clientId: kotlin.String,
    val flowName: kotlin.String,
    val parameters: RpcStartFlowRequestParameters
) {
}