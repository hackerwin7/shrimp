package com.github.hackerwin7.shrimp.test;

import com.github.hackerwin7.shrimp.thrift.server.MultipleProcServer;

/**
 * Created by IntelliJ IDEA.
 * User: hackerwin7
 * Date: 2016/08/03
 * Time: 3:04 PM
 * Desc:
 * Tips:
 */
public class LocalServerTest {
    public static void main(String[] args) throws Exception {
        MultipleProcServer server = new MultipleProcServer();
        server.setEdPath("src/data/server1/ed/");
        server.setIngPath("src/data/server1/ing/");
        server.start(9091);
    }
}
