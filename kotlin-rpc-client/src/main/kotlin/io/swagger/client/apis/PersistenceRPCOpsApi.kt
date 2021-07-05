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
package io.swagger.client.apis

import io.swagger.client.models.DurableReturnResultOfRpcNamedQueryResponseItem
import io.swagger.client.models.PersistenceQueryRequest

import io.swagger.client.infrastructure.*

class PersistenceRPCOpsApi(basePath: kotlin.String = "/api/v1") : ApiClient(basePath) {

    /**
     * 
     * 
     * @return kotlin.Int
     */
    @Suppress("UNCHECKED_CAST")
    fun getPersistenceGetprotocolversion(): kotlin.Int {
        
        val localVariableConfig = RequestConfig(
                RequestMethod.GET,
                "/persistence/getprotocolversion"
        )
        val response = request<kotlin.Int>(
                localVariableConfig
        )

        return when (response.responseType) {
            ResponseType.Success -> (response as Success<*>).data as kotlin.Int
            ResponseType.Informational -> TODO()
            ResponseType.Redirection -> TODO()
            ResponseType.ClientError -> throw ClientException((response as ClientError<*>).body as? String ?: "Client error")
            ResponseType.ServerError -> throw ServerException((response as ServerError<*>).message ?: "Server error")
        }
    }
    /**
     * 
     * Execute a pre-defined named query
     * @param body requestBody 
     * @return DurableReturnResultOfRpcNamedQueryResponseItem
     */
    @Suppress("UNCHECKED_CAST")
    fun postPersistenceQuery(body: PersistenceQueryRequest): DurableReturnResultOfRpcNamedQueryResponseItem {
        val localVariableBody: kotlin.Any? = body
        
        val localVariableConfig = RequestConfig(
                RequestMethod.POST,
                "/persistence/query"
        )
        val response = request<DurableReturnResultOfRpcNamedQueryResponseItem>(
                localVariableConfig, localVariableBody
        )

        return when (response.responseType) {
            ResponseType.Success -> (response as Success<*>).data as DurableReturnResultOfRpcNamedQueryResponseItem
            ResponseType.Informational -> TODO()
            ResponseType.Redirection -> TODO()
            ResponseType.ClientError -> throw ClientException((response as ClientError<*>).body as? String ?: "Client error")
            ResponseType.ServerError -> throw ServerException((response as ServerError<*>).message ?: "Server error")
        }
    }
}
