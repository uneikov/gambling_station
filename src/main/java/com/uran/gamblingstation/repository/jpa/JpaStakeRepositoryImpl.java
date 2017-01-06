package com.uran.gamblingstation.repository.jpa;

import com.uran.gamblingstation.model.Stake;
import com.uran.gamblingstation.repository.StakeRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;
import java.util.List;

@Repository
@Transactional(readOnly = true)
public class JpaStakeRepositoryImpl implements StakeRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    @Transactional
    public Stake save(Stake stake, int userId) {
        if (!stake.isNew() && get(stake.getId(), userId) == null) {
            return null;
        }
        if (stake.isNew()) {
            em.persist(stake);
            return stake;
        } else {
            return em.merge(stake);
        }
    }

    @Override
    @Transactional
    public void update(Stake stake) {
        em.merge(stake);
    }

    @Override
    @Transactional
    public boolean delete(int id, int userId) {
        return em.createNamedQuery(Stake.DELETE)
                 .setParameter("id", id)
                 .setParameter("userId", userId)
                 .executeUpdate() != 0;
    }

    @Override
    public Stake get(int id, int userId) {
        Stake stake = em.find(Stake.class, id);
        return stake != null && stake.getUser().getId() == userId ? stake : null;
    }

    @Override
    public Stake getWithUser(int id) {
        return em.createNamedQuery(Stake.WITH_USER, Stake.class)
                .setParameter("id", id)
                .getSingleResult();
    }

    @Override
    public List<Stake> getAllByUserId(int userId) {
        return em.createNamedQuery(Stake.ALL_SORTED_BELONG_TO_USER, Stake.class)
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
    public Double getAllCash(int raceId) {
        return getAllByRaceId(raceId).stream().mapToDouble(Stake::getStakeValue).sum();
    }

    @Override
    public List<Stake> getAllWinningStakes() {
        return em.createNamedQuery(Stake.ALL_WINNING, Stake.class).getResultList();
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
}
