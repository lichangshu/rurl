/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.microwww.rurl.controller;

import java.io.IOException;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.microwww.rurl.domain.Account;
import net.microwww.rurl.rmi.dto.Department;
import net.microwww.rurl.rmi.dto.Employe;
import net.microwww.rurl.domain.Webapp;
import net.microwww.rurl.help.DataFactory;
import net.microwww.rurl.service.UserinfoService;
import net.microwww.util.AjaxMessage;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author changshu.li
 */
@Controller
@RequestMapping("/user")
public class UserinfoController {

    //@Resource
    //private UserinfoService userinfoService;
    private static final UserinfoService userinfoService = DataFactory.getUserinfoService();

    @RequestMapping("/deparment.html")
    public String listdepartment(HttpServletRequest req) {
        List<Department> list = userinfoService.findAllDepartment();
        req.setAttribute("list", list);
        return "/user/deparment.jsp";
    }

    @RequestMapping("/select.html")
    public String selectdepartment(HttpServletRequest req) {
        listdepartment(req);
        return "/user/select.jsp";
    }

    @RequestMapping("/department.ajax")
    @ResponseBody
    public AjaxMessage getDepartment(HttpServletRequest req) {
        List<Department> list = userinfoService.findAllDepartment();
        AjaxMessage msg = new AjaxMessage();
        msg.setData(list);
        return msg;
    }

    @RequestMapping("/get_account.ajax")
    @ResponseBody
    public AjaxMessage getAccount(@RequestParam String account) {
        AjaxMessage msg = new AjaxMessage();
        msg.setData(userinfoService.findEmployeByAccount("*" + account + "*"));
        return msg;
    }

    @RequestMapping("/ladpuser.ajax")
    @ResponseBody
    public AjaxMessage listLadpUser(@RequestParam String did, HttpServletResponse response) throws IOException {
        List<Employe> list = userinfoService.findEmployeByDepartmentId(did);
        AjaxMessage msg = new AjaxMessage();
        msg.setData(list);
        return msg;
    }

    @RequestMapping("/ladpuser_login_apps.ajax")
    @ResponseBody
    public AjaxMessage listAccountLoginApps(@RequestParam String account) {
        AjaxMessage msg = new AjaxMessage();
        List<Webapp> list = Account.listAccountLoginApps(account);
        msg.setData(list);
        return msg;
    }
}
