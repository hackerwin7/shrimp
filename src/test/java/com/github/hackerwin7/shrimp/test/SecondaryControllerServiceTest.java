package com.github.hackerwin7.shrimp.test;

import com.github.hackerwin7.shrimp.common.Utils;
import com.github.hackerwin7.shrimp.service.ControllerService;

/**
 * Created by IntelliJ IDEA.
 * User: hackerwin7
 * Date: 2016/07/14
 * Time: 10:26 AM
 * Desc:
 * Tips:
 */
public class SecondaryControllerServiceTest {
    public static void main(String[] args) throws Exception {
        SecondaryControllerServiceTest scst = new SecondaryControllerServiceTest();
        scst.start();
    }

    public void start() throws Exception {
        ControllerService service = new ControllerService("127.0.0.1:2181", Utils.ip(), 9098);
        service.start();
    }
}
