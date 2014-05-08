/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.microwww.rurl.service.imp;

import com.alibaba.fastjson.JSONObject;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import net.microwww.common.BeanContextFactory;
import net.microwww.rurl.domain.Webapp;
import net.microwww.rurl.domain.Webappurl;
import net.microwww.rurl.help.DataFactory;
import net.microwww.rurl.rmi.NoRightException;
import net.microwww.rurl.rmi.RurlRightService;
import net.microwww.rurl.rmi.dto.App;
import net.microwww.rurl.rmi.dto.Employe;
import net.microwww.rurl.rmi.dto.RightURL;
import net.microwww.rurl.service.UserinfoService;
import net.microwww.rurl.service.WebappService;
import org.springframework.beans.BeanUtils;

/**
 *
 * @author changshu.li
 */
public class HessianRightServiceImp implements RurlRightService {

    //@Resource
    //UserinfoService userinfoService;
    UserinfoService userinfoService = DataFactory.getUserinfoService();

    WebappService webappService = BeanContextFactory.get().getBean(WebappService.class);

    public Map<String, Object> login(String account, String password) throws NoRightException {
        Employe em = userinfoService.login(account, password);
        return (Map<String, Object>) JSONObject.toJSON(em);
    }

    @Override
    public boolean hasLoginRight(String appname, String account) {
        Webapp app = Webapp.getByAppName(appname);
        if (app == null) {
            throw new RuntimeException("请确认服务器已经配置了应用 : " + appname);
        }
        return app.hasLoginRight(account);
    }

    @Override
    public Map<String, Object>[] listAccountUrlRight(String appname, String account) {
        Webapp app = Webapp.getByAppName(appname);
        List<Map<String, Object>> list = new ArrayList();
        List<Webappurl> accs = app.listAccountRight(account);
        for (Webappurl url : accs) {
            url.setWebapp(app);
            RightURL rt = new RightURL();
            BeanUtils.copyProperties(url, rt);
            list.add((JSONObject) JSONObject.toJSON(rt));
        }
        return list.toArray(new Map[list.size()]);
    }

    @Override
    public Map<String, Object>[] listUrlRight(String appname) {
        Webapp app = Webapp.getByAppName(appname);
        List<Map<String, Object>> list = new ArrayList();
        List<Webappurl> accs = Webappurl.listWebappurl(app);
        for (Webappurl url : accs) {
            url.setWebapp(app);
            RightURL rt = new RightURL();
            BeanUtils.copyProperties(url, rt);
            list.add((JSONObject) JSONObject.toJSON(rt));
        }
        return list.toArray(new Map[list.size()]);
    }

    @Override
    public Map<String, Object> getApplication(String appname) {
        Webapp app = Webapp.getByAppName(appname);
        if (app == null) {
            throw new RuntimeException("not find app for appname :" + appname);
        }
        App da = new App();
        BeanUtils.copyProperties(app, da);
        return (JSONObject) JSONObject.toJSON(da);
    }

    @Override
    public Map<String, Object>[] saveURL(String appname, String[] urllist) {
        Webapp app = Webapp.getByAppName(appname);
        List<Map<String, Object>> list = new ArrayList();
        for (String path : urllist) {
            Map<String, Object> url = this.save4url(appname, path);
            list.add(url);
        }
        return list.toArray(new Map[list.size()]);
    }

    public static String getName(String path) {
        return new File(path).getName();
    }

    public Map<String, Object> save4url(String appname, String path) {
        Webapp app = Webapp.getByAppName(appname);
        List<Webappurl> urls = Webappurl.listByPath(path);
        Webappurl url = null;
        if (urls != null && !urls.isEmpty()) {
            url = urls.get(0);
        } else {
            url = new Webappurl();
            url.setName(getName(path));
            url.setWebappPath(path);
            url.setWebapp(app);
            url.setDiscrption(" auto collection !" + path);
            url = webappService.saveWebappurl(app, url, null);
        }
        RightURL rt = new RightURL();
        BeanUtils.copyProperties(url, rt);
        return (JSONObject) JSONObject.toJSON(rt);
    }
}
