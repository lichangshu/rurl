/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.microwww.rurl.controller;

import com.alibaba.fastjson.JSONObject;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.microwww.rurl.domain.Groups;
import net.microwww.rurl.domain.Webapp;
import net.microwww.rurl.domain.Webappurl;
import net.microwww.rurl.service.GroupsService;
import net.microwww.rurl.service.WebappService;
import net.microwww.util.AjaxMessage;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author changshu.li
 */
@Controller
@RequestMapping("/url")
public class WebappurlController {

    private static Log logger = LogFactory.getLog(WebappurlController.class);
    @Resource
    WebappService webappService;
    @Resource
    GroupsService groupsService;

    @RequestMapping({"/list.html"})
    public String listWebappurl(HttpServletRequest request) {

        List<Webappurl> list = Webappurl.listWebappurl();
        request.setAttribute("list", list);

        return "/url/list.jsp";
    }

    @RequestMapping({"/poplist.html"})
    public String poplistWebappurl(HttpServletRequest request, @RequestParam Integer appid) {

        Webapp app = Webapp.getById(appid);
        List<Webappurl> list = Webappurl.listWebappurl(app);
        request.setAttribute("list", list);

        return "/url/poplist.jsp";
    }

    @RequestMapping({"/list4app.ajax"})
    @ResponseBody
    public AjaxMessage list4app(HttpServletRequest request, @RequestParam Integer appid) {
        AjaxMessage msg = new AjaxMessage();
        Webapp app = Webapp.getById(appid);
        List<Webappurl> list = Webappurl.listWebappurl(app);
        for (Webappurl url : list) {
            url.setWebapp(app);
        }
        msg.setData(list);
        return msg;
    }

    @RequestMapping({"/list4groups.ajax"})
    @ResponseBody
    public AjaxMessage list4groups(HttpServletRequest request, @RequestParam Integer groupsid) {
        AjaxMessage msg = new AjaxMessage();
        Groups g = Groups.getById(groupsid);
        List<Webappurl> list = Webappurl.listWebappurl(g);
        msg.setData(list);
        return msg;
    }

    @RequestMapping({"", "/", "/index.html"})
    public String listWebappAndUrl(HttpServletRequest request) {

        List<Webapp> list = Webapp.listWebapps();
        request.setAttribute("list", list);

        return "/url/index.jsp";
    }

    @RequestMapping("/{groupid}_{webappid}.html")
    public String listWebappAndGroupsUrl(@PathVariable Integer groupid,
            @PathVariable Integer webappid, HttpServletRequest request) {

        Groups g = Groups.getById(groupid);
        Webapp app = Webapp.getById(webappid);
        List<Webappurl> rightlist = Webappurl.listWebappurl(g, app);
        List<Webappurl> appurllist = Webappurl.listWebappurl(app);
        request.setAttribute("appurllist", appurllist);
        request.setAttribute("rightlist", rightlist);
        request.setAttribute("groups", g);
        request.setAttribute("webapp", app);

        return "/url/app_group_url.jsp";
    }

    @RequestMapping("/list.ajax")
    public void listWebappurl(HttpServletResponse res, @RequestParam Integer appid) throws IOException {
        AjaxMessage msg = new AjaxMessage();
        Webapp webapp = Webapp.getById(appid);
        List<Webappurl> list = Webappurl.listWebappurl(webapp);
        msg.setData(list);
        res.getWriter().print(JSONObject.toJSONString(msg));
    }

    @RequestMapping("/add-{id}.html")
    public String addWebappurl(HttpServletRequest request, @PathVariable Integer id) {
        Webapp webapp = Webapp.getById(id);
        if (webapp != null) {
            request.setAttribute("webapp", webapp);
            return "/url/edit.jsp";
        }
        return null;
    }

    @RequestMapping("/dele.ajax")
    @ResponseBody
    public AjaxMessage delWebappurl(HttpServletRequest request, Integer id) {
        AjaxMessage msg = new AjaxMessage();
        Webappurl url = Webappurl.getById(id);
        if (url != null) {
            webappService.deleUrl(url);
        } else {
            msg.addError("找不到 该URL");
        }
        return msg;
    }

    @RequestMapping("/edit.html")
    public String editWebappurl(HttpServletRequest request, @RequestParam Integer id) {
        if (id != null && id > 0) {
            Webappurl url = Webappurl.getById(id);
            request.setAttribute("webappurl", url);
            request.setAttribute("webapp", url.getWebapp());
            return "/url/edit.jsp";
        }
        return null;
    }

    @RequestMapping("/edit.ajax")
    @ResponseBody
    public AjaxMessage saveWebappurl(HttpServletRequest request, Webappurl url) {
        AjaxMessage msg = new AjaxMessage();
        try {
            String wid = request.getParameter("webappId");
            String pid = request.getParameter("parentId");
            if(!StringUtils.isNumeric(wid)){
                msg.addError("webappId 应该为数字");
            }
            if(!StringUtils.isNumeric(pid)){
                pid = "0";
            }
            if(!msg.hasError()){
                Webapp app = Webapp.getById(Integer.valueOf(wid));
                Webappurl purl = Webappurl.getById(Integer.valueOf(pid));
                if (app != null) {
                    webappService.saveWebappurl(app, url, purl);
                    msg.setData(url);
                    msg.addMessage("保存成功");
                } else {
                    msg.addError("找不到对应的APP appid：" + wid);
                }
            }
        } catch (Exception ex) {
            logger.error("保存APP出错", ex);
            ex.printStackTrace();
            msg.addError(ex.getMessage());
        }
        return msg;
    }

    @RequestMapping("/addgroupurl.ajax")
    @ResponseBody
    public AjaxMessage saveWebappurlGroups(@RequestParam Integer groupsid, @RequestParam String appurls) {
        AjaxMessage msg = new AjaxMessage();
        Groups g = Groups.getById(groupsid);
        try {
            String[] ids = appurls.split(",");
            List list = new ArrayList();
            for (String sid : ids) {
                int id = Integer.valueOf(sid);
                Webappurl url = Webappurl.getById(id);
                list.add(groupsService.addWebappurl(g, url));
            }
            msg.setData(list);
        } catch (Exception ex) {
            logger.error("保存 addgroupurl 出错", ex);
            ex.printStackTrace();
            msg.addError(ex.getMessage());
        }
        return msg;
    }

    @RequestMapping("/delegroupurl.ajax")
    @ResponseBody
    public AjaxMessage deleWebappurlGroups(@RequestParam Integer groupsid, @RequestParam String appurls) {
        AjaxMessage msg = new AjaxMessage();
        Groups g = Groups.getById(groupsid);
        try {
            String[] ids = appurls.split(",");
            List<Webappurl> list = new ArrayList();
            for (String sid : ids) {
                int id = Integer.valueOf(sid);
                Webappurl url = Webappurl.getById(id);
                if (url != null) {
                    list.add(url);
                }
            }
            groupsService.deleWebappurlGroups(g, list);
        } catch (Exception ex) {
            logger.error("删除 deleWebappurlGroups 出错", ex);
            msg.addError(ex.getMessage());
        }
        return msg;
    }
}
