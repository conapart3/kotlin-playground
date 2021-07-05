# NodeStatusRPCOpsApi

All URIs are relative to */api/v1*

Method | HTTP request | Description
------------- | ------------- | -------------
[**getNodestatusGetnetworkreadinessstatus**](NodeStatusRPCOpsApi.md#getNodestatusGetnetworkreadinessstatus) | **GET** /nodestatus/getnetworkreadinessstatus | 
[**getNodestatusGetnodediagnosticinfo**](NodeStatusRPCOpsApi.md#getNodestatusGetnodediagnosticinfo) | **GET** /nodestatus/getnodediagnosticinfo | 
[**getNodestatusGetprotocolversion**](NodeStatusRPCOpsApi.md#getNodestatusGetprotocolversion) | **GET** /nodestatus/getprotocolversion | 

<a name="getNodestatusGetnetworkreadinessstatus"></a>
# **getNodestatusGetnetworkreadinessstatus**
> kotlin.Boolean getNodestatusGetnetworkreadinessstatus()



### Example
```kotlin
// Import classes:
//import io.swagger.client.infrastructure.*
//import io.swagger.client.models.*;

val apiInstance = NodeStatusRPCOpsApi()
try {
    val result : kotlin.Boolean = apiInstance.getNodestatusGetnetworkreadinessstatus()
    println(result)
} catch (e: ClientException) {
    println("4xx response calling NodeStatusRPCOpsApi#getNodestatusGetnetworkreadinessstatus")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling NodeStatusRPCOpsApi#getNodestatusGetnetworkreadinessstatus")
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

<a name="getNodestatusGetnodediagnosticinfo"></a>
# **getNodestatusGetnodediagnosticinfo**
> RpcNodeDiagnosticInfo getNodestatusGetnodediagnosticinfo()



### Example
```kotlin
// Import classes:
//import io.swagger.client.infrastructure.*
//import io.swagger.client.models.*;

val apiInstance = NodeStatusRPCOpsApi()
try {
    val result : RpcNodeDiagnosticInfo = apiInstance.getNodestatusGetnodediagnosticinfo()
    println(result)
} catch (e: ClientException) {
    println("4xx response calling NodeStatusRPCOpsApi#getNodestatusGetnodediagnosticinfo")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling NodeStatusRPCOpsApi#getNodestatusGetnodediagnosticinfo")
    e.printStackTrace()
}
```

### Parameters
This endpoint does not need any parameter.

### Return type

[**RpcNodeDiagnosticInfo**](RpcNodeDiagnosticInfo.md)

### Authorization

[basicAuth](../README.md#basicAuth)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getNodestatusGetprotocolversion"></a>
# **getNodestatusGetprotocolversion**
> kotlin.Int getNodestatusGetprotocolversion()



### Example
```kotlin
// Import classes:
//import io.swagger.client.infrastructure.*
//import io.swagger.client.models.*;

val apiInstance = NodeStatusRPCOpsApi()
try {
    val result : kotlin.Int = apiInstance.getNodestatusGetprotocolversion()
    println(result)
} catch (e: ClientException) {
    println("4xx response calling NodeStatusRPCOpsApi#getNodestatusGetprotocolversion")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling NodeStatusRPCOpsApi#getNodestatusGetprotocolversion")
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

