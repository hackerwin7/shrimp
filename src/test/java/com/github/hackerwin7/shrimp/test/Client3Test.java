package com.github.hackerwin7.shrimp.test;

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
        download.setIngPath("src/data/server3/ing/");
        download.setEdPath("src/data/server3/ed/");
        Err err = download.download("shrimp.iml", 0);
        if(err.getErrCode() != Err.OK)
            System.out.println("failure");
        else
            System.out.println("success");
    }
}
