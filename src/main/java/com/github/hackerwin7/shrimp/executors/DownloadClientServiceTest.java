package com.github.hackerwin7.shrimp.executors;

import com.github.hackerwin7.shrimp.common.Err;
import com.github.hackerwin7.shrimp.thrift.client.DownloadClient;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.RandomAccessFile;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: hackerwin7
 * Date: 2016/06/29
 * Time: 10:21 AM
 * Desc: test service for download
 * Tips:
 */
public class DownloadClientServiceTest {

    private Map<String, Integer> hp = new HashMap<String, Integer>();
    private List<String> hosts = new LinkedList<String>();

    private String name = "servers";

    public static void main(String[] args) throws Exception {

        DownloadClientServiceTest dst = new DownloadClientServiceTest();
        dst.start();

    }

    private void start() throws Exception {

        //load servers conf
        init();

        //process
        process();

        //close
        close();
    }


    private void init() throws Exception {
        File file = new File("src/main/resources/servers");
        RandomAccessFile raf = new RandomAccessFile(file, "r");
        String line = null;
        while ((line = raf.readLine()) != null) {
            String[] arr = StringUtils.split(line, ":");
            String host = arr[0];
            int port = Integer.parseInt(arr[1]);
            hosts.add(host);
            hp.put(host, port);
        }
        raf.close();
    }

    private void process() throws Exception {

        //get the specific host and port
        String host = hosts.get(0);
        int port = hp.get(host);

        //connect and download
        DownloadClient client = new DownloadClient();
        client.setRelPath("src/main/");
        Err error = client.download(host, port, name, 3);
        if(error.getErrCode() != Err.OK)
            System.out.println("failure");
        else
            System.out.println("success");
    }

    private void close() throws Exception {
        /* no op */
    }
}
