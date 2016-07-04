package com.github.hackerwin7.shrimp.executors;

import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Created by IntelliJ IDEA.
 * User: hackerwin7
 * Date: 2016/06/30
 * Time: 4:07 PM
 * Desc: load config
 * Tips:
 */
public class ConfLoader {

    /* constants */
    public static final String CONF_FILENAME = "server.properties";

    /* map conf ??? */
    private Map<String, String> cm = new HashMap<>();

    /**
     * load the config from the file to the mem
     * @throws Exception
     */
    public void load() throws Exception {
        Properties pro = new Properties();
        pro.load(new FileInputStream(CONF_FILENAME));
        cm.put("port", pro.getProperty("port"));
        cm.put("download.path", pro.getProperty("download.path"));
        cm.put("upload.path", pro.getProperty("upload.path"));

    }
}
