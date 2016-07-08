package com.github.hackerwin7.shrimp.executors;

import com.github.hackerwin7.shrimp.common.Err;
import com.github.hackerwin7.shrimp.thrift.client.DownloadClient;

/**
 * Created by IntelliJ IDEA.
 * User: hackerwin7
 * Date: 2016/07/08
 * Time: 11:04 AM
 * Desc:
 * Tips:
 */
public class Client3Test {
    public static void main(String[] args) throws Exception {
        DownloadClient download = new DownloadClient();
        download.setRelPath("src/data/server3/");
        Err err = download.download("pom.xml", 5);
        if(err.getErrCode() != Err.OK)
            System.out.println("failure");
        else
            System.out.println("success");
    }
}
