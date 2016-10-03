package com.uran.gamblingstation.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "wallets")
public class Wallet extends BaseEntity{

    @Column(name = "cash_value")
    protected Double cash;

    @OneToOne
    private User user;

    public Wallet(Double cash) {
        this.cash = cash;
    }

    public Wallet(Integer id, Double cash) {
        super(id);
        this.cash = cash;
    }

    public Double getCash() {
        return cash;
    }

    public void setCash(Double cash) {
        this.cash = cash;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
