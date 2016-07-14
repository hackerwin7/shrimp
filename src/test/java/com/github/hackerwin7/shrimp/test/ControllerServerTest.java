package com.github.hackerwin7.shrimp.test;

import com.github.hackerwin7.shrimp.common.Utils;
import com.github.hackerwin7.shrimp.service.ControllerService;
import org.apache.log4j.Logger;

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
        ControllerService service = new ControllerService("127.0.0.1:2181", Utils.ip(), 9097);
        service.start();
    }
}
