package no.nith.pg6100;

import javax.ejb.Remote;
import java.util.List;

@Remote
public interface AccountEJBRemote {

    public void save(final Account account);

    public Account findById(final int accountNumber);

    public List<Account> getAllAccounts();

}
