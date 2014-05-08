/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.microwww.rurl.service;

import java.util.List;
import net.microwww.rurl.rmi.NoRightException;
import net.microwww.rurl.rmi.dto.Department;
import net.microwww.rurl.rmi.dto.Employe;

/**
 *
 * @author changshu.li
 */
public interface UserinfoService {

    /**
     * 列出所有部门
     *
     * @return
     */
    public List<Department> findAllDepartment();

    /**
     * 列出部门的所有员工
     *
     * @param did
     * @return
     */
    public List<Employe> findEmployeByDepartmentId(String did);

    /**
     * 根据 name 查找员工.（可以是姓名也可以是账户，由实现决定）
     *
     * @param account
     * @return
     */
    public List<Employe> findEmployeByAccount(String account);

    /**
     * 登录
     *
     * @param account
     * @param password
     * @return
     * @throws net.microwww.rurl.rmi.NoRightException
     */
    public Employe login(String account, String password) throws NoRightException;
}
