package persistence.nplus1

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.OneToMany
import javax.persistence.Table

@Entity
@Table
class Car {
    constructor()
    constructor(id: String, name: String, wheels: List<Wheel>) {
        this.id = id
        this.name = name
        this.wheels = wheels
    }

    @Id
    @Column
    lateinit var id: String

    @Column
    lateinit var name: String

    @Column
    @OneToMany
    lateinit var wheels: List<Wheel>
}