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


/**
 * 
 * @param jarHash 
 * @param licence 
 * @param minimumPlatformVersion 
 * @param name 
 * @param shortName 
 * @param targetPlatformVersion 
 * @param type 
 * @param vendor 
 * @param version 
 */
data class RpcCorDappInfo (

    val jarHash: kotlin.String,
    val licence: kotlin.String,
    val minimumPlatformVersion: kotlin.Int,
    val name: kotlin.String,
    val shortName: kotlin.String,
    val targetPlatformVersion: kotlin.Int,
    val type: kotlin.String,
    val vendor: kotlin.String,
    val version: kotlin.String
) {
}