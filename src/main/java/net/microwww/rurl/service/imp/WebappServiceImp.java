/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.microwww.rurl.service.imp;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import net.microwww.rurl.domain.Webapp;
import net.microwww.rurl.domain.Webappurl;
import net.microwww.rurl.service.WebappService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author changshu.li
 */
@Service
@Transactional
public class WebappServiceImp implements WebappService {

    private static final int CAGGOREY_MAX = 10;

    @PersistenceContext
    private EntityManager em;

    @Transactional
    public void save(Webapp app) {
        Webapp dbapp = Webapp.getById(app.getId());
        if (dbapp != null) {
            BeanUtils.copyProperties(app, dbapp);
            app = dbapp;
        }
        if (dbapp != null) {
            em.merge(app);
        } else {
            em.persist(app);
        }
    }

    @Transactional
    public Webappurl saveWebappurl(Webapp app, Webappurl url, Webappurl purl) {
        url.setWebapp(app);
        url.setParentUrl(purl);
        Webappurl dburl = Webappurl.getById(url.getId());
        if (dburl != null) {
            BeanUtils.copyProperties(url, dburl);
            url = dburl;
        }
        if (dburl != null) {
            em.merge(url);
        } else {
            em.persist(url);
        }
        check(Webappurl.getById(url.getId()), 0);
        return url;
    }

    @Transactional
    public void deleUrl(Webappurl url) {
        url = Webappurl.getById(url.getId());
        em.remove(url);
    }

    @Transactional
    public void save(Webapp app, List<Webappurl> urls) {
        for (Webappurl url : urls) {
            this.saveWebappurl(app, url, url.getParentUrl());
        }
    }

    @Transactional
    public void clearAndsave(Webapp app, List<Webappurl> urls) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void clearWebappurl(Webapp app) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public static void check(Webappurl url, int i) {
        if (url == null) {
            return;
        }
        if (url.getId() <= 0) {
            throw new IllegalArgumentException("请先保存后调用 check! " + CAGGOREY_MAX);
        }
        url = url.getParentUrl();
        if (i > CAGGOREY_MAX) {
            throw new IllegalArgumentException("父菜单出现循环或菜单超出最大层级 " + CAGGOREY_MAX);
        } else {
            check(url, i + 1);
        }
    }
}
