package com.github.hackerwin7.shrimp.executors;

import com.github.hackerwin7.shrimp.common.Utils;
import com.github.hackerwin7.shrimp.thrift.client.ControllerClient;
import com.github.hackerwin7.shrimp.thrift.server.MultipleProcServer;
import org.apache.log4j.Logger;

/**
 * Created by IntelliJ IDEA.
 * User: hackerwin7
 * Date: 2016/07/08
 * Time: 11:00 AM
 * Desc:
 * Tips:
 */
public class Server1Test {

    private static final Logger LOG = Logger.getLogger(Server1Test.class);

    public static void main(String[] args) throws Exception {
        MultipleProcServer mServer = new MultipleProcServer();
        mServer.setDownPath("src/data/server1/");
        mServer.setUpPath("src/data/server1/");
        mServer.setTransPath("src/data/server1/");
        mServer.start(9091);

        //register to controller, firstly start controller
        ControllerClient controller = new ControllerClient();
        controller.open();
        controller.register(Utils.ip() + ":" + 9091);
        controller.close();

        LOG.debug("server1 started......");

//        Server1Test st1 = new Server1Test();
//        st1.start1();
    }

    private void start1() throws Exception {
        //register to controller, firstly start controller
        ControllerClient controller = new ControllerClient();
        controller.open();
        controller.register(Utils.ip() + ":" + 9091);
        controller.close();

        LOG.debug("started server1......");
    }
}
