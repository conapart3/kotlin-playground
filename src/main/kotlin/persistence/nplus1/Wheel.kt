package persistence.nplus1

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id

@Entity
class Wheel {
    constructor()
    constructor(id: String) {
        this.id = id
    }

    @Id
    @Column
    lateinit var id: String

}