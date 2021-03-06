package net.microwww.rurl.repository;

import java.util.List;
import net.microwww.rurl.domain.Account;
import org.springframework.data.repository.Repository;

/**
 * Auto-generated by: org.apache.openjpa.jdbc.meta.ReverseMappingTool$AnnotatedCodeGenerator
 */
public interface AccountRepository extends Repository<Account, Integer> {

    public Account getById(int id);

    public List<Account> findAll();

    public Account getByAccount(String account);
}
