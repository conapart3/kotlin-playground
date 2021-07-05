# GroupManagerRPCOpsApi

All URIs are relative to */api/v1*

Method | HTTP request | Description
------------- | ------------- | -------------
[**getGroupmanagerGetprotocolversion**](GroupManagerRPCOpsApi.md#getGroupmanagerGetprotocolversion) | **GET** /groupmanager/getprotocolversion | 
[**postGroupmanagerActivate**](GroupManagerRPCOpsApi.md#postGroupmanagerActivate) | **POST** /groupmanager/activate | 
[**postGroupmanagerSuspend**](GroupManagerRPCOpsApi.md#postGroupmanagerSuspend) | **POST** /groupmanager/suspend | 

<a name="getGroupmanagerGetprotocolversion"></a>
# **getGroupmanagerGetprotocolversion**
> kotlin.Int getGroupmanagerGetprotocolversion()



### Example
```kotlin
// Import classes:
//import io.swagger.client.infrastructure.*
//import io.swagger.client.models.*;

val apiInstance = GroupManagerRPCOpsApi()
try {
    val result : kotlin.Int = apiInstance.getGroupmanagerGetprotocolversion()
    println(result)
} catch (e: ClientException) {
    println("4xx response calling GroupManagerRPCOpsApi#getGroupmanagerGetprotocolversion")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling GroupManagerRPCOpsApi#getGroupmanagerGetprotocolversion")
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

<a name="postGroupmanagerActivate"></a>
# **postGroupmanagerActivate**
> postGroupmanagerActivate(body)



Activates membership of the given party or re-activates suspended member of the group.

### Example
```kotlin
// Import classes:
//import io.swagger.client.infrastructure.*
//import io.swagger.client.models.*;

val apiInstance = GroupManagerRPCOpsApi()
val body : GroupmanagerActivateRequest =  // GroupmanagerActivateRequest | requestBody
try {
    apiInstance.postGroupmanagerActivate(body)
} catch (e: ClientException) {
    println("4xx response calling GroupManagerRPCOpsApi#postGroupmanagerActivate")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling GroupManagerRPCOpsApi#postGroupmanagerActivate")
    e.printStackTrace()
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GroupmanagerActivateRequest**](GroupmanagerActivateRequest.md)| requestBody |

### Return type

null (empty response body)

### Authorization

[basicAuth](../README.md#basicAuth)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: Not defined

<a name="postGroupmanagerSuspend"></a>
# **postGroupmanagerSuspend**
> postGroupmanagerSuspend(body)



Suspends membership of the given party which temporarily prevents it to participate in Membership Group.

### Example
```kotlin
// Import classes:
//import io.swagger.client.infrastructure.*
//import io.swagger.client.models.*;

val apiInstance = GroupManagerRPCOpsApi()
val body : GroupmanagerSuspendRequest =  // GroupmanagerSuspendRequest | requestBody
try {
    apiInstance.postGroupmanagerSuspend(body)
} catch (e: ClientException) {
    println("4xx response calling GroupManagerRPCOpsApi#postGroupmanagerSuspend")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling GroupManagerRPCOpsApi#postGroupmanagerSuspend")
    e.printStackTrace()
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GroupmanagerSuspendRequest**](GroupmanagerSuspendRequest.md)| requestBody |

### Return type

null (empty response body)

### Authorization

[basicAuth](../README.md#basicAuth)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: Not defined

