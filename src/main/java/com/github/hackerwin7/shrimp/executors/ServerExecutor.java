package com.github.hackerwin7.shrimp.executors;

import com.github.hackerwin7.jlib.utils.commons.CommonUtils;
import com.github.hackerwin7.shrimp.common.Utils;
import com.github.hackerwin7.shrimp.service.ServerService;
import org.apache.log4j.PropertyConfigurator;

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
        PropertyConfigurator.configure(CommonUtils.file2in(Utils.LOG4J_PROPERTY, Utils.LOG4J_SHELL));
        ServerService server = new ServerService();
        server.start();
    }
}
