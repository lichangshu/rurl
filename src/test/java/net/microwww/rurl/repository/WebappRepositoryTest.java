/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.microwww.rurl.repository;

import java.util.List;
import javax.annotation.Resource;
import net.microwww.rurl.domain.Webapp;
import net.microwww.rurl.domain.Webappurl;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 *
 * @author changshu.li
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"/config/applicationcontext.xml"})
public class WebappRepositoryTest {

    @Resource
    private WebappRepository instance;

    /**
     * Test of getById method, of class WebappRepository.
     */
    @Test
    public void testGetById() {
        System.out.println("getById");
        int id = 23;
        Webapp result = instance.getById(id);
        assertNotNull(result);
    }

    /**
     * Test of findAll method, of class WebappRepository.
     */
    @Test
    public void testFindAll() {
        System.out.println("findAll");
        List<Webapp> result = instance.findAll();
        assertNotNull(result);
    }

    /**
     * Test of getByAppName method, of class WebappRepository.
     */
    @Test
    public void testGetByAppName() {
        System.out.println("getByAppName");
        String appname = "rurl";
        Webapp result = instance.getByAppName(appname);
        assertNotNull(result);
    }

    /**
     * Test of findAccountRightByAccountId method, of class WebappRepository.
     */
    @Test
    public void testFindAccountRightByAccountId() {
        System.out.println("findAccountRightByAccountId");
        int id = 96;
        List<Webappurl> result = instance.findRightByAccountId(id);
        assertFalse(result.isEmpty());
    }

    /**
     * Test of findRightByAccountIdWebappId method, of class WebappRepository.
     */
    @Test
    public void testFindRightByAccountIdWebappId() {
        System.out.println("findAccountRightByAccountId");
        int id = 96;
        List<Webappurl> result = instance.findRightByAccountIdWebappId(id, 23);
        assertFalse(result.isEmpty());
    }

    /**
     * Test of findRightByAccountId method, of class WebappRepository.
     */
    @Test
    public void testFindRightByAccountId() {
        System.out.println("findRightByAccountId");
        int id = 96;
        List<Webappurl> expResult = null;
        List<Webappurl> result = instance.findRightByAccountId(id);
        assertFalse(result.isEmpty());
        //select wg.webappurl from AccountGroups ag, WebappurlGroups wg where ag.groupsId = wg.groupsId and ag.account.id = ?1
    }
}
