package com.github.hackerwin7.shrimp.test;

import com.github.hackerwin7.shrimp.common.Err;
import com.github.hackerwin7.shrimp.thrift.client.DownloadClient;

/**
 * Created by IntelliJ IDEA.
 * User: hackerwin7
 * Date: 2016/06/22
 * Time: 10:28 AM
 * Desc: a retry test code for exploring
 * Tips:
 */
public class DownloadServiceTest {
    public static void main(String[] args) throws Exception {
        String host = null;
        int port = 9090;
        String name = null;
        long offset = 0;

        DownloadClient down = new DownloadClient("127.0.0.1:2181");
        Err err = down.download(host, port, name, offset);

        /* error and retry  */
        while (err.getErrCode() != Err.OK) {
            //log print
            switch (err.getErrCode()) {
                case Err.DOWNLOAD_FAIL:
                    //choose a new host to continue the download by the commit offset
                    //func: findNewNode()
                    err = down.download(host, port, name, err.getCommitOffset());
                    break;
                case Err.MD5_CHECKING:
                    //retry the whole file
                    //func: findNewNode()
                    err = down.download(host, port, name, 0);
                    break;
                default:
                    //print log unknown op to handle the error
            }
        }
    }
}
