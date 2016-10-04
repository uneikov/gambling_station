package com.uran.gamblingstation.repository.jpa;

import com.uran.gamblingstation.model.Stake;
import com.uran.gamblingstation.repository.StakeRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
@Transactional(readOnly = true)
public class JpaStakeRepositoryImpl implements StakeRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<Stake> getALL() {
        return em.createNamedQuery(Stake.ALL_SORTED, Stake.class).getResultList();
    }

    @Override
    public Double getAllCash() {
        return getALL().stream().mapToDouble(Stake::getStakeValue).sum();
    }

    @Override
    public List<Stake> getWinningStakes() {
        return em.createNamedQuery(Stake.ALL_WINNING, Stake.class).getResultList();
    }

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
         em.createNamedQuery(Stake.DELETE)
                 .setParameter("id", id)
                 .executeUpdate();
    }

    @Override
    public Stake get(int id) {
        return em.find(Stake .class, id);
    }
}
