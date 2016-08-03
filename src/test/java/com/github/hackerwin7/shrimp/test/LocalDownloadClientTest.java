package com.github.hackerwin7.shrimp.test;

import com.github.hackerwin7.shrimp.common.Err;
import com.github.hackerwin7.shrimp.thrift.client.DownloadClient;

/**
 * Created by IntelliJ IDEA.
 * User: hackerwin7
 * Date: 2016/08/03
 * Time: 3:08 PM
 * Desc:
 * Tips:
 */
public class LocalDownloadClientTest {
    public static void main(String[] args) throws Exception {
        DownloadClient client = new DownloadClient();
        client.setEdPath("src/data/server2/ed/");
        client.setIngPath("src/data/server2/ing/");
        Err err = client.download("127.0.0.1", 9091, "apache-tomcat-8.0.14.tar.gz", 0);
        System.out.println(err.getErrCode());
    }
}
