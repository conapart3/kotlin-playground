package liquibasetransactions

import java.sql.DriverManager
import java.util.UUID
import javax.persistence.EntityManager
import javax.persistence.Persistence
import liquibase.Contexts
import liquibase.Liquibase
import liquibase.database.DatabaseFactory
import liquibase.database.jvm.JdbcConnection
import liquibase.resource.ClassLoaderResourceAccessor
import liquibasetransactions.entities.ShoppingCart
import liquibasetransactions.entities.ShoppingUser
import org.postgresql.ds.PGSimpleDataSource

fun main() {
    // Scenario to test:
    // will an outer transaction created by java roll back
    // when one of the migrations fail

    // create 4 changesets
    //  1 - works - see if this is rolled back
    //  2 - works - see if this is rolled back
    //  3 - fails - see that this never ran
    //  4 - works - see that this never ran

    // what kind of changeset will fail?
    // needs to fail at the db level i.e. when trying to add some data or change table

    val liquibase = createLiquibaseConnection()
    withEntityManager {
        liquibase.update(Contexts())
    }

    withEntityManager { em ->
        em.persist(ShoppingUser("u1", "conal"))
        em.persist(ShoppingCart("cart1", "cart-1"))
    }

//    withEntityManager {
//        it.merge(ShoppingCart("cart1", "updated-name"))
//        liquibase.update(Contexts())
//    }
}

private fun createLiquibaseConnection(): Liquibase {
    val datasource = PGSimpleDataSource()
//        datasource.setURL("jdbc:postgresql://127.0.0.1:5432")
    datasource.serverName = "localhost"
    datasource.portNumber = 5432
    datasource.user = "postgres"
    datasource.password = "password"
    datasource.databaseName = "postgres"

    val database = DatabaseFactory
        .getInstance()
        .findCorrectDatabaseImplementation(JdbcConnection(datasource.connection))

    return Liquibase(
        "changelog\\cart.changelog-master.xml",
        ClassLoaderResourceAccessor(),
        database
    )
}

fun <T : Any> withEntityManager(block: (em: EntityManager) -> T?): T? {
    val emf = Persistence.createEntityManagerFactory("example-unit")
    val em = emf.createEntityManager()

    em.use { em ->
        val currentTransaction = em.transaction
        currentTransaction.begin()

        return try {
            block(em)
        } catch (e: Exception) {
            currentTransaction.setRollbackOnly()
            throw e
        } finally {
            if (!currentTransaction.rollbackOnly) {
                currentTransaction.commit()
            } else {
                currentTransaction.rollback()
            }
        }
    }
}

inline fun <R> EntityManager.use(block: (EntityManager) -> R): R {
    return try {
        block(this)
    } finally {
        close()
    }
}
