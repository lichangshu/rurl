/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.microwww.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 *
 * @author changshu.li
 */
public class SystemConfig {

    private static Log logger = LogFactory.getLog(SystemConfig.class);
    private static Properties properties = new Properties();
    public static String CONFIG_FILE = "/config/config.properties";

    static {
        loadConfig();
    }

    private SystemConfig() {
    }

    private static void loadConfig() {
        try {
            InputStream input = SystemConfig.class.getResourceAsStream(CONFIG_FILE);
            properties.load(input);
        } catch (IOException ex) {
            logger.error("load " + CONFIG_FILE + "error", ex);
        }
    }

    public static Properties getConfig() {
        return new Properties(properties);
    }

    public static String getConfig(String key, String def) {
        return (String) getConfig().getProperty(key, def);
    }

    public static String getCmsRoot() {
        return getConfig("CMS_ROOT", "http://news.play67.com");
    }

    public static String getBbsRoot() {
        return getConfig("BBS_ROOT", "http://bbs.play67.com");
    }

    public static float getPhonecardExchang() {
        String hxex = getConfig("phonecardExchang", "9");
        return Float.valueOf(hxex);
    }

    public static float getDefaultExchang() {
        String hxex = getConfig("exchang", "10");
        return Float.valueOf(hxex);
    }

    public static float getJcardExchang() {
        String hxex = getConfig("jcardexchang", "8.5");
        return Float.valueOf(hxex);
    }

    public static float getQQExchang() {
        String hxex = getConfig("jcardexchang", "8.5");
        return Float.valueOf(hxex);
    }

    public static float getBankExchang() {
        String hxex = getConfig("jcardexchang", "10");
        return Float.valueOf(hxex);
    }
}
