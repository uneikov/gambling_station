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
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Repository
@Transactional(readOnly = true)
public class JpaStakeRepositoryImpl implements StakeRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    @Transactional
    public boolean save(Stake stake) {
        em.persist(stake);
        return false;
    }

    @Override
    @Transactional
    public boolean update(Stake stake) {
        em.merge(stake);
        return false;
    }

    @Override
    @Transactional
    public void delete(int id) {
      /*  em.remove(em.find(Stake.class, id));*/
        em.createNamedQuery(Stake.DELETE)
                 .setParameter("id", id)
                 .executeUpdate();
    }

    @Override
    public Stake get(int id) {
        return em.find(Stake.class, id);
    }

    @Override
    public List<Stake> getALL() {
        return em.createNamedQuery(Stake.ALL_SORTED, Stake.class).getResultList();
    }

    @Override
    public Double getAllCash() {
        return getALL().stream().mapToDouble(Stake::getStakeValue).sum();
    }

    @Override
    public List<Stake> getAllWinningStakes() {
        return em.createNamedQuery(Stake.ALL_WINNING, Stake.class).getResultList();
    }

    @Override
    public List<Stake> getWinningStakes(LocalDateTime startDate, LocalDateTime endDate) {
        return getBetween(startDate, endDate).stream().filter(Stake::getWins).collect(Collectors.toList());
    }

    @Override
    public Collection<Stake> getBetween(LocalDateTime startDate, LocalDateTime endDate) {
        return em.createNamedQuery(Stake.GET_BETWEEN, Stake.class)
                .setParameter("startDate", startDate)
                .setParameter("endDate", endDate)
                .getResultList();
    }

    @Override
    public Collection<Stake> getBetween(User user, LocalDateTime startDate, LocalDateTime endDate) {
        return em.createNamedQuery(Stake.GET_BETWEEN_WITH_USER, Stake.class)
                .setParameter("startDate", startDate)
                .setParameter("endDate", endDate)
                .setParameter("user_id", user.getId())
                .getResultList();
    }

    @Override
    public Collection<Stake> getBetween(Horse horse, LocalDateTime startDate, LocalDateTime endDate) {
        return em.createNamedQuery(Stake.GET_BETWEEN_WITH_HORSE, Stake.class)
                .setParameter("startDate", startDate)
                .setParameter("endDate", endDate)
                .setParameter("horse_id", horse.getId())
                .getResultList();
    }

    @Override
    public Collection<Stake> getBetween(User user, Horse horse, LocalDateTime startDate, LocalDateTime endDate) {
        return em.createNamedQuery(Stake.GET_BETWEEN, Stake.class)
                .setParameter("startDate", startDate)
                .setParameter("endDate", endDate)
                .setParameter("user_id", user.getId())
                .setParameter("horse_id", horse.getId())
                .getResultList();
    }

}
