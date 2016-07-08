package com.github.hackerwin7.shrimp.common;

import org.apache.commons.codec.digest.DigestUtils;

import java.io.File;
import java.io.FileInputStream;
import java.net.InetAddress;

/**
 * Created by IntelliJ IDEA.
 * User: hackerwin7
 * Date: 2016/06/15
 * Time: 2:03 PM
 * Desc: common utils for the project
 * Tips:
 */
public class Utils {

    /**
     * get md5 hex string
     * @param path
     * @return md5 string
     * @throws Exception
     */
    public static String md5Hex(String path) throws Exception {
        File file = new File(path);
        FileInputStream fis = new FileInputStream(file);
        String md5 = DigestUtils.md5Hex(fis);
        fis.close();
        return md5;
    }

    /**
     * by input stream
     * @param fis
     * @return md5
     * @throws Exception
     */
    public static String md5Hex(FileInputStream fis) throws Exception {
        return DigestUtils.md5Hex(fis);
    }

    /**
     * get local host ip
     * @return local ip
     * @throws Exception
     */
    public static String ip() throws Exception {
        InetAddress ip = InetAddress.getLocalHost();
        return ip.getHostAddress();
    }
}
