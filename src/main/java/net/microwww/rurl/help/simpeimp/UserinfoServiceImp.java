/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.microwww.rurl.help.simpeimp;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import net.microwww.rurl.rmi.NoRightException;
import net.microwww.rurl.rmi.dto.Department;
import net.microwww.rurl.rmi.dto.Employe;
import net.microwww.rurl.service.UserinfoService;
import net.microwww.util.SystemConfig;

/**
 *
 * @author changshu.li
 */
public class UserinfoServiceImp implements UserinfoService {

    private static final String deps = SystemConfig.getConfig("departments", "1,n,1");
    private static final String emps = SystemConfig.getConfig("employes", "1,n,1");

    public List<Department> findAllDepartment() {
        String[] dps = deps.split(";");
        List<Department> list = new ArrayList<Department>();
        for (String str : dps) {
            String[] ss = str.split(",");
            if (ss.length >= 3) {
                Department dm = new Department();
                dm.setId(ss[0]);
                dm.setName(ss[1]);
                dm.setPid(ss[2]);
                list.add(dm);
            }
        }
        return list;
    }

    public List<Employe> findEmployeByDepartmentId(String did) {
        Pattern pt = Pattern.compile("([^,;]+,[^,;]+,\\s*" + did + ");");
        List<Employe> list = getempbypt(pt);
        return list;
    }

    private List<Employe> getempbypt(Pattern pt) {
        List<Employe> list = new ArrayList<Employe>();
        Matcher mt = pt.matcher(emps);
        while (mt.find()) {
            String str = mt.group(1);
            String[] ss = str.split(",");
            if (ss.length >= 3) {
                Employe dm = new Employe();
                dm.setAccount(ss[0]);
                dm.setName(ss[1]);
                list.add(dm);
            }
        }
        return list;
    }

    public List<Employe> findEmployeByAccount(String account) {
        if (account.startsWith("*")) {
            account = "[^,;]*" + account.substring(1);
        }
        if (account.endsWith("*")) {
            account = account.substring(0, account.length() - 1) + "[^,;]*";
        }
        Pattern pt = Pattern.compile("(" + account + ",[^,;]+,[^,;]+);");
        return getempbypt(pt);
    }

    // 测试实现！
    public Employe login(String account, String password) throws NoRightException {
        List<Employe> ems = this.findEmployeByAccount(account);
        if (ems.isEmpty()) {
            throw new NoRightException(-1, "用户名不存在");
        }
        if (!password.equals(account)) {
            throw new NoRightException(-2, "密码错误，测试数据 账户密码相同");
        }
        return ems.get(0);
    }

}
