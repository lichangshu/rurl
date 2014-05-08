/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.microwww.rurl.tool;

import java.io.IOException;
import java.sql.SQLException;
import org.apache.openjpa.jdbc.meta.MappingTool;
import org.apache.openjpa.jdbc.meta.ReverseMappingTool;
import org.junit.Test;

/**
 *
 * @author changshu.li
 */
public class TableEntity {

    //@Test
    public void testMappingTool() throws IOException, SQLException {
        MappingTool.main(new String[]{"net.microwww.rurl.domain.Account"});
    }

    //@Test
    public void testReverseMappingTool() throws IOException, SQLException {
        String f = TableEntity.class.getResource("/").getFile() + "../../src/main/java";
        System.out.println(f);
        ReverseMappingTool.main(new String[]{"-pkg", "net.microwww.rurl.domain", "-d", f, "-annotations", "true"});
    }
}
