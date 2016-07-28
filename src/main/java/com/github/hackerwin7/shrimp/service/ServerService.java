package com.github.hackerwin7.shrimp.service;

import com.github.hackerwin7.jlib.utils.commons.CommonUtils;
import com.github.hackerwin7.jlib.utils.drivers.zk.ZkClient;
import com.github.hackerwin7.shrimp.common.Utils;
import com.github.hackerwin7.shrimp.thrift.client.ControllerClient;
import com.github.hackerwin7.shrimp.thrift.server.MultipleProcServer;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import java.io.InputStream;
import java.util.Properties;

/**
 * Created by IntelliJ IDEA.
 * User: hackerwin7
 * Date: 2016/07/14
 * Time: 11:44 AM
 * Desc: start the server to provide file transfer service
 *          load config from file
 * Tips:
 */
public class ServerService {

    /* logger */
    private static final Logger LOG = Logger.getLogger(ServerService.class);

    /* data */
    private String host = null;
    private int port = 9091;
    private String ingPath = null;
    private String edPath = null;
    private String zks = null;
    private ZkClient zk = null;

    /* constants */
    public static final String CONF_SERVER = "server.properties";
    public static final String CONF_SHELL = "config.server";

    /**
     * start the server service
     * @throws Exception
     */
    public void start() throws Exception {
        load();
        run();
    }

    /**
     * load config from the file
     * @throws Exception
     */
    private void load() throws Exception {
        InputStream is = CommonUtils.file2in(CONF_SERVER, CONF_SHELL);
        Properties prop = new Properties();
        prop.load(is);
        host = prop.getProperty("host");
        if(StringUtils.isBlank(host))
            host = Utils.ip();
        port = Integer.parseInt(prop.getProperty("port"));
        ingPath = prop.getProperty("downloading.dir.path");
        if(!StringUtils.endsWith(ingPath, "/"))
            ingPath += "/";
        edPath = prop.getProperty("downloaded.dir.path");
        if(!StringUtils.endsWith(edPath, "/"))
            edPath += "/";
        zks = prop.getProperty("zookeeper.connect");
        is.close();

        /* zk client init */
        zk = new ZkClient(zks);
    }

    /**
     * apply the config and start the server
     * 1.thrift server start
     * 2.register to controller
     * 3.start heartbeat
     * @throws Exception
     */
    private void run() throws Exception {
        LOG.info("starting server......");
        MultipleProcServer server = new MultipleProcServer();
        server.setIngPath(ingPath);
        server.setEdPath(edPath);
        server.setTransPath(edPath);
        server.start(port);

        ControllerClient client = new ControllerClient(zk);
        client.open();
        client.register(host + ":" + port);
        client.close();

        HeartBeatController hb = new HeartBeatController(zk, host, port); // why too many connections ??
        hb.setPath(edPath);
        hb.start();
    }
}
