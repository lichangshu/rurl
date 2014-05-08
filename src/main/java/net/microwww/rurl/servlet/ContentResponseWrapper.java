/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.microwww.rurl.servlet;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

public class ContentResponseWrapper extends HttpServletResponseWrapper {

    private ByteArrayOutputStream out;
    private PrintWriter writer;

    public ContentResponseWrapper(HttpServletResponse response) {
        super(response);
        out = new ByteArrayOutputStream();
        writer = new PrintWriter(out);
    }

    @Override
    public ServletOutputStream getOutputStream() throws IOException {
        return new ServletOutputStream() {
            @Override
            public void write(int b) throws IOException {
                out.write(b);
            }
        };
    }

    @Override
    public PrintWriter getWriter() throws IOException {
        return writer;
    }

    public byte[] getResult() {
        return out.toByteArray();
    }

    public String getStringResult() throws UnsupportedEncodingException {
        writer.flush();
        return new String(this.getResult());
    }
}
