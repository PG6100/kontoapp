package no.nith.pg6100;


import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Stateless
@LocalBean
public class AccountEJB implements AccountEJBRemote {

    @PersistenceContext(unitName = "PG6100")
    EntityManager em;

    @Override
    public void save(Account account) {
        em.persist(account);
    }

    @Override
    public Account findById(int accountNumber) {
        return em.find(Account.class, accountNumber);
    }

    @Override
    public List<Account> getAllAccounts() {
        return em.createQuery("SELECT a from Account a").getResultList();
    }
}
