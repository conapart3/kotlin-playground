package persistence.jpqlqueryperformance

import java.time.Instant
import persistence.permissions.model.PermissionType

open class InternalPermissionSummaryDto(
    val groupVisibility: String?,
    val virtualNode: String?,
    val permissionString: String,
    val permissionType: PermissionType
) {
    override fun toString(): String {
        return "InternalPermissionSummaryDto(permissionString='$permissionString', permissionType=$permissionType)"
    }
}

data class InternalUserPermissionSummary(
    val loginName: String,
    val permissionSummaries: List<InternalPermissionSummaryDto>,
    val lastUpdateTimestamp: Instant
)


data class UserParentGroupDto(
    val loginName: String,
    val parentGroupId: String?
)

data class GroupParentGroupDto(
    val groupId: String,
    val parentGroupId: String?
)

data class InternalPermissionQueryDto(
    val loginName: String,
    val groupVisibility: String?,
    val virtualNode: String?,
    val permissionString: String,
    val permissionType: PermissionType
)

class InternalGroupRolePermissionDto(
    val groupId: String,
    val roleId: String,
    groupVisibility: String?,
    virtualNode: String?,
    permissionString: String,
    permissionType: PermissionType
) : InternalPermissionSummaryDto(groupVisibility, virtualNode, permissionString, permissionType) {
    override fun toString(): String {
        return "InternalGroupRolePermissionDto(groupId='$groupId', roleId='$roleId', ${this.permissionString}))"
    }
}