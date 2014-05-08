/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.microwww.rurl.service;

import java.util.List;
import net.microwww.rurl.domain.Account;
import net.microwww.rurl.domain.AccountGroups;
import net.microwww.rurl.domain.Groups;
import net.microwww.rurl.domain.WebappurlGroups;
import net.microwww.rurl.domain.Webappurl;

/**
 *
 * @author changshu.li
 */
public interface GroupsService {

    public void save(Groups group);

    public void save(Account group);

    public AccountGroups addAccount(Groups group, Account account);

    public void saveAccount(Groups group, List<Account> account);

    public void clearAndsave(Groups group, List<Account> account);

    public void clearGroupAccount(Groups group);

    public int deleteGroupAccount(Groups group, Account account);

    public void save(Webappurl url);

    public WebappurlGroups addWebappurl(Groups group, Webappurl url);

    public void saveWebappurl(Groups group, List<Webappurl> urls);

    public void deleWebappurlGroups(Groups group, List<Webappurl> urls);

    public void deleWebappurlGroups(WebappurlGroups gurl);
}
