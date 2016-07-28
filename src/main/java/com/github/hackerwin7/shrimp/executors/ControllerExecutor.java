package com.github.hackerwin7.shrimp.executors;

import com.github.hackerwin7.jlib.utils.commons.CommonUtils;
import com.github.hackerwin7.shrimp.common.Utils;
import com.github.hackerwin7.shrimp.service.ControllerService;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

/**
 * Created by IntelliJ IDEA.
 * User: hackerwin7
 * Date: 2016/07/14
 * Time: 3:00 PM
 * Desc:
 * Tips:
 */
public class ControllerExecutor {

    private static final Logger LOG = Logger.getLogger(ControllerExecutor.class);

    public static void main(String[] args) throws Exception {
        PropertyConfigurator.configure(CommonUtils.file2in(Utils.LOG4J_PROPERTY, Utils.LOG4J_SHELL));
        ControllerService controller = new ControllerService();
        controller.start();
    }
}
