/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.microwww.common;

/**
 *
 * @author changshu.li
 */
public interface BeanContent {

    public <T extends Object> T getBean(Class<T> t);

    public Object getBean(String bean);
}
