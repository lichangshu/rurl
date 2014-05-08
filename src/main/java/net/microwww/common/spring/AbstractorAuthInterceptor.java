package net.microwww.common.spring;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

public abstract class AbstractorAuthInterceptor extends HandlerInterceptorAdapter {

    private String skip;
    private List<Pattern> skiplist = new ArrayList<Pattern>();
    private Set<String> skipset = new HashSet();

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String url = request.getRequestURI();
        String contextPath = request.getContextPath();
        url = url.replace(contextPath, "");
        if (!isSkipUrl(url)) {
            boolean ok = isSuccess(request);
            if (!ok) {
                String rd = sendRedirect(request);
                if (rd != null) {
                    if (!rd.startsWith("http")) {
                        rd = contextPath + (rd.startsWith("/") ? rd : ("/" + rd));
                    }
                    response.sendRedirect(rd);
                } else {
                    response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
                }
            }
            return ok;
        }

        return true;
    }

    public abstract boolean isSuccess(HttpServletRequest request);

    /**
     * 不跳转返回 null
     */
    public String sendRedirect(HttpServletRequest request) {
        return null;
    }

    /**
     * 页面跳转
     *
     * @throws java.io.IOException
     * @throws ServletException
     */
    public void forward(HttpServletRequest request, HttpServletResponse response, String path) throws ServletException, IOException {
        request.getRequestDispatcher(path).forward(request, response);
    }

    public boolean isSkipUrl(String url) {
        if (url == null) {
            return false;
        }
        url = url.trim();
        boolean ok = this.skipset.contains(url);
        if (!ok) {
            ok = this.listSkipUrl(url);
        }
        return ok;
    }

    private boolean listSkipUrl(String url) {
        for (Pattern pt : skiplist) {
            if (pt.matcher(url).matches()) {
                return true;
            }
        }
        return false;
    }

    public String getSkip() {
        return skip;
    }

    public void setSkip(String skip) {
        this.skip = skip;
        this.setSkiplist(this.skip.split(","));
        this.setSkipset(this.skip.split(","));
    }

    public void setSkiplist(String[] str) {
        this.skiplist.clear();
        for (String s : str) {
            s = s.trim();
            if (s.indexOf('*') >= 0) {
                s = PT_POINT.matcher(s).replaceAll("\\\\.");
                if (s.startsWith("*") || s.endsWith("*")) {
                    s = PT_STAR.matcher(s).replaceAll(".*");
                } else {
                    s = PT_STAR.matcher(s).replaceAll("[\\\\w.-]*");
                }
                this.skiplist.add(Pattern.compile(s));
            } else {
                this.skipset.add(s);
            }
        }
    }
    private static final Pattern PT_STAR = Pattern.compile("\\*");
    private static final Pattern PT_POINT = Pattern.compile("\\.");

    public void setSkipset(String[] str) {
        this.skipset.clear();
        for (String s : str) {
            if (s != null && s.indexOf('*') < 0) {
                this.skipset.add(s.trim());
            }
        }
    }
}
