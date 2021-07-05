# VaultQueryRPCOpsApi

All URIs are relative to */api/v1*

Method | HTTP request | Description
------------- | ------------- | -------------
[**getVaultqueryrpcGetprotocolversion**](VaultQueryRPCOpsApi.md#getVaultqueryrpcGetprotocolversion) | **GET** /vaultqueryrpc/getprotocolversion | 
[**postVaultqueryrpcQueryvault**](VaultQueryRPCOpsApi.md#postVaultqueryrpcQueryvault) | **POST** /vaultqueryrpc/queryvault | 

<a name="getVaultqueryrpcGetprotocolversion"></a>
# **getVaultqueryrpcGetprotocolversion**
> kotlin.Int getVaultqueryrpcGetprotocolversion()



### Example
```kotlin
// Import classes:
//import io.swagger.client.infrastructure.*
//import io.swagger.client.models.*;

val apiInstance = VaultQueryRPCOpsApi()
try {
    val result : kotlin.Int = apiInstance.getVaultqueryrpcGetprotocolversion()
    println(result)
} catch (e: ClientException) {
    println("4xx response calling VaultQueryRPCOpsApi#getVaultqueryrpcGetprotocolversion")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling VaultQueryRPCOpsApi#getVaultqueryrpcGetprotocolversion")
    e.printStackTrace()
}
```

### Parameters
This endpoint does not need any parameter.

### Return type

**kotlin.Int**

### Authorization

[basicAuth](../README.md#basicAuth)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="postVaultqueryrpcQueryvault"></a>
# **postVaultqueryrpcQueryvault**
> DurableReturnResultOfRpcVaultStateResponseItem postVaultqueryrpcQueryvault(body)



Allows retrieval of vault state identifiers matching filtering criteria

### Example
```kotlin
// Import classes:
//import io.swagger.client.infrastructure.*
//import io.swagger.client.models.*;

val apiInstance = VaultQueryRPCOpsApi()
val body : VaultqueryrpcQueryvaultRequest =  // VaultqueryrpcQueryvaultRequest | requestBody
try {
    val result : DurableReturnResultOfRpcVaultStateResponseItem = apiInstance.postVaultqueryrpcQueryvault(body)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling VaultQueryRPCOpsApi#postVaultqueryrpcQueryvault")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling VaultQueryRPCOpsApi#postVaultqueryrpcQueryvault")
    e.printStackTrace()
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**VaultqueryrpcQueryvaultRequest**](VaultqueryrpcQueryvaultRequest.md)| requestBody |

### Return type

[**DurableReturnResultOfRpcVaultStateResponseItem**](DurableReturnResultOfRpcVaultStateResponseItem.md)

### Authorization

[basicAuth](../README.md#basicAuth)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

