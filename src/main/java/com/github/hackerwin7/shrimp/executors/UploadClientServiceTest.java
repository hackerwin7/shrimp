package com.github.hackerwin7.shrimp.executors;

import com.github.hackerwin7.shrimp.common.Err;
import com.github.hackerwin7.shrimp.thrift.client.UploadClient;

/**
 * Created by IntelliJ IDEA.
 * User: hackerwin7
 * Date: 2016/06/29
 * Time: 3:35 PM
 * Desc:
 * Tips:
 */
public class UploadClientServiceTest {
    public static void main(String[] args) throws Exception {
        UploadClientServiceTest ucst = new UploadClientServiceTest();
        ucst.start();
    }

    private void start() throws Exception {
        UploadClient client = new UploadClient();
        client.setRelPath("src/main/");
        Err error = client.upload("127.0.0.1", 9090, "pom.xml", 5);
        if(error.getErrCode() != Err.OK)
            System.out.println("failure");
        else
            System.out.println("success");
    }
}
