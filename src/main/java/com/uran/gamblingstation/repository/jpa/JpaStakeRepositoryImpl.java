package com.uran.gamblingstation.repository.jpa;

import com.uran.gamblingstation.model.Horse;
import com.uran.gamblingstation.model.Stake;
import com.uran.gamblingstation.model.User;
import com.uran.gamblingstation.repository.StakeRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Repository
@Transactional(readOnly = true)
public class JpaStakeRepositoryImpl implements StakeRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    @Transactional
    public Stake save(Stake stake) {
        em.persist(stake);
        return stake;
    }

    @Override
    @Transactional
    public void update(Stake stake) {
        em.merge(stake);
    }

    @Override
    @Transactional
    public boolean delete(int id) {
      /*  em.remove(em.find(Stake.class, id));*/
        return em.createNamedQuery(Stake.DELETE)
                 .setParameter("id", id)
                 .executeUpdate() != 0;
    }

    @Override
    public Stake get(int id) {
        return em.find(Stake.class, id);
    }

    @Override
    public List<Stake> getAllByUserId(int userId) {
        return em.createNamedQuery(Stake.ALL_SORTED_WITH_USER, Stake.class)
                .setParameter("userId", userId)
                .getResultList();
    }

    @Override
    public List<Stake> getAllByRaceId(int raceId) {
        return em.createNamedQuery(Stake.ALL_WITH_RACE_ID, Stake.class)
                .setParameter("raceId", raceId)
                .getResultList();
    }

    @Override
    public List<Stake> getAllByHorseIdAndRaceId(int horseId, int raceId) {
        return em.createNamedQuery(Stake.SORTED_WITH_HORSE_ID_AND_RACE_ID, Stake.class)
                .setParameter("horseId", horseId)
                .setParameter("raceId", raceId)
                .getResultList();
    }

    @Override
    public List<Stake> getAll() {
        return em.createNamedQuery(Stake.ALL_SORTED, Stake.class).getResultList();
    }

    @Override
    public Double getAllCash(LocalDateTime startDate, LocalDateTime endDate) {
        return getBetween(startDate, endDate).stream().mapToDouble(Stake::getStakeValue).sum();
    }

    @Override
    public Double getAllCash(int raceId) {
        return getAllByRaceId(raceId).stream().mapToDouble(Stake::getStakeValue).sum();
    }

    @Override
    public List<Stake> getAllWinningStakes() {
        return em.createNamedQuery(Stake.ALL_WINNING, Stake.class).getResultList();
    }

    @Override
    public List<Stake> getWinningStakes(LocalDateTime startDate, LocalDateTime endDate, int userId) {
        return getBetween(startDate, endDate, userId).stream()
                .filter(Stake::isWins)
                .collect(Collectors.toList());
    }

    @Override
    public List<Stake> getWinningStakes(int raceId) {
        return em.createNamedQuery(Stake.WINNING_WITH_RACE_ID, Stake.class)
                .setParameter("raceId", raceId)
                .getResultList();
    }

    @Override
    public List<Stake> getBetween(LocalDateTime startDate, LocalDateTime endDate) {
        return em.createNamedQuery(Stake.GET_BETWEEN, Stake.class)
                .setParameter("startDate", startDate)
                .setParameter("endDate", endDate)
                .getResultList();
    }

    @Override
    public List<Stake> getBetween(LocalDateTime startDate, LocalDateTime endDate, int userId) {
        return em.createNamedQuery(Stake.GET_BETWEEN_WITH_USER, Stake.class)
                .setParameter("startDate", startDate)
                .setParameter("endDate", endDate)
                .setParameter("user_id", userId)
                .getResultList();
    }

    @Override
    public List<Stake> getBetween(User user, LocalDateTime startDate, LocalDateTime endDate) {
        return em.createNamedQuery(Stake.GET_BETWEEN_WITH_USER, Stake.class)
                .setParameter("startDate", startDate)
                .setParameter("endDate", endDate)
                .setParameter("user_id", user.getId())
                .getResultList();
    }

    @Override
    public List<Stake> getBetweenWithHorse(int horseId, LocalDateTime startDate, LocalDateTime endDate) {
        return em.createNamedQuery(Stake.GET_BETWEEN_WITH_HORSE, Stake.class)
                .setParameter("startDate", startDate)
                .setParameter("endDate", endDate)
                .setParameter("horse_id", horseId)
                .getResultList();
    }

    @Override
    public List<Stake> getBetween(User user, Horse horse, LocalDateTime startDate, LocalDateTime endDate) {
        return em.createNamedQuery(Stake.GET_BETWEEN_WITH_USER_AND_HORSE, Stake.class)
                .setParameter("startDate", startDate)
                .setParameter("endDate", endDate)
                .setParameter("user_id", user.getId())
                .setParameter("horse_id", horse.getId())
                .getResultList();
    }

}
