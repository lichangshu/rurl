/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.microwww.rurl.controller;

import java.util.List;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import net.microwww.rurl.domain.Account;
import net.microwww.rurl.domain.AccountGroups;
import net.microwww.rurl.rmi.dto.Employe;
import net.microwww.rurl.domain.Groups;
import net.microwww.rurl.domain.WebappurlGroups;
import net.microwww.rurl.domain.Webapp;
import net.microwww.rurl.service.GroupsService;
import net.microwww.util.AjaxMessage;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author changshu.li
 */
@Controller
@RequestMapping("/groups")
public class GroupsController {

    private static final Log logger = LogFactory.getLog(GroupsController.class);
    @Resource
    GroupsService groupsService;

    @RequestMapping({"/", "/index.html"})
    public String listGroups(HttpServletRequest request) {

        List<Groups> list = Groups.listGroups();
        request.setAttribute("list", list);

        return "/groups/list.jsp";
    }

    @RequestMapping("/poplist.html")
    public String poplistGroups(HttpServletRequest request) {

        List<Groups> list = Groups.listGroups();
        request.setAttribute("list", list);

        return "/groups/poplist.jsp";
    }

    @RequestMapping("/list.ajax")
    @ResponseBody
    public AjaxMessage listGroupsAjax(HttpServletRequest request) {
        AjaxMessage msg = new AjaxMessage();
        List<Groups> list = Groups.listGroups();
        msg.setData(list);
        return msg;
    }

    @RequestMapping("/appglist.ajax")
    @ResponseBody
    public AjaxMessage listGroupsApp(HttpServletRequest request, @RequestParam Integer appid) {
        Webapp app = Webapp.getById(appid);
        List<Groups> list = WebappurlGroups.listGroups(app);
        AjaxMessage msg = new AjaxMessage();
        msg.setData(list);
        return msg;
    }

    @RequestMapping("/edit.html")
    public String editGroup(HttpServletRequest request, @RequestParam(required = false) Integer id) {
        if (id != null && id > 0) {
            Groups gp = Groups.getById(id);
            request.setAttribute("groups", gp);
        }
        return "/groups/edit.jsp";
    }

    @RequestMapping("/detail.html")
    public String detailGroup(HttpServletRequest request, @RequestParam Integer id) {
        if (id != null && id > 0) {
            Groups gp = Groups.getById(id);
            request.setAttribute("groups", gp);
        }
        return "/groups/detail.jsp";
    }

    @RequestMapping("/edit.ajax")
    @ResponseBody
    public AjaxMessage saveGroup(HttpServletRequest request, Groups groups) {
        AjaxMessage msg = new AjaxMessage();
        try {
            groupsService.save(groups);
            msg.setData(groups);
            msg.addMessage("保存成功");
        } catch (Exception ex) {
            logger.error("保存 Groups 失败", ex);
            ex.printStackTrace();
            msg.addError(ex.getMessage());
        }
        return msg;
    }

    @RequestMapping("/list_account.ajax")
    @ResponseBody
    public AjaxMessage listAccount4Group(@RequestParam Integer groupsid) {
        AjaxMessage msg = new AjaxMessage();
        try {
            Groups gp = Groups.getById(groupsid);
            List<Account> ags = AccountGroups.listAccountByGroup(gp);
            msg.setData(ags);
        } catch (Exception ex) {
            msg.addError(ex.getMessage());
        }
        return msg;
    }

    @RequestMapping("/add_account.ajax")
    @ResponseBody
    public AjaxMessage addAccount2Group(HttpServletRequest request, Employe emp, @RequestParam Integer groupsid) {
        AjaxMessage msg = new AjaxMessage();
        Account acc = Account.getByAccount(emp.getAccount());
        if (acc == null) {
            acc = new Account();
        }
        acc.setAccount(emp.getAccount());
        acc.setName(emp.getName());
        try {
            Groups grp = Groups.getById(groupsid);
            groupsService.addAccount(grp, acc);
            msg.addMessage("保存成功");
        } catch (Exception ex) {
            logger.error("组中添加用户失败", ex);
            ex.printStackTrace();
            msg.addError(ex.getMessage());
        }
        return msg;
    }

    @RequestMapping("/del_account.ajax")
    @ResponseBody
    public AjaxMessage delAccount2Group(HttpServletRequest request,
            @RequestParam Integer groupsid, @RequestParam String account) {
        AjaxMessage msg = new AjaxMessage();
        try {
            Groups gp = Groups.getById(groupsid);
            Account acc = Account.getByAccount(account);
            if (gp != null && acc != null) {
                int count = groupsService.deleteGroupAccount(gp, acc);
                msg.setData(count);
            }
            msg.addMessage("删除成功");
        } catch (Exception ex) {
            msg.addError(ex.getMessage());
        }
        return msg;
    }
}
