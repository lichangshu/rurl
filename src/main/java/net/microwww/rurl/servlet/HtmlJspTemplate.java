/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.microwww.rurl.servlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author changshu.li
 */
public class HtmlJspTemplate extends AbstractJspTemplate {

    @Override
    protected String loadHead(HttpServletRequest req, HttpServletResponse res) {
        return "/WEB-INF/templates/_head.jsp";
    }

    @Override
    protected String loadFoot(HttpServletRequest req, HttpServletResponse res) {
        return "/WEB-INF/templates/_foot.jsp";
    }

}
