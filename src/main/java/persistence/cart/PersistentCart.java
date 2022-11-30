package persistence.cart;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "cart")
@NamedQuery(
        name="PersistentCart.findCartByCartName",
        query="FROM PersistentCart c WHERE c.name = :cartName"
)
public class PersistentCart extends PersistentState implements Serializable {

    @Column
    private String name;

    @Column
    private String userId;

    // Default constructor required by Hibernate
    public PersistentCart() {
    }

    public PersistentCart(String name, String userId, String txId, Integer index) {
        this.name = name;
        this.userId = userId;
        this.setStateRef(new PersistentStateRef(txId, index));
    }

    public String getName() {
        return name;
    }

    public String getUserId() {
        return userId;
    }
}