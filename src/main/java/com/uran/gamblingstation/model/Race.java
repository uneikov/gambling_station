package com.uran.gamblingstation.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@NamedQueries({
        @NamedQuery(name = Race.DELETE, query = "DELETE FROM Race r WHERE r.id=:id"),
        @NamedQuery(name = Race.BY_DATE_TIME, query = "SELECT r FROM Race r WHERE r.start=?1 AND r.finish=?2"),
        @NamedQuery(name = Race.ALL_SORTED, query = "SELECT r FROM Race r ORDER BY r.start DESC"),
        @NamedQuery(name = Race.ALL_SORTED_WITH_STAKES, query = "SELECT DISTINCT r FROM Race r JOIN FETCH r.stakes ORDER BY r.start DESC")
})
@Entity
@Table(name = "races", uniqueConstraints =
        {@UniqueConstraint(columnNames = {"id"}, name = "stakes_unique_id_idx")})
public class Race extends BaseEntity {
    public static final String DELETE = "Race.delete";
    public static final String BY_DATE_TIME = "Race.getByDateTime";
    public static final String ALL_SORTED = "Race.getAllSorted";
    public static final String ALL_SORTED_WITH_STAKES = "Race.getAllSortedWithStakes";

    @Column(name = "start", nullable = false)
    private LocalDateTime start;
    
    @Column(name = "finish", nullable = false)
    private LocalDateTime finish;

    // Horses list (serialized)
    @Column(name = "horses", nullable = false)
    private String horses;

    // Winning horse (serialized)
    @Column(name = "winning", nullable = false)
    private String winning;
    
    @OneToMany(mappedBy = "race", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    //@JsonIgnore
    @JsonManagedReference
    private List<Stake> stakes;

    public Race() {
    }

    public Race(Integer id, LocalDateTime startRace, LocalDateTime endRace) {
        super(id);
        this.start = startRace;
        this.finish = endRace;
    }

    public Race(Integer id, LocalDateTime startRace, LocalDateTime endRace, String horses, String winning) {
        super(id);
        this.start = startRace;
        this.finish = endRace;
        this.horses = horses;
        this.winning = winning;
    }

    public LocalDateTime getStart() {
        return start;
    }

    public void setStart(LocalDateTime start) {
        this.start = start;
    }

    public Race addStart(LocalDateTime start) {
        this.start = start;
        return this;
    }

    public LocalDateTime getFinish() {
        return finish;
    }

    public void setFinish(LocalDateTime finish) {
        this.finish = finish;
    }

    public Race addFinish(LocalDateTime finish) {
        this.finish = finish;
        return this;
    }

    public List<Stake> getStakes() {
        return stakes != null ? stakes : Collections.EMPTY_LIST;
    }

    public void setStakes(List<Stake> stakes) {
        this.stakes = stakes;
    }

    public String getHorses() {
        return horses;
    }

    public void setHorses(String horses) {
        this.horses = horses;
    }

    public Race addHorses(String horses) {
        this.horses = horses;
        return this;
    }

    public String getWinning() {
        return winning;
    }

    public void setWinning(String winning) {
        this.winning = winning;
    }

    public Race addWinning(String winning) {
        this.winning = winning;
        return this;
    }
    
    @Override
    public String toString() {
        return "Race{" +
                "start=" + start +
                ", finish=" + finish +
                ", horses='" + horses + '\'' +
                ", winning='" + winning + '\'' +
                '}';
    }
}
