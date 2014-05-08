package net.microwww.rurl.controller;

import javax.servlet.http.HttpServletRequest;
import net.microwww.rurl.domain.Groups;
import net.microwww.rurl.domain.Webapp;
import net.microwww.rurl.rmi.NoRightException;
import net.microwww.rurl.rmi.dto.Employe;
import net.microwww.rurl.rmi.imp.RurlFactory;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping
public class RootController {

    private static Log logger = LogFactory.getLog(RootController.class);

    @RequestMapping({"/login.html"})
    public String login(HttpServletRequest request) {
        if ("POST".equals(request.getMethod())) {
            String account = request.getParameter("account");
            String password = request.getParameter("password");
            try {
                Employe em = RurlFactory.getRurlWrap().login(account, password);
                request.getSession(true).setAttribute("LOGIN_EMPLOYE", em);
                logger.info("登录成功:" + account);
                return "redirect:/";
            } catch (NoRightException ex) {
                logger.warn("登录失败:" + account + " " + ex.getMessage());
                request.setAttribute("msg", ex.getMessage());
            }
        }
        return "/login.jsp";
    }

    @RequestMapping(value = "/logout.html", method = RequestMethod.GET)
    public String logout(HttpServletRequest request) {
        login(request);
        Employe em = (Employe) request.getSession(true).getAttribute("LOGIN_EMPLOYE");
        if (em != null) {
            request.getSession(true).removeAttribute("LOGIN_EMPLOYE");
            logger.info("成功退出：" + em.getAccount());
        }
        request.setAttribute("msg", "成功退出");
        return "/login.jsp";
    }

    @RequestMapping({"/", "/index.html"})
    public String home(HttpServletRequest request) {
        request.setAttribute("groupslist", Groups.listGroups());
        request.setAttribute("webapplist", Webapp.listWebapps());
        return "/home.jsp";
    }
}
