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
    public Wallet save(Wallet wallet) {
        return null;
    }

    @Override
    public boolean delete(int id) {
        return false;
    }

    @Override
    public Wallet get(int id) {
        return em.find(Wallet.class, id);
    }

    @Override
    public List<Wallet> getAll() {
        return null;
    }
}
