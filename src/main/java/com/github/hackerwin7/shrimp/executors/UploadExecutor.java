package com.github.hackerwin7.shrimp.executors;

import com.github.hackerwin7.shrimp.service.UploadService;

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
        UploadService service = new UploadService();
        service.start(args[0]);
    }
}
