package persistence.permissions.model

import java.time.Instant
import java.util.UUID
import javax.persistence.EntityManager
import javax.persistence.Persistence
import persistence.jpqlqueryperformance.performanceTest

fun main() {
    testOutMergeDetatchedEntity()
}

fun testOutMergeDetatchedEntity() {
    val id = UUID.randomUUID().toString()
    val loginName = UUID.randomUUID().toString()
    withEntityManager {
        val u = User(id, Instant.now(), "conalsmith", loginName, true, null, null, null, null)
        it.persist(u)
    }

    val uRead = withEntityManager {
        it.createQuery("FROM persistence.permissions.model.User u where u.id = :id", User::class.java)
            .setParameter("id", id)
            .singleResult
    }

    withEntityManager {
        val u = it.createQuery("FROM persistence.permissions.model.User u where u.id = :id", User::class.java)
            .setParameter("id", id)
            .singleResult
        u.enabled = false
        it.merge(u)
    }

    withEntityManager {
        println("Removing user $uRead")
        it.remove(it.merge(uRead))
    }


    withEntityManager {
        val uReadAgain = it.createQuery("FROM persistence.permissions.model.User u where u.id = :id", User::class.java)
            .setParameter("id", id)
            .singleResult
        println(uReadAgain)
    }
}

fun findAllUsers(): List<String> {
    return withEntityManager {
        it.createQuery("SELECT u FROM User u", String::class.java)
            .resultList
    }!!
}

fun findAllUserIds(): List<String> {
    return withEntityManager {
        it.createQuery("SELECT u.id FROM User u", String::class.java)
            .resultList
    }!!
}

fun doIt() {

    val permissionId = UUID.randomUUID().toString()
    val roleId = UUID.randomUUID().toString()
    val roleId2 = UUID.randomUUID().toString()
    val groupId = UUID.randomUUID().toString()
    val parentGroupId = UUID.randomUUID().toString()
    val userId = UUID.randomUUID().toString()
    val userId2 = UUID.randomUUID().toString()
    val userPropId = UUID.randomUUID().toString()
    val groupPropId = UUID.randomUUID().toString()
    val roleName = "Role_" + UUID.randomUUID()
    val roleName2 = "Role_" + UUID.randomUUID()
    val parentGroupName = "ParentGroup_" + UUID.randomUUID()
    val groupName = "Group_" + UUID.randomUUID()
    val userLogin = "User_" + UUID.randomUUID()
    val userLogin2 = "User_" + UUID.randomUUID()

    val permission = createPermission(permissionId, ".*", PermissionType.ALLOW)
    val role = createRole(roleId, roleName)
    val role2 = createRole(roleId2, roleName2)
    val group = createGroup(groupId, groupName)
    val parentGroup = createGroup(parentGroupId, parentGroupName)
    val user = createUser(userId, "Conal", userLogin)
    val user2 = createUser(userId2, "Conal2", userLogin2)
    val groupProperty = GroupProperty(groupPropId, Instant.now(), group, "sampleKey", "sampleProp")

    withEntityManager { em ->
        em.persist(permission)
        em.persist(role)
        em.persist(role2)
        em.persist(group)
        em.persist(user)
        em.persist(user2)
        em.persist(parentGroup)
    }

    withEntityManager { em -> addRoleWithVersion(em, user.loginName, 0, roleId) }
    withEntityManager { em ->
        {
            val u = em.find(User::class.java, user.id)
            println(u)
            println(u.version)
        }
    }
    withEntityManager { em -> addRoleWithVersion(em, user.loginName, 0, roleId2) }
    withEntityManager { em ->
        {
            val u = em.find(User::class.java, user.id)
            println(u)
            println(u.version)
        }
    }

//        withEntityManager { em -> addRoleToUserByLoginName(em, user.loginName, roleId) }
//        println("Try adding same role to user again. Should fail.")
//        withEntityManager { em -> addRoleToUserByLoginName(em, user.loginName, roleId) }
//        println("Done adding role to user.")

//        withEntityManager { em -> addPermissionToRole(em, permissionId, roleId) }
//        withEntityManager { em -> doSomethingWithUserWithWrongVersion(em, userId, 0, "NAME_CHANGE_1") }
//        withEntityManager { em -> doSomethingWithUserWithWrongVersion(em, userId, 0, "NAME_CHANGE_2") }
//        withEntityManager { em -> addRoleToUser(em, userId, roleId) }
//        withEntityManager { em -> addRoleToGroup(em, groupId, roleId) }
//        withEntityManager { em -> setUserParentGroup(em, groupId, userId) }
//        withEntityManager { em -> setParentGroupOfGroup(em, groupId, parentGroupId) }
//        withEntityManager { em -> deleteGroup(em, parentGroupId) }
//        withEntityManager { em -> addPropertyToUser(em, userId, userPropId, "key", "value") }
//        withEntityManager { em -> addPropertyToGroup(em, groupId, groupProperty) }
//        withEntityManager { em -> deleteUser(em, userLogin) }
//        withEntityManager { em -> removeRoleFromGroup(em, groupName, roleName) }
//        withEntityManager { em -> removePermissionFromRole(em, roleName, "*") }

    println("\n======================== Reading final entities ========================")

//        withEntityManager { em ->
//            println("Finding group:")
//            val grpFinal = em.find(Group::class.java, groupId)
//            println("Conal printing final Group: $grpFinal")
//        }

//        withEntityManager { em ->
//            println("Finding user:")
//            val usrFinal = em.find(User::class.java, userId)
//            println("Conal printing final User: $usrFinal")
//        }

//        withEntityManager { em ->
//            println("Finding user:")
//            val usrFinal2 = em.find(User::class.java, userId2)
//            println("Conal printing final User2: $usrFinal2")
//        }

//        withEntityManager { em ->
//            println("Finding role:")
//            val rolFinal = em.find(Role::class.java, roleId)
//            println("Conal printing final Role: $rolFinal")
//        }

//        withEntityManager { em ->
//            println("Finding user property:")
//            val userProp = em.find(UserProperty::class.java, userPropId)
//            println("Conal printing UserProp: $userProp")
//        }

//        withEntityManager { em ->
//            println("Finding group property:")
//            val groupProp = em.find(GroupProperty::class.java, groupPropId)
//            println("Conal printing GroupProp: $groupProp")
//        }

//        seedRandomUsers(10000)
//        val ids = queryForUsersAndGetIds(1000)

    // 10 IDs.
//        queryWithInCount(10, ids)
//        queryWithInCount(100, ids)
//        queryWithInCount(1000, ids)
//        queryWithInCount(10000, ids)
//        queryWithInCount(100000, ids)

    println("======================== Finished! ========================")

//        createAvroObjects(user, group, role)

    // if you add a role to a user, does it update the version column?
}

