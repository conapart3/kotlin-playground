package persistence.cart;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "user")
public class PersistentUser implements Serializable {

    @Id
    @Column
    private String userId;
    @Column
    private String name;
    @OneToOne
    @JoinColumn(name="userId")
    private PersistentCart cart;

    public PersistentUser(String userId, String name) {
        this.userId = userId;
        this.name = name;
    }

    public PersistentUser() {
    }

    public String getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public PersistentCart getCart() {
        return cart;
    }

    public void setCart(PersistentCart cart) {
        this.cart = cart;
    }
}
