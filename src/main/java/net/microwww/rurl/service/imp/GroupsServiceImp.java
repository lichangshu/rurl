/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.microwww.rurl.service.imp;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import net.microwww.rurl.domain.Account;
import net.microwww.rurl.domain.AccountGroups;
import net.microwww.rurl.domain.Groups;
import net.microwww.rurl.domain.WebappurlGroups;
import net.microwww.rurl.domain.Webappurl;
import net.microwww.rurl.service.GroupsService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author changshu.li
 */
@Service
@Transactional
public class GroupsServiceImp implements GroupsService {

    private static final Log logger = LogFactory.getLog(GroupsServiceImp.class);
    @PersistenceContext
    private EntityManager em;

    public void save(Groups group) {
        Groups dbo = Groups.getById(group.getId());
        if (dbo != null) {
            BeanUtils.copyProperties(group, dbo);
            group = dbo;
        }
        if (dbo != null) {
            em.merge(group);
        } else {
            em.persist(group);
        }
    }

    public void save(Account account) {
        Account dbo = Account.getById(account.getId());
        if (dbo != null) {
            BeanUtils.copyProperties(account, dbo);
            account = dbo;
        }
        if (dbo != null) {
            em.merge(account);
        } else {
            em.persist(account);
        }
    }

    public AccountGroups addAccount(Groups group, Account account) {
        AccountGroups ag = null;
        if (account.getId() > 0 && group.getId() > 0) {
            ag = AccountGroups.getByAccountIdGroupsId(account.getId(), group.getId());
        }
        if (ag == null) {
            ag = new AccountGroups();
        }
        ag.setAccount(account);
        ag.setGroups(group);

        if (ag.getId() > 0) {
            em.merge(ag);
        } else {
            em.persist(ag);
        }
        return ag;
    }

    public void saveAccount(Groups group, List<Account> accounts) {
        for (Account acc : accounts) {
            this.addAccount(group, acc);
        }
    }

    public void clearAndsave(Groups group, List<Account> accounts) {
        clearGroupAccount(group);
        saveAccount(group, accounts);
    }

    public void clearGroupAccount(Groups group) {
        List<AccountGroups> ags = AccountGroups.findByGroupsId(group.getId());
        for (AccountGroups ag : ags) {
            em.remove(ag);
        }
    }

    public int deleteGroupAccount(Groups group, Account account) {
        AccountGroups ag = AccountGroups.getByAccountIdGroupsId(account.getId(), group.getId());
        if (ag != null) {
            em.remove(ag);
            return ag.getId();
        }
        return 0;
    }

    public void save(Webappurl url) {
        Webappurl dbo = Webappurl.getById(url.getId());
        if (dbo != null) {
            BeanUtils.copyProperties(url, dbo);
            url = dbo;
        }
        if (dbo != null) {
            em.merge(url);
        } else {
            em.persist(url);
        }
    }

    public WebappurlGroups addWebappurl(Groups group, Webappurl url) {
        WebappurlGroups gurl = WebappurlGroups.getByGroupIdWebappurlId(group.getId(), url.getId());
        if (gurl == null) {
            WebappurlGroups gu = new WebappurlGroups();
            gu.setGroups(group);
            gu.setWebappurl(url);
            em.persist(gu);
        }
        return gurl;
    }

    public void saveWebappurl(Groups group, List<Webappurl> urls) {
        for (Webappurl url : urls) {
            addWebappurl(group, url);
        }
    }

    public void deleWebappurlGroups(Groups group, List<Webappurl> urls) {
        for (Webappurl url : urls) {
            WebappurlGroups gurl = WebappurlGroups.getByGroupIdWebappurlId(group.getId(), url.getId());
            if (gurl != null) {
                deleWebappurlGroups(gurl);
            }
        }
    }

    public void deleWebappurlGroups(WebappurlGroups gurl) {
        gurl = WebappurlGroups.getById(gurl.getId());
        em.remove(gurl);
    }
}
