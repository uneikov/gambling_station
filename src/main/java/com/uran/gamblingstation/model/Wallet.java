package com.uran.gamblingstation.model;

import javax.persistence.*;
@NamedQueries({
        @NamedQuery(name = Wallet.DELETE, query = "DELETE FROM Wallet w WHERE w.id=:id"),
        @NamedQuery(name = Wallet.ALL_SORTED, query = "SELECT w FROM Wallet w ORDER BY w.cash DESC "),
})
@Entity
@Table(name = "wallets", uniqueConstraints =
        {@UniqueConstraint(columnNames = "user_id", name = "wallets_unique_user_id_idx")})
/*@Table(name = "wallets", uniqueConstraints =
        {@UniqueConstraint(columnNames = "id", name = "wallets_unique_user_id_idx")})*/
public class Wallet {

    public static final String DELETE = "Wallet.delete";
    public static final String ALL_SORTED = "Wallet.getAllSorted";

    @Id
    @Column(name = "user_id")
    private Integer id;
    @Column(name = "cash_value")
    private Double cash;

    public Wallet(){
    }

    public Wallet(Double cash) {
        this.cash = cash;
    }

    public Wallet(Integer id, Double cash) {
        this.id = id;
        this.cash = cash;
    }

    public boolean isEmpty(){
        return Double.compare(this.cash, 0.0) == 0;
    }

    public Double getCash() {
        return cash;
    }

    public void setCash(Double cash) {
        this.cash = cash;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Wallet{" +
                "id=" + id +
                ", cash=" + cash +
                '}';
    }
}
