package com.github.hackerwin7.shrimp.executors;

import com.github.hackerwin7.shrimp.service.ControllerService;

/**
 * Created by IntelliJ IDEA.
 * User: hackerwin7
 * Date: 2016/07/14
 * Time: 3:00 PM
 * Desc:
 * Tips:
 */
public class ControllerExecutor {
    public static void main(String[] args) throws Exception {
        ControllerService controller = new ControllerService();
        controller.start();
    }
}
