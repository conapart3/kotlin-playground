package persistence.jpqlqueryperformance

import persistence.permissions.model.PermissionType
import persistence.permissions.model.User
import persistence.permissions.model.withEntityManager

fun main() {

//    val query = """
//        SELECT u FROM User u
//        JOIN FETCH u.userProperties
//        JOIN FETCH u.roleUserAssociations
//    """
    val query = "SELECT u FROM User u"

    withPerformanceTest("a") {
        withEntityManager {
            it.createQuery(query, User::class.java)
                .resultList
        }!!
    }

//    withPerformanceTest("role query") {
//        val roleQuery = """
//                SELECT DISTINCT NEW persistence.jpqlqueryperformance.InternalPermissionQueryDto(
//                    u.loginName,
//                    p.groupVisibility.id,
//                    p.virtualNode,
//                    p.permissionString,
//                    p.permissionType
//                )
//                FROM User u
//                    JOIN RoleUserAssociation rua ON rua.user.id = u.id
//                    JOIN Role r ON rua.role.id = r.id
//                    JOIN RolePermissionAssociation rpa ON rpa.role.id = r.id
//                    JOIN Permission p ON rpa.permission.id = p.id
//            """.trimIndent()
//
//        withEntityManager { em ->
//            em.createQuery(roleQuery, InternalPermissionQueryDto::class.java)
//                .resultList
//                .sortedWith(PermissionQueryDtoComparator())
//                .groupBy { it.loginName }
//        }
//
//        1
//    }
}

class PermissionQueryDtoComparator : Comparator<InternalPermissionQueryDto> {
    override fun compare(o1: InternalPermissionQueryDto, o2: InternalPermissionQueryDto): Int {
        return when {
            o1.permissionType != o2.permissionType -> {
                // DENY before ALLOW
                if (o1.permissionType == PermissionType.DENY) {
                    -1
                } else {
                    1
                }
            }
            o1.permissionString != o2.permissionString -> {
                // then permission string alphabetical order
                o1.permissionString.compareTo(o2.permissionString)
            }
            o1.virtualNode != o2.virtualNode -> {
                // then virtual node null first
                if (o1.virtualNode == null) {
                    -1
                } else if (o2.virtualNode == null) {
                    1
                } else {
                    // then virtualNode alphabetical order
                    o1.virtualNode!!.compareTo(o2.virtualNode!!)
                }
            }
            o1.groupVisibility != o2.groupVisibility -> {
                // then groupVisibility null first
                if (o1.groupVisibility == null) {
                    -1
                } else if (o2.groupVisibility == null) {
                    1
                } else {
                    // then groupVisibility alphabetical order
                    o1.groupVisibility!!.compareTo(o2.groupVisibility!!)
                }
            }
            else -> 0
        }
    }
}

/*


fun combine(): MutableMap<String, InternalUserPermissionSummary> {
    return withEntityManagerAndFactory { em, emf ->
        // these joins are INNER by default, so users without roles will not appear in result set.
        // O(n)
        val roleQuery = """
                SELECT NEW persistence.jpqlqueryperformance.InternalUserRolePermissionDto(
                    u.loginName,
                    r.id,
                    r.groupVisibility.id, 
                    p.virtualNode, 
                    p.permissionString, 
                    p.permissionType
                )
                FROM User u
                    JOIN RoleUserAssociation rua ON rua.user.id = u.id
                    JOIN Role r ON rua.role.id = r.id
                    JOIN RolePermissionAssociation rpa ON rpa.role.id = r.id
                    JOIN Permission p ON rpa.permission.id = p.id
            """.trimIndent()

        // O(n)
        val parentGroupIdsForUser = """
            SELECT NEW persistence.jpqlqueryperformance.UserParentGroupDto(u.loginName, u.parentGroup.id) 
            FROM User u
        """.trimIndent()

        // O(n)
        val parentGroupIdsForGroups = """
            SELECT NEW persistence.jpqlqueryperformance.GroupParentGroupDto(g.id, g.parentGroup.id) 
            FROM Group g
        """.trimIndent()

        // these joins are INNER joins, so groups without roles associated will not appear in result set.
        // O(n)
        val groupQuery = """
                SELECT NEW persistence.jpqlqueryperformance.InternalGroupRolePermissionDto(
                    g.id, 
                    r.id, 
                    p.groupVisibility.id,
                    p.virtualNode, 
                    p.permissionString, 
                    p.permissionType
                )
                FROM Group g
                    JOIN RoleGroupAssociation rga ON rga.group.id = g.id
                    JOIN Role r ON rga.role.id = r.id
                    JOIN RolePermissionAssociation rpa ON rpa.role.id = r.id
                    JOIN Permission p ON rpa.permission.id = p.id
            """.trimIndent()

        // O(n)
        val userPermissionSummaries: Map<String, List<InternalUserRolePermissionDto>> = withPerformanceTest("User role query") {
            em.createQuery(roleQuery, InternalUserRolePermissionDto::class.java)
                .resultList
                .groupBy { it.loginName }
        }

        // O(n)
        val groupPermissionSummaries: Map<String, List<InternalGroupRolePermissionDto>> = withPerformanceTest("Group role query") {
            em.createQuery(groupQuery, InternalGroupRolePermissionDto::class.java)
                .resultList
                .groupBy { it.groupId }
        }

        // O(n)
        val userToParentGroupMappings = withPerformanceTest("User to parent group mappings query") {
            em.createQuery(parentGroupIdsForUser, UserParentGroupDto::class.java)
                .resultList
                .associate { it.loginName to it.parentGroupId }
        }

        // O(n)
        val groupToParentGroupMappings = withPerformanceTest("group to parent group mappings query") {
            em.createQuery(parentGroupIdsForGroups, GroupParentGroupDto::class.java)
                .resultList
                .associate { it.groupId to it.parentGroupId }
        }

        // O(n)
        val completePermissionSummaryMap: MutableMap<String, InternalUserPermissionSummary> = mutableMapOf()
        withPerformanceTest("Combining role permissions with user permissions") {
            userToParentGroupMappings.forEach { (loginName, parentGroupId) ->
                val summaryForUser: MutableList<InternalPermissionSummaryDto> = mutableListOf()

                // O(1)
                userPermissionSummaries[loginName]?.let { summaryForUser.addAll(it) }

                // O(1)
                summaryForUser.addAll(
                    getRolesFromGroups(parentGroupId, groupToParentGroupMappings, groupPermissionSummaries)
                )

                completePermissionSummaryMap[loginName] = InternalUserPermissionSummary(loginName, summaryForUser, Instant.now())
            }
        }

        println(
            "Created permission summaries for ${completePermissionSummaryMap.size} users. Data set includes " +
                    "${groupPermissionSummaries.size} groups."
        )
        completePermissionSummaryMap
    }!!
}

fun getRolesFromGroups(
    groupId: String?,
    groupToParentGroupMappings: Map<String, String?>,
    groupPermissionSummaries: Map<String, List<InternalGroupRolePermissionDto>>
): Collection<InternalPermissionSummaryDto> {
    var currentGroup = groupId
    val totalRoles = mutableListOf<InternalPermissionSummaryDto>()
    while (currentGroup != null) {
        groupPermissionSummaries[currentGroup]?.let { totalRoles.addAll(it) }
        currentGroup = groupToParentGroupMappings[currentGroup]
    }
    return totalRoles
}

*/
/*
tailrec fun recursivelyGetParentGroupRoles(
    groupId: String?,
    groupToParentGroupMapping: Map<String, String?>,
    groupPermissionSummaries: Map<String, List<InternalGroupRolePermission>>
): List<InternalPermissionSummary> {

    if(groupId == null) {
        return emptyList()
    }
    val permissions = groupPermissionSummaries[groupId]
    val parentGroupId = groupToParentGroupMapping[groupId] ?: return permissions!!

    return recursivelyGetParentGroupRoles(parentGroupId, groupToParentGroupMapping, groupPermissionSummaries)
}*//*


*/
/*fun loadAllEntitiesFirst(): List<InternalPermissionSummary> {
    return withEntityManager {
        val users = it.createQuery("SELECT u from User u", User::class.java)
            .resultList
        val roles = it.createQuery("SELECT r from Role r", Role::class.java)
            .resultList
        val groups = it.createQuery("SELECT g from Group g", Group::class.java)
            .resultList
        val permissions = it.createQuery("SELECT p from Permission p", Permission::class.java)
            .resultList
        val ruas = it.createQuery("SELECT rua from RoleUserAssociation rua", RoleUserAssociation::class.java)
            .resultList
        val rgas = it.createQuery("SELECT rga from RoleGroupAssociation rga", RoleGroupAssociation::class.java)
            .resultList
        val rpas = it.createQuery("SELECT rpa from RolePermissionAssociation rpa", RolePermissionAssociation::class.java)
            .resultList

        users.flatMap { user ->
            user.roleUserAssociations.flatMap { rua ->
                rua.role.rolePermAssociations.map { rpa ->
                    val permission = rpa.permission
                    InternalPermissionSummary(
                        permission.groupVisibility?.id,
                        permission.virtualNode,
                        permission.permissionString,
                        permission.permissionType
                    )
                }
            }
        }
    }!!
}*//*


*/
/*fun loadUserRolesWithJoin(): List<InternalUserRolePermission> {
    val rolePermissions = withEntityManager {
        val query = """
                SELECT NEW persistence.jpqlqueryperformance.InternalUserRolePermission(
                    u.loginName,
                    r.id,
                    r.groupVisibility.id, 
                    p.virtualNode, 
                    p.permissionString, 
                    p.permissionType
                )
                FROM User u
                    JOIN RoleUserAssociation rua ON rua.user.id = u.id
                    JOIN Role r ON rua.role.id = r.id
                    JOIN RolePermissionAssociation rpa ON rpa.role.id = r.id
                    JOIN Permission p ON rpa.permission.id = p.id
            """.trimIndent()
        it.createQuery(query, InternalUserRolePermission::class.java)
            .resultList
    }
    return rolePermissions!!
}

fun loadGroupRolesWithJoin(): List<InternalGroupRolePermission> {
    val groupPermissions = withEntityManager {
        val query = """
                SELECT NEW persistence.jpqlqueryperformance.InternalGroupRolePermission(
                    g.id, 
                    r.id, 
                    p.virtualNode, 
                    p.permissionString, 
                    p.permissionType
                )
                FROM Group g
                    INNER JOIN RoleGroupAssociation rga ON rga.group.id = g.id
                    INNER JOIN Role r ON rga.role.id = r.id
                    INNER JOIN RolePermissionAssociation rpa ON rpa.role.id = r.id
                    INNER JOIN Permission p ON rpa.permission.id = p.id
            """.trimIndent()
        it.createQuery(query, InternalGroupRolePermission::class.java)
            .resultList
    }
    return groupPermissions!!
}

fun queryForRoleSummariesForAllUsers(): List<InternalUserRolePermission> {
    val rolePermissions = withEntityManager {
        val query = """
                SELECT NEW persistence.jpqlqueryperformance.InternalUserRolePermission(
                    u.loginName, 
                    r.id, 
                    r.groupVisibility.id, 
                    p.virtualNode, 
                    p.permissionString, 
                    p.permissionType
                )
                FROM User u
                    INNER JOIN RoleUserAssociation rua ON rua.user.id = u.id
                    INNER JOIN Role r ON rua.role.id = r.id
                    INNER JOIN RolePermissionAssociation rpa ON rpa.role.id = r.id
                    INNER JOIN Permission p ON rpa.permission.id = p.id
            """.trimIndent()
        it.createQuery(query, InternalUserRolePermission::class.java)
            .resultList
    }
    return rolePermissions!!
}

fun queryForGroupSummariesForAllGroups(): List<InternalGroupRolePermission> {
    val groupPermissions = withEntityManager {
        val query = """
                SELECT NEW persistence.jpqlqueryperformance.InternalGroupRolePermission(
                    g.id, 
                    r.id, 
                    p.virtualNode, 
                    p.permissionString, 
                    p.permissionType
                )
                FROM Group g
                    INNER JOIN RoleGroupAssociation rga ON rga.group.id = g.id
                    INNER JOIN Role r ON rga.role.id = r.id
                    INNER JOIN RolePermissionAssociation rpa ON rpa.role.id = r.id
                    INNER JOIN Permission p ON rpa.permission.id = p.id
            """.trimIndent()
        it.createQuery(query, InternalGroupRolePermission::class.java)
            .resultList
    }
    return groupPermissions!!
}

// loading the associations resulted in N+1 problem - loading the associated roles and users 1 by 1.
fun loadRoleUserAssociations(): List<RoleUserAssociation> {
    return withEntityManager {
        it.createQuery("SELECT rua from RoleUserAssociation rua", RoleUserAssociation::class.java)
            .resultList
    }!!
}

fun loadRoleGroupAssociations(): List<RoleGroupAssociation> {
    return withEntityManager {
        it.createQuery("SELECT rga from RoleGroupAssociation rga", RoleGroupAssociation::class.java)
            .resultList
    }!!
}

fun loadRolePermissionAssociations(): List<RolePermissionAssociation> {
    return withEntityManager {
        it.createQuery("SELECT rpa from RolePermissionAssociation rpa", RolePermissionAssociation::class.java)
            .resultList
    }!!
}*/

