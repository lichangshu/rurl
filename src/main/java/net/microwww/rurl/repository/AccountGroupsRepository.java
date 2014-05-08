package net.microwww.rurl.repository;

import java.util.List;
import net.microwww.rurl.domain.Account;
import net.microwww.rurl.domain.AccountGroups;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

public interface AccountGroupsRepository extends Repository<AccountGroups, Integer> {

    public AccountGroups getById(int id);

    public List<AccountGroups> findAll();

    @Query("select u from AccountGroups as u where u.account.id = ?1 and u.groups.id = ?2")
    public AccountGroups getByAccountIdGroupsId(int aid, int gid);

    public List<AccountGroups> findByGroupsId(int id);

    @Query("select u.account from AccountGroups as u where u.groups.id = ?1")
    public List<Account> findAccountByGroupsId(int id);
}
