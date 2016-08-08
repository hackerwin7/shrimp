package com.github.hackerwin7.shrimp.service;

import com.github.hackerwin7.jlib.utils.commons.CommonUtils;
import com.github.hackerwin7.shrimp.common.Err;
import com.github.hackerwin7.shrimp.common.Utils;
import com.github.hackerwin7.shrimp.thrift.client.ControllerClient;
import com.github.hackerwin7.shrimp.thrift.client.DownloadClient;
import com.github.hackerwin7.shrimp.thrift.client.TransClient;
import com.github.hackerwin7.shrimp.thrift.gen.TFileInfo;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
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

    /* data */
    private String ip = null;
    private int port = 0;
    private String zks = null;
    private String edPath = null;
    private String ingPath = null;
    private String fileName = null;

    /**
     * shell command download
     * @param name
     */
    public void start(String name) throws Exception {
        if(StringUtils.isBlank(name))
            throw new Exception("download file name is null, please indicate the target...");
        fileName = name;
        /* load config form file */
        InputStream is = CommonUtils.file2in(CONF_SERVER, CONF_SHELL);
        Properties prop = new Properties();
        prop.load(is);
        ingPath = prop.getProperty("downloading.dir.path");
        if(StringUtils.isBlank(ingPath))
            ingPath = "./";
        if(!StringUtils.endsWith(ingPath, "/"))
            ingPath += "/";
        edPath = prop.getProperty("downloaded.dir.path");
        if(StringUtils.isBlank(edPath))
            edPath = "./";
        if(!StringUtils.endsWith(edPath, "/"))
            edPath += "/";
        ip = prop.getProperty("host");
        if(StringUtils.isBlank(ip))
            ip = Utils.ip();
        port = Integer.parseInt(prop.getProperty("port"));
        zks = prop.getProperty("zookeeper.connect");
        is.close();
        /* start download */
        DownloadClient client = new DownloadClient(zks);
        client.setIngPath(ingPath);
        client.setEdPath(edPath);
        Err err = client.download(name, 0);
        if(err.getErrCode() != Err.OK)
            System.out.println("failure");
        else
            System.out.println("success");

        /* inform */
        if(err.getErrCode() == Err.OK)
            inform(client.getDownInfo());
    }

    /**
     * inform the local and controller
     * @param info
     * @throws Exception
     */
    private void inform(TFileInfo info) throws Exception {
        informServer(info);
        informController(info);
    }

    /**
     * send a file info to the controller
     * maybe occur the overwrite problem ??? new controller pool overwritten by the old server pool
     * @throws Exception
     */
    private void informController(TFileInfo info) throws Exception {
        ControllerClient client = new ControllerClient(zks);
        client.open();
        client.sendFile(ip, port, info);
        client.close();
    }

    /**
     * send file info to the local server
     * @param info
     * @throws Exception
     */
    private void informServer(TFileInfo info) throws Exception {
        TransClient client = new TransClient(ip, port);
        client.open();
        client.send(info);
        client.close();
    }
}
