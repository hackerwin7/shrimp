package com.github.hackerwin7.shrimp.executors;

import com.github.hackerwin7.shrimp.common.Utils;
import com.github.hackerwin7.shrimp.thrift.client.ControllerClient;
import com.github.hackerwin7.shrimp.thrift.server.MultipleProcServer;

/**
 * Created by IntelliJ IDEA.
 * User: hackerwin7
 * Date: 2016/07/08
 * Time: 11:00 AM
 * Desc:
 * Tips:
 */
public class Server3Test {
    public static void main(String[] args) throws Exception {
        MultipleProcServer mServer = new MultipleProcServer();
        mServer.setDownPath("src/data/server3/");
        mServer.setUpPath("src/data/server3/");
        mServer.setTransPath("src/data/server3/");
        mServer.start(9093);

        //register to controller, firstly start controller
        ControllerClient controller = new ControllerClient();
        controller.open();
        controller.register(Utils.ip() + ":" + 9093);
        controller.close();
    }
}
