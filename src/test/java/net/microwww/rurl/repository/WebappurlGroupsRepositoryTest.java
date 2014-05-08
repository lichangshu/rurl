/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.microwww.rurl.repository;

import java.util.List;
import javax.annotation.Resource;
import net.microwww.rurl.domain.WebappurlGroups;
import net.microwww.rurl.domain.Webapp;
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
@ContextConfiguration({"/config/applicateioncontext.xml"})
public class WebappurlGroupsRepositoryTest {

    @Resource
    WebappurlGroupsRepository instance;

    /**
     * Test of getById method, of class WebappurlGroupsRepository.
     */
    @Test
    public void testGetById() {
        System.out.println("getById");
        int id = 1;
        WebappurlGroups result = instance.getById(id);
        assertNotNull(result);
    }

    /**
     * Test of findAll method, of class WebappurlGroupsRepository.
     */
    @Test
    public void testFindAll() {
        System.out.println("findAll");
        List<WebappurlGroups> result = instance.findAll();
        assertTrue(result.size() > 0);
    }

    /**
     * Test of findByGroupsId method, of class WebappurlGroupsRepository.
     */
    @Test
    public void testFindByGroupsId() {
        System.out.println("findByGroupsId");
        int id = 7;
        int expResult = 62;
        List<WebappurlGroups> result = instance.findByGroupsId(id);
        assertEquals(expResult, result.size());
    }

    /**
     * Test of findWebappByGroupsId method, of class WebappurlGroupsRepository.
     */
    @Test
    public void testFindWebappByGroupsId() {
        System.out.println("findWebappByGroupsId");
        int id = 7;
        int expResult = 3;
        List<Webapp> result = instance.findWebappByGroupsId(id);
        assertEquals(expResult, result.size());
    }

}
