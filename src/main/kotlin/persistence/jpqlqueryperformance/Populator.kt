package persistence.jpqlqueryperformance

import java.util.UUID
import javax.persistence.EntityManager
import persistence.permissions.model.Group
import persistence.permissions.model.PermissionType
import persistence.permissions.model.User
import persistence.permissions.model.addGroupParentGroup
import persistence.permissions.model.addPermissionToRole
import persistence.permissions.model.addRoleToGroup
import persistence.permissions.model.addRoleToUserByLoginName
import persistence.permissions.model.addUserParentGroup
import persistence.permissions.model.createGroup
import persistence.permissions.model.createPermission
import persistence.permissions.model.createRole
import persistence.permissions.model.createUser
import persistence.permissions.model.withEntityManager

fun main() {
//    val groupId = populateGroupChain()
//    val groupId2 = populateGroupChain()
//    val userLoginsGroup1 = addUsersWithRolesIntoGroup(groupId, 100)
//    val userLoginsGroup2 = addUsersWithRolesIntoGroup(groupId2, 100)
//    println(userLoginsGroup1)
//    println(userLoginsGroup2)
//    populateUserWithoutPermissions()
    populateUsersWithRoles(1000)
//    val ids = populateUsersWithRolesWithDuplicatePermissions(1000)
//    val ids = populateUserToTestSorting(1000)
//    println(ids)
}

fun populateUserWithoutPermissions() {
    val user = createUser(UUID.randomUUID().toString(), UUID.randomUUID().toString(), "no-permissions")
    withEntityManager {
        it.persist(user)
    }
}

fun populateGroupChain(): String {
    val seedGroupId = createLongChainGroup(
        "J_LONG",
        createLongChainGroup(
            "I_LONG",
            createLongChainGroup(
                "H_LONG",
                createLongChainGroup(
                    "G_LONG",
                    createLongChainGroup(
                        "F_LONG",
                        createLongChainGroup(
                            "E_LONG",
                            createLongChainGroup(
                                "D_LONG",
                                createLongChainGroup(
                                    "C_LONG",
                                    createLongChainGroup(
                                        "B_LONG",
                                        createLongChainGroup("A_LONG", null)
                                    )
                                )
                            )
                        )
                    )
                )
            )
        )
    )
    val user = createUser(UUID.randomUUID().toString(), UUID.randomUUID().toString(), UUID.randomUUID().toString())
    withEntityManager {
        it.persist(user)
    }
    withEntityManager {
        addUserParentGroup(it, user.id, seedGroupId)
    }
    return seedGroupId
}

fun createLongChainGroup(permission: String, parentGroupId: String?): String {
    val group = createGroup(UUID.randomUUID().toString(), UUID.randomUUID().toString())
    val permission1 = createPermission(UUID.randomUUID().toString(), permission, PermissionType.ALLOW)
    val role1 = createRole(UUID.randomUUID().toString(), UUID.randomUUID().toString())

    withEntityManager { em ->
        em.persist(group)
        em.persist(permission1)
        em.persist(role1)
    }

    withEntityManager {
        addPermissionToRole(it, permission1.id, role1.id)
        addRoleToGroup(it, group.id, role1.id)
        if (parentGroupId != null) {
            addGroupParentGroup(it, group.id, parentGroupId)
        }
    }
    return group.id
}

fun populateUserWithGroup(num: Int = 1) {
    for (i in 1..num) {
        val permission1 = createPermission(UUID.randomUUID().toString(), "A", PermissionType.ALLOW)
        val permission2 = createPermission(UUID.randomUUID().toString(), "B", PermissionType.DENY)
        val permission3 = createPermission(UUID.randomUUID().toString(), "C", PermissionType.ALLOW)
        val permission4 = createPermission(UUID.randomUUID().toString(), "D", PermissionType.ALLOW)
        val permission5 = createPermission(UUID.randomUUID().toString(), "E", PermissionType.DENY)
        val permission6 = createPermission(UUID.randomUUID().toString(), "F", PermissionType.ALLOW)
        val role1 = createRole(UUID.randomUUID().toString(), UUID.randomUUID().toString())
        val role2 = createRole(UUID.randomUUID().toString(), UUID.randomUUID().toString())
        val role3 = createRole(UUID.randomUUID().toString(), UUID.randomUUID().toString())
        val group = createGroup(UUID.randomUUID().toString(), UUID.randomUUID().toString())
        val parentGroup = createGroup(UUID.randomUUID().toString(), UUID.randomUUID().toString())
        val user = createUser(UUID.randomUUID().toString(), UUID.randomUUID().toString(), UUID.randomUUID().toString())

        withEntityManager { em ->
            em.persist(permission1)
            em.persist(permission2)
            em.persist(permission3)
            em.persist(permission4)
            em.persist(permission5)
            em.persist(permission6)
            em.persist(role1)
            em.persist(role2)
            em.persist(role3)
            em.persist(group)
            em.persist(parentGroup)
            em.persist(user)
        }

        withEntityManager {
            addPermissionToRole(it, permission1.id, role1.id)
            addPermissionToRole(it, permission4.id, role1.id)
            addPermissionToRole(it, permission2.id, role2.id)
            addPermissionToRole(it, permission5.id, role2.id)
            addPermissionToRole(it, permission3.id, role3.id)
            addPermissionToRole(it, permission6.id, role3.id)
            addRoleToUserByLoginName(it, user.loginName, role1.id)
            addRoleToGroup(it, group.id, role2.id)
            addRoleToGroup(it, parentGroup.id, role3.id)
            addUserParentGroup(it, user.id, group.id)
            addGroupParentGroup(it, group.id, parentGroup.id)
        }
    }
}

fun populateUsersWithRoles(num: Int = 1): List<String> {
    val userLogins = (1..num).map {
        val permission1 = createPermission(UUID.randomUUID().toString(), "A", PermissionType.ALLOW)
        val permission2 = createPermission(UUID.randomUUID().toString(), "B", PermissionType.DENY)
        val permission3 = createPermission(UUID.randomUUID().toString(), "C", PermissionType.ALLOW)
        val permission4 = createPermission(UUID.randomUUID().toString(), "D", PermissionType.ALLOW)
        val permission5 = createPermission(UUID.randomUUID().toString(), "E", PermissionType.DENY)
        val permission6 = createPermission(UUID.randomUUID().toString(), "F", PermissionType.ALLOW)
        val role1 = createRole(UUID.randomUUID().toString(), UUID.randomUUID().toString())
        val role2 = createRole(UUID.randomUUID().toString(), UUID.randomUUID().toString())
        val role3 = createRole(UUID.randomUUID().toString(), UUID.randomUUID().toString())
        val user = createUser(UUID.randomUUID().toString(), UUID.randomUUID().toString(), UUID.randomUUID().toString())

        withEntityManager { em ->
            em.persist(permission1)
            em.persist(permission2)
            em.persist(permission3)
            em.persist(permission4)
            em.persist(permission5)
            em.persist(permission6)
            em.persist(role1)
            em.persist(role2)
            em.persist(role3)
            em.persist(user)
        }

        withEntityManager {
            addPermissionToRole(it, permission1.id, role1.id)
            addPermissionToRole(it, permission4.id, role1.id)
            addPermissionToRole(it, permission2.id, role2.id)
            addPermissionToRole(it, permission5.id, role2.id)
            addPermissionToRole(it, permission3.id, role3.id)
            addPermissionToRole(it, permission6.id, role3.id)
            addRoleToUserByLoginName(it, user.loginName, role1.id)
        }
        user.loginName
    }
    return userLogins
}

fun populateUserToTestSorting(num: Int = 1): List<String> {
    val userLogins = (1..num).map {
        val group = createGroup(UUID.randomUUID().toString(), UUID.randomUUID().toString())
        val permission1 = createPermission(UUID.randomUUID().toString(), "A", PermissionType.ALLOW)
        val permission2 = createPermission(UUID.randomUUID().toString(), "A", PermissionType.DENY)
        val permission3 = createPermission(UUID.randomUUID().toString(), "F", PermissionType.ALLOW)
        val permission4 = createPermission(UUID.randomUUID().toString(), "D", PermissionType.ALLOW)
        val permission5 = createPermission(UUID.randomUUID().toString(), "C", PermissionType.ALLOW, null, null)
        val permission6 = createPermission(UUID.randomUUID().toString(), "C", PermissionType.ALLOW, "bbbvnode", null)
        val permission7 = createPermission(UUID.randomUUID().toString(), "C", PermissionType.ALLOW, "aaavnode", null)
        val permission8 = createPermission(UUID.randomUUID().toString(), "D", PermissionType.ALLOW, null, null)
        val permission9 = createPermission(UUID.randomUUID().toString(), "D", PermissionType.ALLOW, null, group)
        val role1 = createRole(UUID.randomUUID().toString(), UUID.randomUUID().toString())
        val user = createUser(UUID.randomUUID().toString(), UUID.randomUUID().toString(), UUID.randomUUID().toString())

        withEntityManager { em ->
            em.persist(group)
            em.persist(permission1)
            em.persist(permission2)
            em.persist(permission3)
            em.persist(permission4)
            em.persist(permission5)
            em.persist(permission6)
            em.persist(permission7)
            em.persist(permission8)
            em.persist(permission9)
            em.persist(role1)
            em.persist(user)
            addPermissionToRole(em, permission1.id, role1.id)
            addPermissionToRole(em, permission2.id, role1.id)
            addPermissionToRole(em, permission3.id, role1.id)
            addPermissionToRole(em, permission4.id, role1.id)
            addPermissionToRole(em, permission5.id, role1.id)
            addPermissionToRole(em, permission6.id, role1.id)
            addPermissionToRole(em, permission7.id, role1.id)
            addPermissionToRole(em, permission8.id, role1.id)
            addPermissionToRole(em, permission9.id, role1.id)
            addRoleToUserByLoginName(em, user.loginName, role1.id)
        }

        user.loginName
    }
    return userLogins
}

fun populateUsersWithRolesWithDuplicatePermissions(num: Int = 1): List<String> {
    val permission1 = createPermission(UUID.randomUUID().toString(), "A", PermissionType.ALLOW)
    val permission2 = createPermission(UUID.randomUUID().toString(), "A", PermissionType.ALLOW)
    val permission3 = createPermission(UUID.randomUUID().toString(), "C", PermissionType.ALLOW)
    val permission4 = createPermission(UUID.randomUUID().toString(), "D", PermissionType.ALLOW)
    val permission5 = createPermission(UUID.randomUUID().toString(), "E", PermissionType.DENY)
    val permission6 = createPermission(UUID.randomUUID().toString(), "F", PermissionType.ALLOW)
    val role1 = createRole(UUID.randomUUID().toString(), UUID.randomUUID().toString())
    val role2 = createRole(UUID.randomUUID().toString(), UUID.randomUUID().toString())
    val role3 = createRole(UUID.randomUUID().toString(), UUID.randomUUID().toString())

    withEntityManager { em ->
        em.persist(permission1)
        em.persist(permission2)
        em.persist(permission3)
        em.persist(permission4)
        em.persist(permission5)
        em.persist(permission6)
        em.persist(role1)
        em.persist(role2)
        em.persist(role3)
        addPermissionToRole(em, permission1.id, role1.id)
        addPermissionToRole(em, permission2.id, role1.id)
        addPermissionToRole(em, permission1.id, role2.id)
        addPermissionToRole(em, permission2.id, role2.id)
        addPermissionToRole(em, permission1.id, role3.id)
        addPermissionToRole(em, permission3.id, role3.id)
    }

    val userLogins = (1..num).map {
        val user = createUser(UUID.randomUUID().toString(), UUID.randomUUID().toString(), UUID.randomUUID().toString())

        withEntityManager { em ->
            em.persist(user)
        }

        withEntityManager {
            addRoleToUserByLoginName(it, user.loginName, role1.id)
            addRoleToUserByLoginName(it, user.loginName, role2.id)
            addRoleToUserByLoginName(it, user.loginName, role3.id)
        }
        user.loginName
    }
    return userLogins
}

fun addUsersWithRolesIntoGroup(groupId: String, num: Int = 1): List<String> {
    val userLogins = (1..num).map {
        val permission1 = createPermission(UUID.randomUUID().toString(), "A", PermissionType.ALLOW)
        val permission2 = createPermission(UUID.randomUUID().toString(), "B", PermissionType.DENY)
        val permission3 = createPermission(UUID.randomUUID().toString(), "C", PermissionType.ALLOW)
        val permission4 = createPermission(UUID.randomUUID().toString(), "D", PermissionType.ALLOW)
        val permission5 = createPermission(UUID.randomUUID().toString(), "E", PermissionType.DENY)
        val permission6 = createPermission(UUID.randomUUID().toString(), "F", PermissionType.ALLOW)
        val role1 = createRole(UUID.randomUUID().toString(), UUID.randomUUID().toString())
        val role2 = createRole(UUID.randomUUID().toString(), UUID.randomUUID().toString())
        val role3 = createRole(UUID.randomUUID().toString(), UUID.randomUUID().toString())
        val user = createUser(UUID.randomUUID().toString(), UUID.randomUUID().toString(), UUID.randomUUID().toString())

        withEntityManager { em ->
            em.persist(permission1)
            em.persist(permission2)
            em.persist(permission3)
            em.persist(permission4)
            em.persist(permission5)
            em.persist(permission6)
            em.persist(role1)
            em.persist(role2)
            em.persist(role3)
            em.persist(user)
        }

        withEntityManager {
            addPermissionToRole(it, permission1.id, role1.id)
            addPermissionToRole(it, permission4.id, role1.id)
            addPermissionToRole(it, permission2.id, role2.id)
            addPermissionToRole(it, permission5.id, role2.id)
            addPermissionToRole(it, permission3.id, role3.id)
            addPermissionToRole(it, permission6.id, role3.id)
            addRoleToUserByLoginName(it, user.loginName, role1.id)
            addUserParentGroup(it, user.id, groupId)
        }
        user.loginName
    }
    return userLogins
}

fun populateUserWithoutGroupOrRole(num: Int = 1) {
    for (i in 1..num) {
        val user = createUser(UUID.randomUUID().toString(), UUID.randomUUID().toString(), UUID.randomUUID().toString())

        withEntityManager { em ->
            em.persist(user)
        }
    }
}

