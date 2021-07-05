# FlowManagerRPCOpsApi

All URIs are relative to */api/v1*

Method | HTTP request | Description
------------- | ------------- | -------------
[**getFlowmanagerrpcopsDebugcheckpoints**](FlowManagerRPCOpsApi.md#getFlowmanagerrpcopsDebugcheckpoints) | **GET** /flowmanagerrpcops/debugcheckpoints | 
[**getFlowmanagerrpcopsDumpcheckpoints**](FlowManagerRPCOpsApi.md#getFlowmanagerrpcopsDumpcheckpoints) | **GET** /flowmanagerrpcops/dumpcheckpoints | 
[**getFlowmanagerrpcopsGetprotocolversion**](FlowManagerRPCOpsApi.md#getFlowmanagerrpcopsGetprotocolversion) | **GET** /flowmanagerrpcops/getprotocolversion | 
[**getFlowmanagerrpcopsListactive**](FlowManagerRPCOpsApi.md#getFlowmanagerrpcopsListactive) | **GET** /flowmanagerrpcops/listactive | 
[**postFlowmanagerrpcopsKillflowRunid**](FlowManagerRPCOpsApi.md#postFlowmanagerrpcopsKillflowRunid) | **POST** /flowmanagerrpcops/killflow/{runid} | 

<a name="getFlowmanagerrpcopsDebugcheckpoints"></a>
# **getFlowmanagerrpcopsDebugcheckpoints**
> getFlowmanagerrpcopsDebugcheckpoints()



### Example
```kotlin
// Import classes:
//import io.swagger.client.infrastructure.*
//import io.swagger.client.models.*;

val apiInstance = FlowManagerRPCOpsApi()
try {
    apiInstance.getFlowmanagerrpcopsDebugcheckpoints()
} catch (e: ClientException) {
    println("4xx response calling FlowManagerRPCOpsApi#getFlowmanagerrpcopsDebugcheckpoints")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling FlowManagerRPCOpsApi#getFlowmanagerrpcopsDebugcheckpoints")
    e.printStackTrace()
}
```

### Parameters
This endpoint does not need any parameter.

### Return type

null (empty response body)

### Authorization

[basicAuth](../README.md#basicAuth)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: Not defined

<a name="getFlowmanagerrpcopsDumpcheckpoints"></a>
# **getFlowmanagerrpcopsDumpcheckpoints**
> getFlowmanagerrpcopsDumpcheckpoints()



### Example
```kotlin
// Import classes:
//import io.swagger.client.infrastructure.*
//import io.swagger.client.models.*;

val apiInstance = FlowManagerRPCOpsApi()
try {
    apiInstance.getFlowmanagerrpcopsDumpcheckpoints()
} catch (e: ClientException) {
    println("4xx response calling FlowManagerRPCOpsApi#getFlowmanagerrpcopsDumpcheckpoints")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling FlowManagerRPCOpsApi#getFlowmanagerrpcopsDumpcheckpoints")
    e.printStackTrace()
}
```

### Parameters
This endpoint does not need any parameter.

### Return type

null (empty response body)

### Authorization

[basicAuth](../README.md#basicAuth)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: Not defined

<a name="getFlowmanagerrpcopsGetprotocolversion"></a>
# **getFlowmanagerrpcopsGetprotocolversion**
> kotlin.Int getFlowmanagerrpcopsGetprotocolversion()



### Example
```kotlin
// Import classes:
//import io.swagger.client.infrastructure.*
//import io.swagger.client.models.*;

val apiInstance = FlowManagerRPCOpsApi()
try {
    val result : kotlin.Int = apiInstance.getFlowmanagerrpcopsGetprotocolversion()
    println(result)
} catch (e: ClientException) {
    println("4xx response calling FlowManagerRPCOpsApi#getFlowmanagerrpcopsGetprotocolversion")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling FlowManagerRPCOpsApi#getFlowmanagerrpcopsGetprotocolversion")
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

<a name="getFlowmanagerrpcopsListactive"></a>
# **getFlowmanagerrpcopsListactive**
> kotlin.collections.Map&lt;kotlin.String, StateMachineRunId&gt; getFlowmanagerrpcopsListactive()



### Example
```kotlin
// Import classes:
//import io.swagger.client.infrastructure.*
//import io.swagger.client.models.*;

val apiInstance = FlowManagerRPCOpsApi()
try {
    val result : kotlin.collections.Map<kotlin.String, StateMachineRunId> = apiInstance.getFlowmanagerrpcopsListactive()
    println(result)
} catch (e: ClientException) {
    println("4xx response calling FlowManagerRPCOpsApi#getFlowmanagerrpcopsListactive")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling FlowManagerRPCOpsApi#getFlowmanagerrpcopsListactive")
    e.printStackTrace()
}
```

### Parameters
This endpoint does not need any parameter.

### Return type

[**kotlin.collections.Map&lt;kotlin.String, StateMachineRunId&gt;**](StateMachineRunId.md)

### Authorization

[basicAuth](../README.md#basicAuth)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="postFlowmanagerrpcopsKillflowRunid"></a>
# **postFlowmanagerrpcopsKillflowRunid**
> kotlin.Boolean postFlowmanagerrpcopsKillflowRunid(runid)



### Example
```kotlin
// Import classes:
//import io.swagger.client.infrastructure.*
//import io.swagger.client.models.*;

val apiInstance = FlowManagerRPCOpsApi()
val runid : kotlin.String = runid_example // kotlin.String | Run ID for a previously started flow
try {
    val result : kotlin.Boolean = apiInstance.postFlowmanagerrpcopsKillflowRunid(runid)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling FlowManagerRPCOpsApi#postFlowmanagerrpcopsKillflowRunid")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling FlowManagerRPCOpsApi#postFlowmanagerrpcopsKillflowRunid")
    e.printStackTrace()
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **runid** | **kotlin.String**| Run ID for a previously started flow |

### Return type

**kotlin.Boolean**

### Authorization

[basicAuth](../README.md#basicAuth)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

