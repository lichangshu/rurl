/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.microwww.rurl.domain;

import com.alibaba.fastjson.JSONObject;
import javax.annotation.Resource;
import net.microwww.common.spring.SpringDispatcherServlet;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 *
 * @author changshu.li
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"/config/applicationcontext.xml"})
public class AccountTest {

    @Resource
    ApplicationContext applicationContext;

    /**
     * Test of getById method, of class Account.
     */
    @Test
    public void testGetById() {
        System.out.println("getById");
        SpringDispatcherServlet.set(applicationContext);
        int id = 96;
        Account result = Account.getById(id);
        assertNotNull(result);
        
        Webappurl wp = Webappurl.getById(1);
        try {
        JSONObject.toJSON(wp);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
