package persistence.permissions.model

import java.time.Instant
import java.util.UUID
import javax.persistence.EntityManager
import javax.persistence.EntityManagerFactory
import javax.persistence.Persistence

fun addRoleToUserByLoginName(em: EntityManager, userLogin: String, roleId: String) {
    println("\n======================== Adding role $roleId to user with name $userLogin ========================")
//        val queryString = "SELECT u FROM User u LEFT JOIN FETCH u.roleUserAssociations WHERE u.loginName = :loginName"
    val queryString = "FROM User WHERE loginName = :loginName"
    val users = em
        .createQuery(queryString, User::class.java)
        .setParameter("loginName", userLogin)
        .resultList

    require(users.size == 1) {
        "More than 1 user with loginName $userLogin"
    }
    val user = users.single()

    println("Accessing roleUserAssocations:")
    println(user.roleUserAssociations)

    println("User $userLogin has ${user.roleUserAssociations.size} roles associated: ${user.roleUserAssociations}")

    println("Finding role by id $roleId")
    val role = em.find(Role::class.java, roleId)

    require(user.roleUserAssociations.none { it.role == role }) {
        "Role $roleId already associated."
    }

    val assoc = RoleUserAssociation(UUID.randomUUID().toString(), role, user, Instant.now())

    user.roleUserAssociations.add(assoc)
    println("Merging user with added roleUserAssoc:")
    em.merge(user)
}

fun addRoleWithVersion(em: EntityManager, userLogin: String, version: Int, roleId: String) {
    println("\n======================== Adding role $roleId to user with name $userLogin and version $version ========================")
    val queryString = "FROM User WHERE loginName = :loginName"
    val users = em
        .createQuery(queryString, User::class.java)
        .setParameter("loginName", userLogin)
        .resultList

    require(users.size == 1) {
        "More than 1 user with loginName $userLogin"
    }
    val user = users.single()

    println("Accessing roleUserAssocations:")
    println(user.roleUserAssociations)

    println("User $userLogin has ${user.roleUserAssociations.size} roles associated: ${user.roleUserAssociations}")

    println("Finding role by id $roleId")
    val role = em.find(Role::class.java, roleId)

    require(user.roleUserAssociations.none { it.role == role }) {
        "Role $roleId already associated."
    }

    val assoc = RoleUserAssociation(UUID.randomUUID().toString(), role, user, Instant.now())

    user.roleUserAssociations.add(assoc)
    println("Merging user with added roleUserAssoc:")
    em.merge(user)
}

fun queryForUsersAndGetIds(maxCount: Int): List<String> {
    val ids: List<String> = withEntityManager { em ->
        val query = em.createQuery("from persistence.permissions.model.User", User::class.java)
        query.maxResults = maxCount
        val ids = query.resultList.map { it.id }
        ids
    } as List<String>
    return ids
}

fun queryWithInCount(count: Int, ids: List<String>?) {
    val longListOfIds1 = ids ?: (1..count).map { UUID.randomUUID().toString() }
    withEntityManager { em ->
        println("Long IN expression with $count IDs:")
        val start = System.currentTimeMillis()
        val query = em.createQuery("select u from persistence.permissions.model.User u where u.id IN :ids")
        query.setParameter("ids", longListOfIds1)
        val result = query.resultList
        val end = System.currentTimeMillis()
        val total = end - start
        println("Took $total millis. Got ${result.count()} results.")
    }
}

fun seedRandomUsers(count: Int) {
    val users = (1..count).map {
        User(
            id = UUID.randomUUID().toString(),
            updateTimestamp = Instant.now(),
            fullName = UUID.randomUUID().toString(),
            loginName = UUID.randomUUID().toString(),
            enabled = true,
            saltValue = null,
            hashedPassword = null,
            passwordExpiry = null,
            parentGroup = null
        )
    }
    withEntityManager { em ->
        users.forEach { em.persist(it) }
    }
}

// testing optimistic lock exception when we manually set version on the entity
fun doSomethingWithUserWithWrongVersion(em: EntityManager, userId: String, requestVersion: Int, name: String) {
    println("\n======================== Testing optimistic locking ========================")
    println("Finding User:")
    val foundUser = em.find(User::class.java, userId)
    println("Found User with version: ${foundUser.version}, request version: $requestVersion")
    foundUser.fullName = name
    foundUser.version = requestVersion
    em.merge(foundUser)
}

fun removePermissionFromRole(em: EntityManager, roleName: String, permissionString: String) {
    println("\n======================== Removing permission $permissionString from role $roleName ========================")
    println("Finding role:")
    val role = em.createQuery("from persistence.permissions.model.Role u where u.name = :name", Role::class.java)
        .setParameter("name", roleName)
        .singleResult
    println("Found role: $role")
    println("Opening rolePermAssocs of role:")
    val permissions = role.rolePermAssociations.map { it.permission }
    println("Found permissions: $permissions")
    val permToDelete = permissions.filter { it.permissionString == permissionString }
    val rolePermAssocsTODelete =
        role.rolePermAssociations.filter { assoc -> permToDelete.map { perm -> perm.id }.contains(assoc.permission.id) }
    role.rolePermAssociations.removeAll(rolePermAssocsTODelete)
    em.merge(role)
}

fun removeRoleFromGroup(em: EntityManager, groupName: String, roleName: String) {
    println("\n======================== Removing role $roleName from group $groupName ========================")
    println("Finding role:")
    val role = em.createQuery("from persistence.permissions.model.Role u where u.name = :name", Role::class.java)
        .setParameter("name", roleName)
        .singleResult
    println("Found role: $role")
    println("Finding group:")
    val group = em.createQuery("from persistence.permissions.model.Group u where u.name = :name", Group::class.java)
        .setParameter("name", groupName)
        .singleResult
    println("Found group: $group")
    val roleGroupAssocs =
        em.createQuery(
            "from persistence.permissions.model.RoleGroupAssoc u where u.role.id = :roleId",
            RoleGroupAssociation::class.java
        )
            .setParameter("roleId", role.id)
            .resultList
    println("Found role group assocs: ${roleGroupAssocs.size} - ${roleGroupAssocs.map { it.id }}")
    println("Removing role group assoc from group:")
    group.roleGroupAssociations.removeAll(roleGroupAssocs)
    em.merge(group)
}

fun deleteUser(em: EntityManager, userLogin: String) {
    println("\n======================== Deleting user by login $userLogin ========================")
    println("Finding user:")
    val user = em.createQuery("from persistence.permissions.model.User u where u.loginName = :login", User::class.java)
        .setParameter("login", userLogin)
        .singleResult
    println("Removing user:")
    em.remove(user)
}

fun <T : Any> withEntityManager(block: (em: EntityManager) -> T?): T? {
    val emf = Persistence.createEntityManagerFactory("example-unit")
    val em = emf.createEntityManager()
    val result = try {
        em.transaction.begin()
        val temp = block.invoke(em)
        em.transaction.commit()
        temp
    } finally {
        em.close()
        emf.close()
    }
    return result
}

fun <T : Any> withEntityManagerAndFactory(block: (em: EntityManager, emf: EntityManagerFactory) -> T?): T? {
    val emf = Persistence.createEntityManagerFactory("example-unit")

    val em = emf.createEntityManager()
    val result = try {
        em.transaction.begin()
        val temp = block.invoke(em, emf)
        em.transaction.commit()
        temp
    } finally {
        em.close()
        emf.close()
    }
    return result
}

fun addPropertyToGroup(em: EntityManager, groupId: String, groupProperty: GroupProperty) {
    println("\n======================== Adding property to group $groupId ========================")
    println("Finding group:")
    val group = em.find(Group::class.java, groupId)
    group.groupProperties.add(groupProperty)
    println("Merging group with added group property:")
    em.merge(group)
}

fun addPropertyToUser(em: EntityManager, userId: String, userPropId: String, key: String, value: String) {
    println("\n======================== Adding property to user $userId ========================")
    println("Finding user:")
    val user = em.find(User::class.java, userId)
    user.userProperties.add(UserProperty(userPropId, Instant.now(), user, key, value))
    println("Merging user with added user property:")
    em.merge(user)
}

fun deleteGroup(em: EntityManager, groupId: String) {
    println("\n======================== Deleting group $groupId ========================")
    println("Finding Group:")
    val grp = em.find(Group::class.java, groupId)
/*        em.createQuery("UPDATE persistence.permissions.model.Group g SET g.parentGroup = NULL WHERE g.parentGroup.id = :group")
            .setParameter("group", grp.id)
            .executeUpdate()
        em.createQuery("UPDATE persistence.permissions.model.User u SET u.parentGroup = NULL WHERE u.parentGroup.id = :group")
            .setParameter("group", grp.id)
            .executeUpdate()*/
    // remove associations first before removing the group. UPDATE queries didn't seem to work.
    println("Executing query to find groups with parentGroupId matching group to be deleted:")
    val groups = em.createQuery("from persistence.permissions.model.Group u where u.parentGroup.id = :groupId", Group::class.java)
        .setParameter("groupId", groupId)
        .resultList
    println("Executing query to find users with parentGroupId matching group to be deleted:")
    val users = em.createQuery("from persistence.permissions.model.User u where u.parentGroup.id = :groupId", User::class.java)
        .setParameter("groupId", groupId)
        .resultList
    users.forEach {
        it.parentGroup = null
        println("Inside users loop, merging user with parentGroup set to null:")
        em.merge(it)
    }
    groups.forEach {
        println("Inside groups loop, merging group with parentGroup set to null:")
        it.parentGroup = null
        em.merge(it)
    }
    println("Removing group:")
    em.remove(grp)
}

fun setParentGroupOfGroup(em: EntityManager, groupId: String, parentGroupId: String) {
    println("\n======================== Setting parent group $parentGroupId on group $groupId ========================")
    println("Finding Group:")
    val grp = em.find(Group::class.java, groupId)
    println("Finding Parent group:")
    val parent = em.find(Group::class.java, parentGroupId)
    grp.parentGroup = parent
    println("Merging group with new parent group:")
    em.merge(grp)
}

fun setUserParentGroup(em: EntityManager, groupId: String, userId: String) {
    println("\n======================== Setting parent group $groupId on user $userId ========================")
    println("Finding Group:")
    val grp = em.find(Group::class.java, groupId)
    println("Finding User:")
    val usr = em.find(User::class.java, userId)
    usr.parentGroup = grp
    println("Merging User with new parent group set:")
    em.merge(usr)
}

fun addRoleToGroup(em: EntityManager, groupId: String, roleId: String) {
    println("\n======================== Adding role $roleId to group $groupId ========================")
    println("Finding Group:")
    val grp = em.find(Group::class.java, groupId)
    println("Finding Role:")
    val rol = em.find(Role::class.java, roleId)
    val assoc = RoleGroupAssociation(UUID.randomUUID().toString(), rol, grp, Instant.now())
    grp.roleGroupAssociations.add(assoc)
    println("Merging group with added roleGroupAssoc:")
    em.merge(grp)
}

fun addPermissionToRole(em: EntityManager, permissionId: String, roleId: String) {
    println("\n======================== Adding permission $permissionId to role $roleId ========================")
    println("Find permission:")
    val perm = em.find(Permission::class.java, permissionId)
    println("Find role:")
    val rol2 = em.find(Role::class.java, roleId)
    val assoc = RolePermissionAssociation(UUID.randomUUID().toString(), rol2, perm, Instant.now())
    rol2.rolePermAssociations.add(assoc)
    println("Merge role with added rolePermAssoc:")
    em.merge(rol2)
}

fun addRoleToUser(em: EntityManager, userId: String, roleId: String) {
    println("\n======================== Adding role $roleId to user $userId ========================")
    println("Find user:")
    val usr = em.find(User::class.java, userId)
    println("Find role:")
    val rol = em.find(Role::class.java, roleId)
    val assoc = RoleUserAssociation(UUID.randomUUID().toString(), rol, usr, Instant.now())
    usr.roleUserAssociations.add(assoc)
    println("Merging user with added roleUserAssoc:")
    em.merge(usr)
}

fun createUser(userId: String, name: String, login: String): User {
    return User(
        id = userId,
        updateTimestamp = Instant.now(),
        fullName = name,
        loginName = login,
        enabled = true,
        saltValue = null,
        hashedPassword = null,
        passwordExpiry = null,
        parentGroup = null
    )
}

fun createGroup(groupId: String, name: String): Group {
    return Group(
        id = groupId,
        updateTimestamp = Instant.now(),
        name = name,
        parentGroup = null
    )
}

fun createRole(roleId: String, name: String, groupVisibility: Group? = null): Role {
    return Role(
        id = roleId,
        updateTimestamp = Instant.now(),
        name = name,
        groupVisibility
    )
}

fun createPermission(permissionId: String, permStr: String = ".*", type: PermissionType = PermissionType.ALLOW, virtualNode: String? = null, groupVis: Group? = null): Permission {
    return Permission(
        id = permissionId,
        updateTimestamp = Instant.now(),
        groupVis,
        virtualNode,
        permissionType = type,
        permissionString = permStr
    )
}

fun addUserParentGroup(it: EntityManager, userId: String, groupId: String) {
    val user = it.find(User::class.java, userId)
    val group = it.find(Group::class.java, groupId)
    user.parentGroup = group
    it.merge(user)
}

fun addGroupParentGroup(it: EntityManager, groupId: String, parentGroupId: String) {
    val group = it.find(Group::class.java, groupId)
    val parentGroup = it.find(Group::class.java, parentGroupId)
    group.parentGroup = parentGroup
    it.merge(group)
}