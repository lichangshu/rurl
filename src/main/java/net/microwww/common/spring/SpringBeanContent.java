/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.microwww.common.spring;

import net.microwww.common.BeanContent;
import org.springframework.context.ApplicationContext;

/**
 *
 * @author changshu.li
 */
public class SpringBeanContent implements BeanContent {

    private ApplicationContext applicationContext;

    public SpringBeanContent(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Override
    public <T> T getBean(Class<T> t) {
        return applicationContext.getBean(t);
    }

    @Override
    public Object getBean(String bean) {
        return applicationContext.getBean(bean);
    }
}
