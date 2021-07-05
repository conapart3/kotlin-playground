# NodeLifecycleRPCOpsApi

All URIs are relative to */api/v1*

Method | HTTP request | Description
------------- | ------------- | -------------
[**getNodelifecycleGetnodeconfiguration**](NodeLifecycleRPCOpsApi.md#getNodelifecycleGetnodeconfiguration) | **GET** /nodelifecycle/getnodeconfiguration | 
[**getNodelifecycleGetprotocolversion**](NodeLifecycleRPCOpsApi.md#getNodelifecycleGetprotocolversion) | **GET** /nodelifecycle/getprotocolversion | 
[**getNodelifecycleIswaitingforshutdown**](NodeLifecycleRPCOpsApi.md#getNodelifecycleIswaitingforshutdown) | **GET** /nodelifecycle/iswaitingforshutdown | 
[**postNodelifecycleTerminate**](NodeLifecycleRPCOpsApi.md#postNodelifecycleTerminate) | **POST** /nodelifecycle/terminate | 

<a name="getNodelifecycleGetnodeconfiguration"></a>
# **getNodelifecycleGetnodeconfiguration**
> kotlin.String getNodelifecycleGetnodeconfiguration()



### Example
```kotlin
// Import classes:
//import io.swagger.client.infrastructure.*
//import io.swagger.client.models.*;

val apiInstance = NodeLifecycleRPCOpsApi()
try {
    val result : kotlin.String = apiInstance.getNodelifecycleGetnodeconfiguration()
    println(result)
} catch (e: ClientException) {
    println("4xx response calling NodeLifecycleRPCOpsApi#getNodelifecycleGetnodeconfiguration")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling NodeLifecycleRPCOpsApi#getNodelifecycleGetnodeconfiguration")
    e.printStackTrace()
}
```

### Parameters
This endpoint does not need any parameter.

### Return type

**kotlin.String**

### Authorization

[basicAuth](../README.md#basicAuth)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getNodelifecycleGetprotocolversion"></a>
# **getNodelifecycleGetprotocolversion**
> kotlin.Int getNodelifecycleGetprotocolversion()



### Example
```kotlin
// Import classes:
//import io.swagger.client.infrastructure.*
//import io.swagger.client.models.*;

val apiInstance = NodeLifecycleRPCOpsApi()
try {
    val result : kotlin.Int = apiInstance.getNodelifecycleGetprotocolversion()
    println(result)
} catch (e: ClientException) {
    println("4xx response calling NodeLifecycleRPCOpsApi#getNodelifecycleGetprotocolversion")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling NodeLifecycleRPCOpsApi#getNodelifecycleGetprotocolversion")
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

<a name="getNodelifecycleIswaitingforshutdown"></a>
# **getNodelifecycleIswaitingforshutdown**
> kotlin.Boolean getNodelifecycleIswaitingforshutdown()



### Example
```kotlin
// Import classes:
//import io.swagger.client.infrastructure.*
//import io.swagger.client.models.*;

val apiInstance = NodeLifecycleRPCOpsApi()
try {
    val result : kotlin.Boolean = apiInstance.getNodelifecycleIswaitingforshutdown()
    println(result)
} catch (e: ClientException) {
    println("4xx response calling NodeLifecycleRPCOpsApi#getNodelifecycleIswaitingforshutdown")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling NodeLifecycleRPCOpsApi#getNodelifecycleIswaitingforshutdown")
    e.printStackTrace()
}
```

### Parameters
This endpoint does not need any parameter.

### Return type

**kotlin.Boolean**

### Authorization

[basicAuth](../README.md#basicAuth)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="postNodelifecycleTerminate"></a>
# **postNodelifecycleTerminate**
> postNodelifecycleTerminate(body)



### Example
```kotlin
// Import classes:
//import io.swagger.client.infrastructure.*
//import io.swagger.client.models.*;

val apiInstance = NodeLifecycleRPCOpsApi()
val body : NodelifecycleTerminateBody =  // NodelifecycleTerminateBody | requestBody
try {
    apiInstance.postNodelifecycleTerminate(body)
} catch (e: ClientException) {
    println("4xx response calling NodeLifecycleRPCOpsApi#postNodelifecycleTerminate")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling NodeLifecycleRPCOpsApi#postNodelifecycleTerminate")
    e.printStackTrace()
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**NodelifecycleTerminateBody**](NodelifecycleTerminateBody.md)| requestBody | [optional]

### Return type

null (empty response body)

### Authorization

[basicAuth](../README.md#basicAuth)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: Not defined

