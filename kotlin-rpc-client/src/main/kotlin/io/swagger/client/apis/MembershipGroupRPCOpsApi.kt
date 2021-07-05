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

import io.swagger.client.models.CordaX500Name
import io.swagger.client.models.RpcMemberInfo

import io.swagger.client.infrastructure.*

class MembershipGroupRPCOpsApi(basePath: kotlin.String = "/api/v1") : ApiClient(basePath) {

    /**
     * 
     * Returns all MemberInfo currently visible in the membership group.
     * @return kotlin.Array<RpcMemberInfo>
     */
    @Suppress("UNCHECKED_CAST")
    fun getMembershipgroupGetallmembers(): kotlin.Array<RpcMemberInfo> {
        
        val localVariableConfig = RequestConfig(
                RequestMethod.GET,
                "/membershipgroup/getallmembers"
        )
        val response = request<kotlin.Array<RpcMemberInfo>>(
                localVariableConfig
        )

        return when (response.responseType) {
            ResponseType.Success -> (response as Success<*>).data as kotlin.Array<RpcMemberInfo>
            ResponseType.Informational -> TODO()
            ResponseType.Redirection -> TODO()
            ResponseType.ClientError -> throw ClientException((response as ClientError<*>).body as? String ?: "Client error")
            ResponseType.ServerError -> throw ServerException((response as ServerError<*>).message ?: "Server error")
        }
    }
    /**
     * 
     * Returns MemberInfo with given name.
     * @param name Identity of member. 
     * @return RpcMemberInfo
     */
    @Suppress("UNCHECKED_CAST")
    fun getMembershipgroupGetmember(name: kotlin.String): RpcMemberInfo {
        val localVariableQuery: MultiValueMap = mapOf("name" to listOf("$name"))
        val localVariableConfig = RequestConfig(
                RequestMethod.GET,
                "/membershipgroup/getmember", query = localVariableQuery
        )
        val response = request<RpcMemberInfo>(
                localVariableConfig
        )

        return when (response.responseType) {
            ResponseType.Success -> (response as Success<*>).data as RpcMemberInfo
            ResponseType.Informational -> TODO()
            ResponseType.Redirection -> TODO()
            ResponseType.ClientError -> throw ClientException((response as ClientError<*>).body as? String ?: "Client error")
            ResponseType.ServerError -> throw ServerException((response as ServerError<*>).message ?: "Server error")
        }
    }
    /**
     * 
     * Retrieves set of members given matching criteria.
     * @param query Query string to perform match upon. 
     * @param exactmatch Whether to do exact match or allow fuzzy matches. (optional)
     * @return kotlin.Array<CordaX500Name>
     */
    @Suppress("UNCHECKED_CAST")
    fun getMembershipgroupGetmembersfromname(query: kotlin.String, exactmatch: kotlin.Boolean? = null): kotlin.Array<CordaX500Name> {
        val localVariableQuery: MultiValueMap = mapOf("query" to listOf("$query"), "exactmatch" to listOf("$exactmatch"))
        val localVariableConfig = RequestConfig(
                RequestMethod.GET,
                "/membershipgroup/getmembersfromname", query = localVariableQuery
        )
        val response = request<kotlin.Array<CordaX500Name>>(
                localVariableConfig
        )

        return when (response.responseType) {
            ResponseType.Success -> (response as Success<*>).data as kotlin.Array<CordaX500Name>
            ResponseType.Informational -> TODO()
            ResponseType.Redirection -> TODO()
            ResponseType.ClientError -> throw ClientException((response as ClientError<*>).body as? String ?: "Client error")
            ResponseType.ServerError -> throw ServerException((response as ServerError<*>).message ?: "Server error")
        }
    }
    /**
     * 
     * Returns the groupID of the membership.
     * @return kotlin.String
     */
    @Suppress("UNCHECKED_CAST")
    fun getMembershipgroupGetmygroupid(): kotlin.String {
        
        val localVariableConfig = RequestConfig(
                RequestMethod.GET,
                "/membershipgroup/getmygroupid"
        )
        val response = request<kotlin.String>(
                localVariableConfig
        )

        return when (response.responseType) {
            ResponseType.Success -> (response as Success<*>).data as kotlin.String
            ResponseType.Informational -> TODO()
            ResponseType.Redirection -> TODO()
            ResponseType.ClientError -> throw ClientException((response as ClientError<*>).body as? String ?: "Client error")
            ResponseType.ServerError -> throw ServerException((response as ServerError<*>).message ?: "Server error")
        }
    }
    /**
     * 
     * Returns Node&#x27;s MemberInfo.
     * @return RpcMemberInfo
     */
    @Suppress("UNCHECKED_CAST")
    fun getMembershipgroupGetmymemberinfo(): RpcMemberInfo {
        
        val localVariableConfig = RequestConfig(
                RequestMethod.GET,
                "/membershipgroup/getmymemberinfo"
        )
        val response = request<RpcMemberInfo>(
                localVariableConfig
        )

        return when (response.responseType) {
            ResponseType.Success -> (response as Success<*>).data as RpcMemberInfo
            ResponseType.Informational -> TODO()
            ResponseType.Redirection -> TODO()
            ResponseType.ClientError -> throw ClientException((response as ClientError<*>).body as? String ?: "Client error")
            ResponseType.ServerError -> throw ServerException((response as ServerError<*>).message ?: "Server error")
        }
    }
    /**
     * 
     * 
     * @return kotlin.Int
     */
    @Suppress("UNCHECKED_CAST")
    fun getMembershipgroupGetprotocolversion(): kotlin.Int {
        
        val localVariableConfig = RequestConfig(
                RequestMethod.GET,
                "/membershipgroup/getprotocolversion"
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
}
