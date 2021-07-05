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

import io.swagger.client.models.CordaX500Name
import io.swagger.client.models.RpcEndpointInfo

/**
 * 
 * @param certificate 
 * @param endpoints 
 * @param groupId 
 * @param identityKeys 
 * @param key 
 * @param notaryService 
 * @param platformVersion 
 * @param serial 
 * @param status 
 * @param x500Name 
 */
data class RpcMemberInfo (

    val certificate: kotlin.String? = null,
    val endpoints: kotlin.Array<RpcEndpointInfo>,
    val groupId: kotlin.String,
    val identityKeys: kotlin.Array<kotlin.String>,
    val key: kotlin.String,
    val notaryService: CordaX500Name? = null,
    val platformVersion: kotlin.Int,
    val serial: kotlin.Long,
    val status: kotlin.String,
    val x500Name: CordaX500Name
) {
}