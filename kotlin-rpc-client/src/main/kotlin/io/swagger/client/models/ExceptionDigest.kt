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
 * @param exceptionType 
 * @param message 
 */
data class ExceptionDigest (

    val exceptionType: kotlin.String,
    val message: kotlin.String? = null
) {
}