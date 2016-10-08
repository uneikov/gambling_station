package com.uran.gamblingstation.model;

import javax.persistence.*;

@Entity
@Table(name = "wallets", uniqueConstraints =
        {@UniqueConstraint(columnNames = "user_id", name = "wallets_unique_user_id_idx")})
public class Wallet {

    @Id
    @Column(name = "user_id")
    private Integer id;

    @Column(name = "cash_value")
    private Double cash;

    /*@OneToOne
    @PrimaryKeyJoinColumn
    private User user;*/

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
