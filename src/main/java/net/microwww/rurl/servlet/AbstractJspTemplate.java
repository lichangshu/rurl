/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.microwww.rurl.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author changshu.li
 */
public abstract class AbstractJspTemplate extends CommonJspTemplate {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/include/env.jsp").include(req, resp);

        String head = loadHead(req, resp);
        String foot = loadFoot(req, resp);

        ContentResponseWrapper res = new ContentResponseWrapper(resp);
        this._jspService(req, res);

        writeHead(head, req, resp);
        writeBody(res);
        writeFoot(foot, req, resp);
    }

    abstract protected String loadHead(HttpServletRequest req, HttpServletResponse res);

    abstract protected String loadFoot(HttpServletRequest req, HttpServletResponse res);

    protected void writeHead(String head, HttpServletRequest req, HttpServletResponse response) throws IOException, ServletException {
        if (head != null) {
            req.getRequestDispatcher(head).include(req, response);
        }
    }

    protected void writeFoot(String foot, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        if (foot != null) {
            request.getRequestDispatcher(foot).include(request, response);
        }
    }

    protected void writeBody(ContentResponseWrapper wrp) throws IOException {
        ServletResponse response = wrp.getResponse();
        try {
            String stringResult = wrp.getStringResult();
            response.getWriter().write(stringResult);
        } catch (IllegalStateException e) {
            logger.error("模板替换出错!", e);
            response.getOutputStream().write(wrp.getResult());
        }
    }
}
