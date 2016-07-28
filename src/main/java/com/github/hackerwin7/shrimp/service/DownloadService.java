package com.github.hackerwin7.shrimp.service;

import com.github.hackerwin7.jlib.utils.commons.CommonUtils;
import com.github.hackerwin7.shrimp.common.Err;
import com.github.hackerwin7.shrimp.thrift.client.DownloadClient;
import org.apache.commons.lang3.StringUtils;

import java.io.InputStream;
import java.util.Properties;

/**
 * Created by IntelliJ IDEA.
 * User: hackerwin7
 * Date: 2016/07/21
 * Time: 11:06 AM
 * Desc: provide a good interface to call download file
 * Tips:
 */
public class DownloadService {

    /* constants */
    public static final String CONF_SERVER = "server.properties";
    public static final String CONF_SHELL = "config.server";

    /**
     * shell command download
     * @param fileName
     */
    public void start(String fileName) throws Exception {
        if(StringUtils.isBlank(fileName))
            throw new Exception("download file name is null, please indicate the target...");
        /* load config form file */
        InputStream is = CommonUtils.file2in(CONF_SERVER, CONF_SHELL);
        Properties prop = new Properties();
        prop.load(is);
        String ingPath = prop.getProperty("downloading.dir.path");
        if(StringUtils.isBlank(ingPath))
            ingPath = "./";
        if(!StringUtils.endsWith(ingPath, "/"))
            ingPath += "/";
        String edPath = prop.getProperty("downloaded.dir.path");
        if(StringUtils.isBlank(edPath))
            edPath = "./";
        if(!StringUtils.endsWith(edPath, "/"))
            edPath += "/";
        String zks = prop.getProperty("zookeeper.connect");
        is.close();
        /* start download */
        DownloadClient client = new DownloadClient(zks);
        client.setIngPath(ingPath);
        client.setEdPath(edPath);
        Err err = client.download(fileName, 0);
        if(err.getErrCode() != Err.OK)
            System.out.println("failure");
        else
            System.out.println("success");
    }
}
