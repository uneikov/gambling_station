package com.uran.gamblingstation.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.time.LocalDateTime;

@SuppressWarnings("JpaQlInspection")
@NamedQueries({
        @NamedQuery(name = Stake.DELETE, query =
                "DELETE FROM Stake s WHERE s.id=:id AND s.user.id=:userId"),
        @NamedQuery(name = Stake.GET_BETWEEN, query =
                "SELECT s FROM Stake s WHERE s.dateTime BETWEEN :startDate AND :endDate ORDER BY s.dateTime DESC"),
        @NamedQuery(name = Stake.GET_BETWEEN_WITH_USER, query =
                "SELECT s FROM Stake s WHERE s.dateTime BETWEEN :startDate AND :endDate AND s.user.id=:user_id ORDER BY s.dateTime DESC"),
        @NamedQuery(name = Stake.ALL_SORTED_BELONG_TO_USER, query =
                "SELECT s FROM Stake s WHERE s.user.id=:userId ORDER BY s.dateTime DESC "),
        @NamedQuery(name = Stake.ALL_SORTED, query =
                "SELECT s FROM Stake s ORDER BY s.dateTime DESC "),
        @NamedQuery(name = Stake.ALL_WINNING, query =
                "SELECT s FROM Stake s  WHERE s.wins=true ORDER BY s.dateTime "),
        @NamedQuery(name = Stake.WINNING_WITH_RACE_ID, query =
                "SELECT s FROM Stake s  WHERE s.race.id=:raceId AND s.wins=true ORDER BY s.dateTime "),
        @NamedQuery(name = Stake.ALL_WITH_RACE_ID, query =
                "SELECT s FROM Stake s  WHERE s.race.id=:raceId ORDER BY s.dateTime DESC"),
        @NamedQuery(name = Stake.SORTED_WITH_HORSE_ID_AND_RACE_ID, query =
                "SELECT s FROM Stake s  WHERE s.horse.id=:horseId AND s.race.id=:raceId ORDER BY s.dateTime DESC"),
        @NamedQuery(name = Stake.WITH_USER, query =
                "SELECT DISTINCT s FROM Stake s  JOIN FETCH s.user WHERE s.id=:id  ORDER BY s.dateTime DESC")
})
@Entity
@Table(name = "stakes", uniqueConstraints =
        {@UniqueConstraint(columnNames = {"user_id", "date_time"}, name = "stakes_unique_user_id_idx")})
public class Stake extends BaseEntity {

    public static final String ALL_SORTED = "Stake.getAllSorted";
    public static final String ALL_SORTED_BELONG_TO_USER = "Stake.getAllSortedBelongToUser";
    public static final String DELETE = "Stake.delete";
    public static final String ALL_WINNING = "Stake.getWinningStakes";
    public static final String WINNING_WITH_RACE_ID = "Stake.getWinningWithRaceId";
    public static final String GET_BETWEEN = "Stake.getBetween";
    public static final String GET_BETWEEN_WITH_USER = "Stake.getBetweenWithUser";
    public static final String ALL_WITH_RACE_ID = "Stake.getAllWithRaceId";
    public static final String SORTED_WITH_HORSE_ID_AND_RACE_ID = "Stake.getAllWithHorseIdAndRaceId";
    public static final String WITH_USER = "Stake.getWithUser";

    @Column(name = "stake_value")
    private Double stakeValue;

    @Column(name = "date_time", columnDefinition = "timestamp default now()")
    private LocalDateTime dateTime = LocalDateTime.now();

    @Column(name = "wins")
    private boolean wins;

    @Column(name = "amount")
    private Double amount;

    @Column(name = "editable", nullable = false)
    private boolean editable;

    @OneToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "horse_id", referencedColumnName = "id", nullable = false)
    private Horse horse;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private User user;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "race_id", referencedColumnName = "id", nullable = false)
    @JsonBackReference
    private Race race;

    public Stake(){
    }

    public Stake(Double stakeValue, boolean wins, Double amount) {
        this.stakeValue = stakeValue;
        this.wins = wins;
        this.amount = amount;
        this.editable = true;
    }

    public Stake(Integer id, Double stakeValue, boolean wins, Double amount) {
        super(id);
        this.stakeValue = stakeValue;
        this.wins = wins;
        this.amount = amount;
        this.editable = true;
    }

    public Stake(Integer id, User user, Horse horse, Double stakeValue, boolean wins, Double amount) {
        super(id);
        this.user = user;
        this.horse = horse;
        this.stakeValue = stakeValue;
        this.wins = wins;
        this.amount = amount;
        this.editable = true;
    }

    public Stake(Integer id, User user, Horse horse, Double stakeValue, LocalDateTime dateTime, boolean wins, Double amount, boolean editable) {
        super(id);
        this.user = user;
        this.horse = horse;
        this.stakeValue = stakeValue;
        this.dateTime = dateTime;
        this.wins = wins;
        this.amount = amount;
        this.editable = editable;
    }

    public Stake(Integer id, User user, Horse horse, Race race, Double stakeValue, LocalDateTime dateTime, boolean wins, Double amount, boolean editable) {
        super(id);
        this.user = user;
        this.horse = horse;
        this.race = race;
        this.stakeValue = stakeValue;
        this.dateTime = dateTime;
        this.wins = wins;
        this.amount = amount;
        this.editable = editable;
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

    public boolean isWins() {
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

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public boolean isEditable() {
        return editable;
    }

    public void setEditable(boolean editable) {
        this.editable = editable;
    }

    public Race getRace() {
        return race;
    }

    public void setRace(Race race) {
        this.race = race;
    }

    @Override
    public String toString() {
        return "Stake{" +
                "stakeValue=" + stakeValue +
                ", dateTime=" + dateTime +
                ", wins=" + wins +
                ", amount=" + amount +
                ", editable=" + editable +
                '}';
    }
}
