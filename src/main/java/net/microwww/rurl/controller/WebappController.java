/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.microwww.rurl.controller;

import java.util.List;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import net.microwww.rurl.domain.Groups;
import net.microwww.rurl.domain.Webapp;
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
@RequestMapping("/app")
public class WebappController {

    private static Log logger = LogFactory.getLog(WebappController.class);
    @Resource
    WebappService webappService;
    @Resource
    GroupsService groupsService;

    @RequestMapping({"", "/", "/index.html"})
    public String indexWebapps(HttpServletRequest request) {
        listWebapps(request);
        return "/webapp/index.jsp";
    }

    @RequestMapping({"/list.html"})
    public String listWebapps(HttpServletRequest request) {
        List<Webapp> list = Webapp.listWebapps();
        request.setAttribute("list", list);
        return "/webapp/list.jsp";
    }

    @RequestMapping({"/list.ajax"})
    @ResponseBody
    public AjaxMessage listWebappsAjax(HttpServletRequest request) {
        AjaxMessage msg = new AjaxMessage();
        List<Webapp> list = Webapp.listWebapps();
        msg.setData(list);
        return msg;
    }

    @RequestMapping("/poplist.html")
    public String poplistWebapps(HttpServletRequest request) {

        List<Webapp> list = Webapp.listWebapps();
        request.setAttribute("webapplist", list);

        return "/webapp/poplist.jsp";
    }

    @RequestMapping("/edit.html")
    public String andWebapp(HttpServletRequest request) {
        return editWebapp(request, null);
    }

    @RequestMapping("/edit/{appid}.html")
    public String editWebapp(HttpServletRequest request, @PathVariable Integer appid) {
        if (appid != null && appid > 0) {
            Webapp gp = Webapp.getById(appid);
            request.setAttribute("webapp", gp);
        }
        return "/webapp/edit.jsp";
    }

    @RequestMapping("/detail/{appid}.html")
    public String detailWebapp(HttpServletRequest request, @PathVariable Integer appid) {
        if (appid != null && appid > 0) {
            Webapp gp = Webapp.getById(appid);
            request.setAttribute("webapp", gp);
        }
        return "/webapp/detail.jsp";
    }

    @RequestMapping("/edit.ajax")
    @ResponseBody
    public AjaxMessage saveWebapp(HttpServletRequest request, Webapp webapp) {
        AjaxMessage msg = new AjaxMessage();
        try {
            String loginGroupId = request.getParameter("loginGroupId");
            if(!StringUtils.isNumeric(loginGroupId)){
                loginGroupId = "0";
            }
            Groups gp = Groups.getById(Integer.valueOf(loginGroupId));
            webapp.setLoginGroup(gp);
            webappService.save(webapp);
            msg.setData(webapp);
            msg.addMessage("保存成功");
        } catch (Exception ex) {
            logger.error("保存APP出错", ex);
            ex.printStackTrace();
            msg.addError(ex.getMessage());
        }
        return msg;
    }

    @RequestMapping("/addgroups.ajax")
    @ResponseBody
    public AjaxMessage addGroupsWebapp(HttpServletRequest request,
            @RequestParam Integer webappid, @RequestParam String groupids) {
        AjaxMessage msg = new AjaxMessage();
        try {
            String[] gids = groupids.split(",");
            for (String gid : gids) {
                int id = Integer.valueOf(gid);
                Groups gp = Groups.getById(id);
                groupsService.addWebappurl(gp, null);
            }
            msg.addMessage("保存成功");
        } catch (Exception ex) {
            logger.error("保存APP出错", ex);
            msg.addError(ex.getMessage());
        }
        return msg;
    }
}
