# FlowStarterRPCOpsApi

All URIs are relative to */api/v1*

Method | HTTP request | Description
------------- | ------------- | -------------
[**getFlowstarterFlowoutcomeRunid**](FlowStarterRPCOpsApi.md#getFlowstarterFlowoutcomeRunid) | **GET** /flowstarter/flowoutcome/{runid} | 
[**getFlowstarterFlowoutcomeforclientidClientid**](FlowStarterRPCOpsApi.md#getFlowstarterFlowoutcomeforclientidClientid) | **GET** /flowstarter/flowoutcomeforclientid/{clientid} | 
[**getFlowstarterGetprotocolversion**](FlowStarterRPCOpsApi.md#getFlowstarterGetprotocolversion) | **GET** /flowstarter/getprotocolversion | 
[**getFlowstarterRegisteredflows**](FlowStarterRPCOpsApi.md#getFlowstarterRegisteredflows) | **GET** /flowstarter/registeredflows | 
[**postFlowstarterStartflow**](FlowStarterRPCOpsApi.md#postFlowstarterStartflow) | **POST** /flowstarter/startflow | 

<a name="getFlowstarterFlowoutcomeRunid"></a>
# **getFlowstarterFlowoutcomeRunid**
> RpcFlowOutcomeResponse getFlowstarterFlowoutcomeRunid(runid)



Retrieves flow outcome using stateMachineRunId provided. StateMachineRunId can be obtained when flow is started.

### Example
```kotlin
// Import classes:
//import io.swagger.client.infrastructure.*
//import io.swagger.client.models.*;

val apiInstance = FlowStarterRPCOpsApi()
val runid : kotlin.String = runid_example // kotlin.String | Run ID for a previously started flow
try {
    val result : RpcFlowOutcomeResponse = apiInstance.getFlowstarterFlowoutcomeRunid(runid)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling FlowStarterRPCOpsApi#getFlowstarterFlowoutcomeRunid")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling FlowStarterRPCOpsApi#getFlowstarterFlowoutcomeRunid")
    e.printStackTrace()
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **runid** | **kotlin.String**| Run ID for a previously started flow |

### Return type

[**RpcFlowOutcomeResponse**](RpcFlowOutcomeResponse.md)

### Authorization

[basicAuth](../README.md#basicAuth)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getFlowstarterFlowoutcomeforclientidClientid"></a>
# **getFlowstarterFlowoutcomeforclientidClientid**
> RpcFlowOutcomeResponse getFlowstarterFlowoutcomeforclientidClientid(clientid)



Retrieves flow outcome using client id provided.

### Example
```kotlin
// Import classes:
//import io.swagger.client.infrastructure.*
//import io.swagger.client.models.*;

val apiInstance = FlowStarterRPCOpsApi()
val clientid : kotlin.String = clientid_example // kotlin.String | Client id used for the flow start.
try {
    val result : RpcFlowOutcomeResponse = apiInstance.getFlowstarterFlowoutcomeforclientidClientid(clientid)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling FlowStarterRPCOpsApi#getFlowstarterFlowoutcomeforclientidClientid")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling FlowStarterRPCOpsApi#getFlowstarterFlowoutcomeforclientidClientid")
    e.printStackTrace()
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **clientid** | **kotlin.String**| Client id used for the flow start. |

### Return type

[**RpcFlowOutcomeResponse**](RpcFlowOutcomeResponse.md)

### Authorization

[basicAuth](../README.md#basicAuth)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getFlowstarterGetprotocolversion"></a>
# **getFlowstarterGetprotocolversion**
> kotlin.Int getFlowstarterGetprotocolversion()



### Example
```kotlin
// Import classes:
//import io.swagger.client.infrastructure.*
//import io.swagger.client.models.*;

val apiInstance = FlowStarterRPCOpsApi()
try {
    val result : kotlin.Int = apiInstance.getFlowstarterGetprotocolversion()
    println(result)
} catch (e: ClientException) {
    println("4xx response calling FlowStarterRPCOpsApi#getFlowstarterGetprotocolversion")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling FlowStarterRPCOpsApi#getFlowstarterGetprotocolversion")
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

<a name="getFlowstarterRegisteredflows"></a>
# **getFlowstarterRegisteredflows**
> kotlin.Array&lt;kotlin.String&gt; getFlowstarterRegisteredflows()



### Example
```kotlin
// Import classes:
//import io.swagger.client.infrastructure.*
//import io.swagger.client.models.*;

val apiInstance = FlowStarterRPCOpsApi()
try {
    val result : kotlin.Array<kotlin.String> = apiInstance.getFlowstarterRegisteredflows()
    println(result)
} catch (e: ClientException) {
    println("4xx response calling FlowStarterRPCOpsApi#getFlowstarterRegisteredflows")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling FlowStarterRPCOpsApi#getFlowstarterRegisteredflows")
    e.printStackTrace()
}
```

### Parameters
This endpoint does not need any parameter.

### Return type

**kotlin.Array&lt;kotlin.String&gt;**

### Authorization

[basicAuth](../README.md#basicAuth)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="postFlowstarterStartflow"></a>
# **postFlowstarterStartflow**
> RpcStartFlowResponse postFlowstarterStartflow(body)



### Example
```kotlin
// Import classes:
//import io.swagger.client.infrastructure.*
//import io.swagger.client.models.*;

val apiInstance = FlowStarterRPCOpsApi()
val body : FlowstarterStartflowRequest =  // FlowstarterStartflowRequest | requestBody
try {
    val result : RpcStartFlowResponse = apiInstance.postFlowstarterStartflow(body)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling FlowStarterRPCOpsApi#postFlowstarterStartflow")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling FlowStarterRPCOpsApi#postFlowstarterStartflow")
    e.printStackTrace()
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**FlowstarterStartflowRequest**](FlowstarterStartflowRequest.md)| requestBody |

### Return type

[**RpcStartFlowResponse**](RpcStartFlowResponse.md)

### Authorization

[basicAuth](../README.md#basicAuth)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

