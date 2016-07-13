package com.github.hackerwin7.shrimp.executors;

import com.github.hackerwin7.jlib.utils.test.drivers.zk.ZkClient;
import com.github.hackerwin7.shrimp.thrift.server.ControllerServer;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.thrift.TException;

import java.net.InetAddress;

/**
 * Created by IntelliJ IDEA.
 * User: hackerwin7
 * Date: 2016/07/07
 * Time: 3:20 PM
 * Desc:
 * Tips:
 */
public class ControllerServerTest {

    private static final Logger LOG = Logger.getLogger(ControllerServerTest.class);

    public static void main(String[] args) throws Exception {
        ControllerServerTest cst = new ControllerServerTest();
        cst.start();
    }

    public void start() throws Exception {
        int dport = 9097;
        ControllerServer controller = null;
        String zks = "127.0.0.1:2181";
        ZkClient zk = new ZkClient(zks);
        String localHost = ip();
        if(zk.exists("/shrimp/controller")) {
            String hostpath = zk.get("/shrimp/controller");
            String[] arr = StringUtils.split(hostpath, ":");
            String host = arr[0];
            int port = Integer.parseInt(arr[1]);
            if(StringUtils.equals(host, localHost)) {
                // startCon controller
                controller = new ControllerServer();
                controller.start(port);
            } else {
                //nothing
            }
        } else {
            // startCon controller
            controller = new ControllerServer();
            controller.start(dport);
            //update the zk path
            if(!zk.exists("/shrimp"))
                zk.create("/shrimp", "");
            if(!zk.exists("/shrimp/controller"))
                zk.create("/shrimp/controller", localHost + ":" + dport);
            else
                zk.set("/shrimp/controller", localHost + ":" + dport);
        }
        LOG.debug("controller started......");
    }

    private String ip() throws Exception {
        InetAddress ip = InetAddress.getLocalHost();
        return ip.getHostAddress();
    }

    private void start1() throws TException {
        ControllerServer controllerServer = new ControllerServer();
        controllerServer.start();
    }
}
