package com.uran.gamblingstation.model;

import javax.persistence.*;
import java.time.LocalDateTime;

@SuppressWarnings("JpaQlInspection")
@NamedQueries({
        @NamedQuery(name = Stake.DELETE, query = "DELETE FROM Stake s WHERE s.id=:id "),
        @NamedQuery(name = Stake.GET_BETWEEN, query =
                "SELECT s FROM Stake s WHERE s.dateTime BETWEEN :startDate AND :endDate ORDER BY s.dateTime DESC"),
        @NamedQuery(name = Stake.GET_BETWEEN_WITH_USER, query =
                "SELECT s FROM Stake s WHERE s.dateTime BETWEEN :startDate AND :endDate AND s.user.id=:user_id ORDER BY s.dateTime DESC"),
        @NamedQuery(name = Stake.GET_BETWEEN_WITH_USER_AND_HORSE, query =
                "SELECT s FROM Stake s WHERE s.dateTime BETWEEN :startDate AND :endDate AND s.user.id=:user_id AND s.horse.id=:horse_id ORDER BY s.dateTime DESC"),
        @NamedQuery(name = Stake.GET_BETWEEN_WITH_HORSE, query =
                "SELECT s FROM Stake s WHERE s.dateTime BETWEEN :startDate AND :endDate AND s.horse.id=:horse_id ORDER BY s.dateTime DESC"),
        @NamedQuery(name = Stake.ALL_SORTED_WITH_USER, query =
                "SELECT s FROM Stake s WHERE s.user.id=:userId ORDER BY s.dateTime DESC "),
        @NamedQuery(name = Stake.ALL_SORTED, query =
                "SELECT s FROM Stake s ORDER BY s.dateTime DESC "),
        @NamedQuery(name = Stake.ALL_WINNING, query =
                "SELECT s FROM Stake s  WHERE s.wins=true ORDER BY s.dateTime ")
})
@Entity
@Table(name = "stakes", uniqueConstraints =
        {@UniqueConstraint(columnNames = {"user_id", "date_time"}, name = "stakes_unique_user_id_idx")})
public class Stake extends BaseEntity {

    public static final String ALL_SORTED = "Stake.getAllSorted";
    public static final String ALL_SORTED_WITH_USER = "Stake.getAllSortedWithUser";
    public static final String DELETE = "Stake.delete";
    public static final String ALL_WINNING = "Stake.getWinningStakes";
    public static final String GET_BETWEEN = "Stake.getBetween";
    public static final String GET_BETWEEN_WITH_USER = "Stake.getBetweenWithUser";
    public static final String GET_BETWEEN_WITH_HORSE = "Stake.getBetweenWithHorse";
    public static final String GET_BETWEEN_WITH_USER_AND_HORSE = "Stake.getBetweenWithUserAndHorse";

    @Column(name = "stake_value")
    private Double stakeValue;

    @Column(name = "date_time", columnDefinition = "timestamp default now()")
    //private Date dateTime = new Date();
    private LocalDateTime dateTime = LocalDateTime.now();

    @Column(name = "wins")
    private boolean wins;

    @Column(name = "amount")
    private Double amount;

    @OneToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "horse_id", referencedColumnName = "id", nullable = false)
    private Horse horse;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private User user;

    public Stake(){
    }

    public Stake(Double stakeValue, LocalDateTime dateTime, boolean wins, Double amount) {
        this.stakeValue = stakeValue;
        //this.dateTime = dateTime;
        this.wins = wins;
        this.amount = amount;
    }

    public Stake(Integer id, Double stakeValue, LocalDateTime dateTime, boolean wins, Double amount) {
        super(id);
        this.stakeValue = stakeValue;
        //this.dateTime = dateTime;
        this.wins = wins;
        this.amount = amount;
    }

    public Stake(User user, Horse horse, Double stakeValue, LocalDateTime dateTime, boolean wins, Double amount) {
        this.user = user;
        this.horse = horse;
        this.stakeValue = stakeValue;

        this.wins = wins;
        this.amount = amount;
    }

    public Stake(Integer id, User user, Horse horse, Double stakeValue, boolean wins, Double amount) {
        super(id);
        this.user = user;
        this.horse = horse;
        this.stakeValue = stakeValue;
        this.wins = wins;
        this.amount = amount;
    }
    public Stake(Integer id, User user, Horse horse, Double stakeValue, LocalDateTime dateTime, boolean wins, Double amount) {
        super(id);
        this.user = user;
        this.horse = horse;
        this.stakeValue = stakeValue;
        this.dateTime = dateTime;
        this.wins = wins;
        this.amount = amount;
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

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "Stake{" +
                "stakeValue=" + stakeValue +
                ", dateTime=" + dateTime +
                ", wins=" + wins +
                ", amount=" + amount +
                ", horse=" + horse +
                ", user=" + user +
                '}';
    }
}
