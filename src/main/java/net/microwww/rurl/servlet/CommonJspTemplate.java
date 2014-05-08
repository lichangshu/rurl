/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.microwww.rurl.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.HttpJspPage;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *
 * @author changshu.li
 */
public class CommonJspTemplate extends HttpServlet implements HttpJspPage {

    private static final long serialVersionUID = 1L;
    Log logger = LogFactory.getLog(CommonJspTemplate.class);

    @Override
    public void init() throws ServletException {
        super.init();
        this.jspInit();
    }

    @Override
    public void _jspService(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.debug("_jspService");
    }

    @Override
    public void jspInit() {
        logger.debug("jspInit");
        _jspInit();
    }

    @Override
    public void jspDestroy() {
        logger.debug("jspDestroy");
        _jspDestroy();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this._jspService(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req, resp);
    }

    @Override
    public void destroy() {
        this.jspDestroy();
        super.destroy();
    }

    public void _jspInit() {
    }

    public void _jspDestroy() {
    }
}
