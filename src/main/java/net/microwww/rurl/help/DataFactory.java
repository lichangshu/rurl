/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.microwww.rurl.help;

import net.microwww.rurl.help.simpeimp.UserinfoServiceImp;
import net.microwww.rurl.service.UserinfoService;

/**
 *
 * @author lcs
 */
public class DataFactory {

    private static final UserinfoService uis = new UserinfoServiceImp();

    public static UserinfoService getUserinfoService() {
        return uis;
    }
}
