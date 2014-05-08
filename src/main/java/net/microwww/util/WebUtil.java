/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.microwww.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * @author changshu.li
 */
public class WebUtil {

    private WebUtil() {
    }

    public static String getIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

    public static Cookie createCookie(String key, String value) {
        Cookie ck = new Cookie(key, value);
        return ck;
    }

    public static Cookie createCookie(String key, String value, String path) {
        Cookie ck = new Cookie(key, value);
        ck.setPath(path);
        return ck;
    }

    public static Cookie createCookie(String key, String v, int age, String path) throws Exception {
        Cookie ck = new Cookie(key, v);
        ck.setMaxAge(age);
        ck.setPath(path);
        return ck;
    }

    public static String getCookie(HttpServletRequest req, String key) {
        Cookie[] cks = req.getCookies();
        if (cks == null) {
            return null;
        }
        for (Cookie ck : cks) {
            if (ck.getName().equals(key)) {
                return ck.getValue();
            }
        }
        return null;
    }

    public static Cookie getDoaminCookie(HttpServletRequest req, String key) {
        Cookie[] cks = req.getCookies();
        if (cks == null) {
            return null;
        }
        for (Cookie ck : cks) {
            if (ck.getName().equals(key)) {
                return ck;
            }
        }
        return null;
    }

    public static String getSessionId(HttpServletRequest request) {
        return request.getSession(true).getId();
    }

    public static String loginReback(HttpServletRequest hsr) {
        return loginReback(hsr, "UTF-8");
    }

    public static String loginFailBack(HttpServletRequest request, String encode) {
        String root = "http://" + request.getHeader("Host") + request.getContextPath();
        String rf = request.getHeader("referer");
        if (rf == null || rf.contains("/user/login")) {
            rf = root;
        }
        return loginBack(request, rf, encode);
    }

    public static String loginReback(HttpServletRequest hsr, String encode) {
        String qr = hsr.getQueryString();
        String rtback = hsr.getRequestURI();
        if (qr != null) {
            rtback += "?" + hsr.getQueryString();
        }
        return loginBack(hsr, rtback, encode);
    }

    public static String loginBack(HttpServletRequest hsr, String rtback, String encode) {
        try {
            return "http://" + hsr.getHeader("Host") + hsr.getContextPath() + "/user/login.html?rback=" + URLEncoder.encode(rtback, encode);
        } catch (UnsupportedEncodingException ex) {
            throw new RuntimeException(ex);
        }
    }
}
