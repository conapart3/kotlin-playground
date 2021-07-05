# MembershipGroupRPCOpsApi

All URIs are relative to */api/v1*

Method | HTTP request | Description
------------- | ------------- | -------------
[**getMembershipgroupGetallmembers**](MembershipGroupRPCOpsApi.md#getMembershipgroupGetallmembers) | **GET** /membershipgroup/getallmembers | 
[**getMembershipgroupGetmember**](MembershipGroupRPCOpsApi.md#getMembershipgroupGetmember) | **GET** /membershipgroup/getmember | 
[**getMembershipgroupGetmembersfromname**](MembershipGroupRPCOpsApi.md#getMembershipgroupGetmembersfromname) | **GET** /membershipgroup/getmembersfromname | 
[**getMembershipgroupGetmygroupid**](MembershipGroupRPCOpsApi.md#getMembershipgroupGetmygroupid) | **GET** /membershipgroup/getmygroupid | 
[**getMembershipgroupGetmymemberinfo**](MembershipGroupRPCOpsApi.md#getMembershipgroupGetmymemberinfo) | **GET** /membershipgroup/getmymemberinfo | 
[**getMembershipgroupGetprotocolversion**](MembershipGroupRPCOpsApi.md#getMembershipgroupGetprotocolversion) | **GET** /membershipgroup/getprotocolversion | 

<a name="getMembershipgroupGetallmembers"></a>
# **getMembershipgroupGetallmembers**
> kotlin.Array&lt;RpcMemberInfo&gt; getMembershipgroupGetallmembers()



Returns all MemberInfo currently visible in the membership group.

### Example
```kotlin
// Import classes:
//import io.swagger.client.infrastructure.*
//import io.swagger.client.models.*;

val apiInstance = MembershipGroupRPCOpsApi()
try {
    val result : kotlin.Array<RpcMemberInfo> = apiInstance.getMembershipgroupGetallmembers()
    println(result)
} catch (e: ClientException) {
    println("4xx response calling MembershipGroupRPCOpsApi#getMembershipgroupGetallmembers")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling MembershipGroupRPCOpsApi#getMembershipgroupGetallmembers")
    e.printStackTrace()
}
```

### Parameters
This endpoint does not need any parameter.

### Return type

[**kotlin.Array&lt;RpcMemberInfo&gt;**](RpcMemberInfo.md)

### Authorization

[basicAuth](../README.md#basicAuth)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getMembershipgroupGetmember"></a>
# **getMembershipgroupGetmember**
> RpcMemberInfo getMembershipgroupGetmember(name)



Returns MemberInfo with given name.

### Example
```kotlin
// Import classes:
//import io.swagger.client.infrastructure.*
//import io.swagger.client.models.*;

val apiInstance = MembershipGroupRPCOpsApi()
val name : kotlin.String = name_example // kotlin.String | Identity of member.
try {
    val result : RpcMemberInfo = apiInstance.getMembershipgroupGetmember(name)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling MembershipGroupRPCOpsApi#getMembershipgroupGetmember")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling MembershipGroupRPCOpsApi#getMembershipgroupGetmember")
    e.printStackTrace()
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **name** | **kotlin.String**| Identity of member. |

### Return type

[**RpcMemberInfo**](RpcMemberInfo.md)

### Authorization

[basicAuth](../README.md#basicAuth)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getMembershipgroupGetmembersfromname"></a>
# **getMembershipgroupGetmembersfromname**
> kotlin.Array&lt;CordaX500Name&gt; getMembershipgroupGetmembersfromname(query, exactmatch)



Retrieves set of members given matching criteria.

### Example
```kotlin
// Import classes:
//import io.swagger.client.infrastructure.*
//import io.swagger.client.models.*;

val apiInstance = MembershipGroupRPCOpsApi()
val query : kotlin.String = query_example // kotlin.String | Query string to perform match upon.
val exactmatch : kotlin.Boolean = true // kotlin.Boolean | Whether to do exact match or allow fuzzy matches.
try {
    val result : kotlin.Array<CordaX500Name> = apiInstance.getMembershipgroupGetmembersfromname(query, exactmatch)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling MembershipGroupRPCOpsApi#getMembershipgroupGetmembersfromname")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling MembershipGroupRPCOpsApi#getMembershipgroupGetmembersfromname")
    e.printStackTrace()
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **query** | **kotlin.String**| Query string to perform match upon. |
 **exactmatch** | **kotlin.Boolean**| Whether to do exact match or allow fuzzy matches. | [optional]

### Return type

[**kotlin.Array&lt;CordaX500Name&gt;**](CordaX500Name.md)

### Authorization

[basicAuth](../README.md#basicAuth)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getMembershipgroupGetmygroupid"></a>
# **getMembershipgroupGetmygroupid**
> kotlin.String getMembershipgroupGetmygroupid()



Returns the groupID of the membership.

### Example
```kotlin
// Import classes:
//import io.swagger.client.infrastructure.*
//import io.swagger.client.models.*;

val apiInstance = MembershipGroupRPCOpsApi()
try {
    val result : kotlin.String = apiInstance.getMembershipgroupGetmygroupid()
    println(result)
} catch (e: ClientException) {
    println("4xx response calling MembershipGroupRPCOpsApi#getMembershipgroupGetmygroupid")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling MembershipGroupRPCOpsApi#getMembershipgroupGetmygroupid")
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

<a name="getMembershipgroupGetmymemberinfo"></a>
# **getMembershipgroupGetmymemberinfo**
> RpcMemberInfo getMembershipgroupGetmymemberinfo()



Returns Node&#x27;s MemberInfo.

### Example
```kotlin
// Import classes:
//import io.swagger.client.infrastructure.*
//import io.swagger.client.models.*;

val apiInstance = MembershipGroupRPCOpsApi()
try {
    val result : RpcMemberInfo = apiInstance.getMembershipgroupGetmymemberinfo()
    println(result)
} catch (e: ClientException) {
    println("4xx response calling MembershipGroupRPCOpsApi#getMembershipgroupGetmymemberinfo")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling MembershipGroupRPCOpsApi#getMembershipgroupGetmymemberinfo")
    e.printStackTrace()
}
```

### Parameters
This endpoint does not need any parameter.

### Return type

[**RpcMemberInfo**](RpcMemberInfo.md)

### Authorization

[basicAuth](../README.md#basicAuth)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getMembershipgroupGetprotocolversion"></a>
# **getMembershipgroupGetprotocolversion**
> kotlin.Int getMembershipgroupGetprotocolversion()



### Example
```kotlin
// Import classes:
//import io.swagger.client.infrastructure.*
//import io.swagger.client.models.*;

val apiInstance = MembershipGroupRPCOpsApi()
try {
    val result : kotlin.Int = apiInstance.getMembershipgroupGetprotocolversion()
    println(result)
} catch (e: ClientException) {
    println("4xx response calling MembershipGroupRPCOpsApi#getMembershipgroupGetprotocolversion")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling MembershipGroupRPCOpsApi#getMembershipgroupGetprotocolversion")
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

