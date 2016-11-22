package com.uran.gamblingstation.repository.jpa;

import com.uran.gamblingstation.model.Race;
import com.uran.gamblingstation.repository.RaceRepository;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;
import java.util.Collection;

@Repository
@Transactional(readOnly = true)
public class JpaRaceRepositoryImpl implements RaceRepository{

    @PersistenceContext
    private EntityManager em;

    @Override
    @Transactional
    public Race save(Race race) {
        if (race.isNew()) {
            em.persist(race);
            return race;
        } else {
            return em.merge(race); //?????????????
        }
    }

    @Override
    @Transactional
    public boolean delete(int id) {
        return em.createNamedQuery(Race.DELETE).setParameter("id", id).executeUpdate() != 0;
    }

    @Override
    public Race get(int id) {
        return em.find(Race.class, id);
    }

    @Override
    public Race getByDateTime(LocalDateTime start, LocalDateTime finish) {
        Collection<Race> races = em.createNamedQuery(Race.BY_DATE_TIME, Race.class)
                .setParameter(1, start)
                .setParameter(2, finish)
                .getResultList();
        return DataAccessUtils.singleResult(races);
    }

    @Override
    public Collection<Race> getAll() {
        return em.createNamedQuery(Race.ALL_SORTED, Race.class).getResultList();
    }

    @Override
    @Transactional
    public void update(Race race) { //?????????????????/
        em.merge(race);
    }
}
