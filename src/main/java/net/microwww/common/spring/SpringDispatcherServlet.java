/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.microwww.common.spring;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

/**
 *
 * @author changshu.li
 */
public class SpringDispatcherServlet extends DispatcherServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void initStrategies(ApplicationContext context) {
        super.initStrategies(context);
        applicationContext = context;// FrameworkServlet 'mvc': initialization completed !
    }

    public SpringDispatcherServlet() {
    }

    public SpringDispatcherServlet(WebApplicationContext webApplicationContext) {
        super(webApplicationContext);
    }

    private static ApplicationContext applicationContext;

    public static ApplicationContext get() {
        if (applicationContext == null) {
            throw new RuntimeException("ERROR, Not init ApplicationContext!");
        }
        return applicationContext;
    }

    public static void set(ApplicationContext applicationContext) {
        SpringDispatcherServlet.applicationContext = applicationContext;
    }
}
