/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.microwww.rurl.service;

import java.util.List;
import net.microwww.rurl.domain.Webapp;
import net.microwww.rurl.domain.Webappurl;

/**
 *
 * @author changshu.li
 */
public interface WebappService {

    public void save(Webapp app);

    public Webappurl saveWebappurl(Webapp app, Webappurl url, Webappurl purl);

    public void deleUrl(Webappurl url);

    public void save(Webapp app, List<Webappurl> urls);

    public void clearAndsave(Webapp app, List<Webappurl> urls);

    public void clearWebappurl(Webapp app);
}
