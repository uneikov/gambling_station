package com.uran.gamblingstation.repository.jpa;

import com.uran.gamblingstation.model.Horse;
import com.uran.gamblingstation.repository.HorseRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
@Transactional(readOnly = true)
public class JpaHorseRepositoryImpl implements HorseRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Horse get(int id) {
        return em.find(Horse.class, id);
    }

    @Override
    @Transactional
    public boolean delete(int id) {
        return em.createNamedQuery(Horse.DELETE).setParameter("id", id).executeUpdate() != 0;
    }

    @Override
    @Transactional
    public Horse save(Horse horse) {
        if (horse.isNew()) {
            em.persist(horse);
        } else {
            return em.merge(horse);
        }
        return horse;
    }

    @Override
    public List<Horse> getAll() {
        return em.createNamedQuery(Horse.ALL_SORTED, Horse.class).getResultList();
    }
}
