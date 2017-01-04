package com.uran.gamblingstation.repository.jpa;

import com.uran.gamblingstation.model.Wallet;
import com.uran.gamblingstation.repository.WalletRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
@Transactional(readOnly = true)
public class JpaWalletRepositoryImpl implements WalletRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    @Transactional
    public Wallet save(Wallet wallet) {
        em.persist(wallet);
        return wallet;
    }

    @Override
    @Transactional
    public void update(Wallet wallet) {
        em.merge(wallet);
    }

    @Override
    @Transactional
    public boolean delete(int id) {
        return em.createNamedQuery(Wallet.DELETE).setParameter("id", id).executeUpdate() != 0;
    }

    @Override
    public Wallet get(int id) {
        return em.find(Wallet.class, id);
    }

    @Override
    public List<Wallet> getAll() {
        return em.createNamedQuery(Wallet.ALL_SORTED, Wallet.class).getResultList();
    }
}
