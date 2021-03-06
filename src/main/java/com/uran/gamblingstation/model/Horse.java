package com.uran.gamblingstation.model;

import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.Digits;

@NamedQueries({
        @NamedQuery(name = Horse.DELETE, query = "DELETE FROM Horse h WHERE h.id=:id"),
        @NamedQuery(name = Horse.ALL_SORTED, query = "SELECT h FROM Horse h  ORDER BY h.name"),
})
@Entity
@Table(name = "horses", uniqueConstraints =
        {@UniqueConstraint(columnNames = "name", name = "horses_unique_name_idx")})
public class Horse extends NamedEntity {

    public static final String ALL_SORTED = "Horse.getAllSorted";
    public static final String DELETE = "Horse.delete";

    @Column(name = "ru_name", nullable = false)
    @NotEmpty
    private String ruName;

    @Column(name = "age", nullable = false)
    @Digits(fraction = 0, integer = 2)
    private int age;

    @Column(name = "wins", nullable = false)
    @Digits(fraction = 0, integer = 4)
    private int wins;

    @Column(name = "ready", nullable = false)
    private boolean ready;

    public Horse() {
    }

    public Horse(String name, String ruName, int age, int wins) {
        this(null, name, ruName, age, wins);
    }

    public Horse(Integer id, String name, String ruName, int age, int wins) {
        super(id, name);
        this.ruName = ruName;
        this.age = age;
        this.wins = wins;
        this.ready = false;
    }

    public Horse(Integer id, String name, String ruName, int age, int wins, boolean ready) {
        super(id, name);
        this.ruName = ruName;
        this.age = age;
        this.wins = wins;
        this.ready = ready;
    }

    public String getRuName() {
        return ruName;
    }

    public void setRuName(String ruName) {
        this.ruName = ruName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getWins() {
        return wins;
    }

    public void setWins(int wins) {
        this.wins = wins;
    }

    public Horse addWins() {
        this.wins++;
        return this;
    }

    public boolean isReady() {
        return ready;
    }

    public void setReady(boolean ready) {
        this.ready = ready;
    }

    @Override
    public String toString() {
        return "Horse{"
                + "id='" + id + '\''
                + ", name='" + name + '\''
                + ", ruName='" + ruName + '\''
                + ", age=" + age
                + ", wins=" + wins
                + ", ready=" + ready
                + '}';
    }
}
