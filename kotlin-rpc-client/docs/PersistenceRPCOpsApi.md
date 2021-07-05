# PersistenceRPCOpsApi

All URIs are relative to */api/v1*

Method | HTTP request | Description
------------- | ------------- | -------------
[**getPersistenceGetprotocolversion**](PersistenceRPCOpsApi.md#getPersistenceGetprotocolversion) | **GET** /persistence/getprotocolversion | 
[**postPersistenceQuery**](PersistenceRPCOpsApi.md#postPersistenceQuery) | **POST** /persistence/query | 

<a name="getPersistenceGetprotocolversion"></a>
# **getPersistenceGetprotocolversion**
> kotlin.Int getPersistenceGetprotocolversion()



### Example
```kotlin
// Import classes:
//import io.swagger.client.infrastructure.*
//import io.swagger.client.models.*;

val apiInstance = PersistenceRPCOpsApi()
try {
    val result : kotlin.Int = apiInstance.getPersistenceGetprotocolversion()
    println(result)
} catch (e: ClientException) {
    println("4xx response calling PersistenceRPCOpsApi#getPersistenceGetprotocolversion")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling PersistenceRPCOpsApi#getPersistenceGetprotocolversion")
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

<a name="postPersistenceQuery"></a>
# **postPersistenceQuery**
> DurableReturnResultOfRpcNamedQueryResponseItem postPersistenceQuery(body)



Execute a pre-defined named query

### Example
```kotlin
// Import classes:
//import io.swagger.client.infrastructure.*
//import io.swagger.client.models.*;

val apiInstance = PersistenceRPCOpsApi()
val body : PersistenceQueryRequest =  // PersistenceQueryRequest | requestBody
try {
    val result : DurableReturnResultOfRpcNamedQueryResponseItem = apiInstance.postPersistenceQuery(body)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling PersistenceRPCOpsApi#postPersistenceQuery")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling PersistenceRPCOpsApi#postPersistenceQuery")
    e.printStackTrace()
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**PersistenceQueryRequest**](PersistenceQueryRequest.md)| requestBody |

### Return type

[**DurableReturnResultOfRpcNamedQueryResponseItem**](DurableReturnResultOfRpcNamedQueryResponseItem.md)

### Authorization

[basicAuth](../README.md#basicAuth)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

