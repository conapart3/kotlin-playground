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

import io.swagger.client.models.SecureHash

/**
 * 
 * @param index 
 * @param txhash 
 */
data class StateRef (

    val index: kotlin.Int,
    val txhash: SecureHash
) {
}