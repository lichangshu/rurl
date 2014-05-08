/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.microwww.common;

import net.microwww.common.spring.SpringBeanContent;
import net.microwww.common.spring.SpringDispatcherServlet;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *
 * @author changshu.li
 */
public class BeanContextFactory {

    private static Log logger = LogFactory.getLog(BeanContextFactory.class);
    private static BeanContent beanContent;

    public static BeanContent get() {
        if (beanContent == null) {
            initBeanContent();
        }
        return beanContent;
    }

    private static synchronized BeanContent initBeanContent() {
        if (beanContent == null) {
            beanContent = new SpringBeanContent(SpringDispatcherServlet.get());
        }
        return beanContent;
    }

    public static void set(BeanContent context) {
        logger.debug(" NOTE: Init global data , TO remove it !");
        beanContent = context;
    }

    public static void remove() {
        logger.debug(" remove SupportContext !");
        beanContent = null;
    }
}
