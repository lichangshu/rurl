/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.microwww.rurl.repository;

import java.util.List;
import javax.annotation.Resource;
import net.microwww.rurl.domain.AccountGroups;
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
public class AccountGroupsRepositoryTest {

    @Resource
    AccountGroupsRepository instance;

    /**
     * Test of getById method, of class AccountGroupsRepository.
     */
    @Test
    public void testGetById() {
        System.out.println("getById");
        int id = 14;
        AccountGroups result = instance.getById(id);
        assertNotNull(result);
    }

    /**
     * Test of findAll method, of class AccountGroupsRepository.
     */
    @Test
    public void testFindAll() {
        System.out.println("findAll");
        List<AccountGroups> result = instance.findAll();
        assertNotNull(result);
    }

}
