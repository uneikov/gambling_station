package com.uran.gamblingstation.model;

import javax.persistence.*;
import java.time.LocalDateTime;
@NamedQueries({
        @NamedQuery(name = Stake.DELETE, query = "DELETE FROM Stake s WHERE s.id=:id "),
        /*@NamedQuery(name = Meal.GET_BETWEEN, query =
                "SELECT m FROM Meal m WHERE m.user.id=:userId AND m.dateTime BETWEEN :startDate AND :endDate ORDER BY m.dateTime DESC"),*/
        @NamedQuery(name = Stake.ALL_SORTED, query =
                "SELECT s FROM Stake s  ORDER BY s.dateTime DESC "),
        @NamedQuery(name = Stake.ALL_WINNING, query =
                "SELECT s FROM Stake s  WHERE s.wins=true ORDER BY s.dateTime ")
})
@Entity
@Table(name = "stakes", uniqueConstraints =
        {@UniqueConstraint(columnNames = {"user_id", "date_time"}, name = "stakes_unique_user_id_idx")})

public class Stake extends BaseEntity {

    public static final String ALL_SORTED = "Stake.getAllSorted";
    public static final String DELETE = "Stake.delete";
    public static final String ALL_WINNING = "Stake.getWinningStakes";

    @Column(name = "stake_value")
    private Double stakeValue;

    @Column(name = "date_time", columnDefinition = "timestamp default now()")
    private LocalDateTime dateTime;

    @Column(name = "wins")
    private boolean wins;

    @OneToOne(fetch = FetchType.LAZY)
    private Horse horse;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    public Stake(){
    }

    public Stake(Double stakeValue, LocalDateTime dateTime, boolean wins) {
        this.stakeValue = stakeValue;
        this.dateTime = dateTime;
        this.wins = wins;
    }

    public Stake(Integer id, Double stakeValue, LocalDateTime dateTime, boolean wins) {
        super(id);
        this.stakeValue = stakeValue;
        this.dateTime = dateTime;
        this.wins = wins;
    }

    public Double getStakeValue() {
        return stakeValue;
    }

    public void setStakeValue(Double stakeValue) {
        this.stakeValue = stakeValue;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public boolean getWins() {
        return wins;
    }

    public void setWins(boolean wins) {
        this.wins = wins;
    }

    public Horse getHorse() {
        return horse;
    }

    public void setHorse(Horse horse) {
        this.horse = horse;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
