package liquibasetransactions.entities

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "shopping_user")
class ShoppingUser(
    @Id
    val id: String,

    @Column
    var name: String,
)