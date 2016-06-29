package com.github.hackerwin7.shrimp.executors;

import com.github.hackerwin7.shrimp.thrift.server.TransServer;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: hackerwin7
 * Date: 2016/06/29
 * Time: 11:16 AM
 * Desc: test for download service server
 * Tips:
 */
public class ServerServiceTest {

    private Map<String, Integer> hp = new HashMap<>();
    private int port = 9090;

    public static void main(String[] args) throws Exception {
        ServerServiceTest dsst = new ServerServiceTest();
        dsst.start();
    }

    private void start() throws Exception {
        TransServer server = new TransServer();
        server.setDownPath("src/main/resources/");
        server.setUpPath("src/main/resources/");
        server.start(port);
    }
}
