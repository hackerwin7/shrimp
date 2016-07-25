package com.github.hackerwin7.shrimp.executors;

import com.github.hackerwin7.shrimp.service.DownloadService;

/**
 * Created by IntelliJ IDEA.
 * User: hackerwin7
 * Date: 2016/07/22
 * Time: 3:16 PM
 * Desc:
 * Tips:
 */
public class DownloadExecutor {
    public static void main(String[] args) throws Exception {
        DownloadService service = new DownloadService();
        service.start(args[0]);
    }
}
