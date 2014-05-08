package net.microwww.rurl.controller;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import net.microwww.rurl.domain.Webapp;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/easy")
public class EasyController {

    @RequestMapping({"", "/", "/index.html"})
    public String listGroups(HttpServletRequest request) {

        List<Webapp> list = Webapp.listWebapps();
        request.setAttribute("applist", list);

        return "/easy/index.jsp";
    }

    @RequestMapping("/list.html")
    public String listDeparment(HttpServletRequest request) {
        return "/ldap/deparment.jsp";
    }
}
