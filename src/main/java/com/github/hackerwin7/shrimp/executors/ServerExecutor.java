package com.github.hackerwin7.shrimp.executors;

import com.github.hackerwin7.shrimp.service.ServerService;

/**
 * Created by IntelliJ IDEA.
 * User: hackerwin7
 * Date: 2016/07/14
 * Time: 2:59 PM
 * Desc:
 * Tips:
 */
public class ServerExecutor {
    public static void main(String[] args) throws Exception {
        ServerService server = new ServerService();
        server.start();
    }
}
