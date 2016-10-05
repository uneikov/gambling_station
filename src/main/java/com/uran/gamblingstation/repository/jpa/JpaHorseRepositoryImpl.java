package com.uran.gamblingstation.repository.jpa;

import com.uran.gamblingstation.model.Horse;
import com.uran.gamblingstation.repository.HorseRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

import static com.uran.gamblingstation.model.BaseEntity.ADMIN_ID;

@Repository
@Transactional(readOnly = true)
public class JpaHorseRepositoryImpl implements HorseRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Horse get(int id, int userId) {
        return em.find(Horse.class, id);
    }

    @Override
    @Transactional
    public void delete(int id) {
       /* em.createNamedQuery(Horse.DELETE).setParameter("id", id);*/
        Horse horseRef = em.getReference(Horse.class, id);
        em.remove(horseRef);
    }

    @Override
    @Transactional
    public Horse save(Horse horse, int userId) {
        if (horse.isNew()) {
            if (userId == ADMIN_ID) {
                em.persist(horse);
            }else { // Unauthorized operation
                return null;
            }
        } else {
            if (userId == ADMIN_ID) {
                return em.merge(horse);
            }
            else { // Unauthorized operation
                return null;
            }
        }
        return horse;
    }

    @Override
    public List<Horse> getAll() {
        return em.createNamedQuery(Horse.ALL_SORTED, Horse.class).getResultList();
    }
}
