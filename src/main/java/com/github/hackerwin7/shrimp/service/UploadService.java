package com.github.hackerwin7.shrimp.service;

import com.github.hackerwin7.jlib.utils.commons.CommonUtils;
import com.github.hackerwin7.shrimp.common.Utils;
import com.github.hackerwin7.shrimp.thrift.client.ControllerClient;
import com.github.hackerwin7.shrimp.thrift.client.TransClient;
import com.github.hackerwin7.shrimp.thrift.gen.TFileInfo;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.InputStream;
import java.nio.file.*;
import java.util.Properties;

/**
 * Created by IntelliJ IDEA.
 * User: hackerwin7
 * Date: 2016/07/22
 * Time: 11:12 AM
 * Desc: upload a file to server and inform the controller
 * Tips:
 */
public class UploadService {

    /* logger */
    private static final Logger LOG = Logger.getLogger(UploadService.class);

    /* constants */
    public static final String CONF_SERVER = "server.properties";
    public static final String CONF_SHELL = "config.server";

    /* data */
    private String ip = null;
    private int port = 0;
    private String zks = null;
    private String edPath = null;
    private String fileName = null;

    /**
     * start the service to upload
     * @param src, the upload file source path
     * @throws Exception
     */
    public void start(String src) throws Exception {
        /* args input */
        fileName = getName(src);
        /* load config to get path */
        load();
        /* copy file to the path */
        String des = edPath + fileName;
        fileCopy(src, des);
        /* scan the file info and inform the controller */
        inform();
    }

    /**
     * get the file name from the path
     * @param path
     * @return file name
     * @throws Exception
     */
    private String getName(String path) throws Exception {
        String[] arr = StringUtils.split(path, "/");
        return arr[arr.length - 1];
    }

    /**
     * load ed path from the file config
     * @throws Exception
     */
    private void load() throws Exception {
        InputStream is = CommonUtils.file2in(CONF_SERVER, CONF_SHELL);
        Properties prop = new Properties();
        prop.load(is);
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
    }

    /**
     * copy file from src path to des path
     * @param src
     * @param des
     * @throws Exception
     */
    private void fileCopy(String src, String des) throws Exception {
        Path sp = Paths.get(src);
        Path dp = Paths.get(des);
        Files.copy(sp, dp, StandardCopyOption.REPLACE_EXISTING);
    }

    /**
     * inform a new uploaded file to controller and server, parallel
     * @throws Exception
     */
    private void inform() throws Exception {
        TFileInfo info = getInfo(edPath + fileName);
        informServer(info);
        informController(info);
    }

    /**
     * get info from the path
     * @param path
     * @return file thrift info
     * @throws Exception
     */
    private TFileInfo getInfo(String path) throws Exception {
        File file = new File(path);
        TFileInfo info = new TFileInfo();
        info.setName(file.getName());
        info.setLength(file.length());
        info.setMd5(Utils.md5Hex(path));
        return info;
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
