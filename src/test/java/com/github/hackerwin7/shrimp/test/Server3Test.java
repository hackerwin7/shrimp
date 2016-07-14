package com.github.hackerwin7.shrimp.test;

import com.github.hackerwin7.shrimp.common.Utils;
import com.github.hackerwin7.shrimp.thrift.client.ControllerClient;
import com.github.hackerwin7.shrimp.service.HeartBeatController;
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
        mServer.setIngPath("src/data/server3/ing/");
        mServer.setEdPath("src/data/server3/ed/");
        mServer.setTransPath("src/data/server3/ed/");
        mServer.start(9093);

        //register to controller, firstly startCon controller
        ControllerClient controller = new ControllerClient();
        controller.open();
        controller.register(Utils.ip() + ":" + 9093);
        controller.close();

        //heartbeat
        HeartBeatController hb = new HeartBeatController("127.0.0.1:2181", Utils.ip(), 9093);
        hb.setPath("src/data/server3/ed/");
        hb.start();
    }
}
