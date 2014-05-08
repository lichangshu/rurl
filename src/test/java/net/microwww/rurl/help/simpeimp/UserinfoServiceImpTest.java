/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.microwww.rurl.help.simpeimp;

import com.alibaba.fastjson.JSONObject;
import java.util.List;
import net.microwww.rurl.domain.Webappurl;
import net.microwww.rurl.rmi.dto.Department;
import net.microwww.rurl.rmi.dto.Employe;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author lcs
 */
public class UserinfoServiceImpTest {

    public UserinfoServiceImpTest() {
    }

    @Test
    public void testFindAllDepartment() {
        System.out.println("findAllDepartment");
        UserinfoServiceImp instance = new UserinfoServiceImp();
        List<Department> result = instance.findAllDepartment();
        assertEquals(5, result.size());
    }

    @Test
    public void testFindEmployeByDepartmentId() {
        System.out.println("findEmployeByDepartmentId");
        String did = "2";
        UserinfoServiceImp instance = new UserinfoServiceImp();
        List<Employe> result = instance.findEmployeByDepartmentId(did);
        assertEquals(2, result.size());
    }

    @Test
    public void testFindEmployeByName() {
        System.out.println("findEmployeByName");
        String name = "*n*";
        UserinfoServiceImp instance = new UserinfoServiceImp();
        List<Employe> result = instance.findEmployeByAccount(name);
        assertEquals(3, result.size());
    }

    public static void main(String[] args){
        Webappurl url = new Webappurl();
        JSONObject obj = (JSONObject) JSONObject.toJSON(url);
        System.out.print(obj);
    }
}
