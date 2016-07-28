package com.github.hackerwin7.shrimp.executors;

import com.github.hackerwin7.jlib.utils.commons.CommonUtils;
import com.github.hackerwin7.shrimp.common.Utils;
import com.github.hackerwin7.shrimp.service.UploadService;
import org.apache.log4j.PropertyConfigurator;

/**
 * Created by IntelliJ IDEA.
 * User: hackerwin7
 * Date: 2016/07/22
 * Time: 3:17 PM
 * Desc:
 * Tips:
 */
public class UploadExecutor {
    public static void main(String[] args) throws Exception {
        PropertyConfigurator.configure(CommonUtils.file2in(Utils.LOG4J_PROPERTY, Utils.LOG4J_SHELL));
        UploadService service = new UploadService();
        service.start(args[0]);
    }
}
