package com.github.hackerwin7.shrimp.service;

import com.github.hackerwin7.jlib.utils.test.drivers.zk.ZkClient;
import com.github.hackerwin7.shrimp.common.Utils;
import com.github.hackerwin7.shrimp.thrift.client.ControllerClient;
import com.github.hackerwin7.shrimp.thrift.gen.TFileInfo;
import com.github.hackerwin7.shrimp.thrift.gen.TFilePool;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by IntelliJ IDEA.
 * User: hackerwin7
 * Date: 2016/07/11
 * Time: 10:28 AM
 * Desc: every server send a heartbeat constantly (startCon send and every minute send)
 *          it's also a controller client sample
 * Tips:
 */
public class HeartBeatController {

    /* logger */
    private static final Logger LOG = Logger.getLogger(HeartBeatController.class);

    /* running signal */
    private AtomicBoolean running = new AtomicBoolean(true);

    /* constants */
    public static final long SLEEP_INTERVAL = 10000;
    public static final int RAND_SLEEP_SEC = 20;

    /* relative path */
    private String path = "";

    /* data */
    private String host = null;
    private int port = 9091;
    private String zks = null;
    private ZkClient zk = null;

    /**
     * constructor
     * @param zks conn string, use to find the target host and target port
     * @param host, local server's host
     * @param port, local server's port
     */
    public HeartBeatController(String zks, String host, int port) {
        this.host = host;
        this.port = port;
        this.zks = zks;
    }

    /**
     * reload zk driver constructor
     * @param zk
     * @param host
     * @param port
     */
    public HeartBeatController(ZkClient zk, String host, int port) {
        this.host = host;
        this.port = port;
        this.zk = zk;
    }

    /**
     * startCon the controller to be running
     */
    public void start() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                //running and interval 10s + 20s random
                while (running.get()) {
                    try {
                        proc();
                        //sleeping
                        Thread.sleep(SLEEP_INTERVAL);
                    } catch (Exception | Error e) {
                        LOG.error("heart beat running error : " + e.getMessage(), e);
                    }
                }

            }
        });
        thread.start();
    }

    /**
     * indicate the count for the heartbeat send
     * @param count
     * @param sec
     */
    public void start(final int count, final int sec) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                int times = count;
                //running and interval 50s + 20s random
                while (times > 0) {
                    try {
                        proc(sec);
                        //sleeping
                        Thread.sleep(SLEEP_INTERVAL);
                        times --;
                    } catch (Exception | Error e) {
                        LOG.error("heart beat running error : " + e.getMessage(), e);
                    }
                }

            }
        });
        thread.start();
    }

    /**
     * start process
     * 1.scan the relative path to get the file pool info
     * 2.sleep the random seconds (avoid hot spot)
     * 3.startCon the controller client and send the file pool info to controller server
     * @throws Exception
     */
    private void proc() throws Exception {
        TFilePool pool = scan();
        randSleep();
        send(pool);
    }

    /**
     * indicate the seconds to sleep
     * @param sec
     * @throws Exception
     */
    private void proc(int sec) throws Exception {
        TFilePool pool = scan();
        Thread.sleep(sec * 1000);
        send(pool);
    }

    /**
     * scan the file pool info according to the path
     * @return file pool
     * @throws Exception
     */
    private TFilePool scan() throws Exception {
        Map<String, TFileInfo> infos = new HashMap<>();
        File dir = new File(path);
        String rpath = getPath(path);
        File[] files = dir.listFiles();
        for(File file : files) {
            // info
            TFileInfo info = new TFileInfo();
            info.setName(file.getName());
            info.setLength(file.length());
            info.setMd5(Utils.md5Hex(rpath + info.getName()));
            // put
            infos.put(info.getName(), info);
        }
        TFilePool pool = new TFilePool();
        pool.setHost(host);
        pool.setPort(port);
        pool.setPool(infos);
        return pool;
    }

    /**
     * get the relative file path
     * @return relative path
     */
    private String getPath(String path) {
        if(StringUtils.endsWith(path, "/"))
            return path;
        else
            return path + "/";
    }

    /**
     * sleep random seconds
     * @throws Exception
     */
    private void randSleep() throws Exception {
        Random random = new Random();
        int rd = random.nextInt(RAND_SLEEP_SEC) + 1;
        Thread.sleep(rd * 1000);
    }

    /**
     * startCon the conn to controller and send the pool info to the controller
     * @throws Exception
     */
    private void send(TFilePool pool) throws Exception {
        ControllerClient controller = null;
        try {
            if(zk == null) {
                controller = new ControllerClient(zks);
            } else {
                controller = new ControllerClient(zk);
            }
            controller.open();
            controller.sendPool(pool);
        } catch (Exception | Error e) {
            LOG.error(e.getMessage(), e);
        } finally {
            if(controller != null)
                controller.close();
        }
    }

    /* getter and setter */

    public void setPath(String path) {
        this.path = path;
    }
}
