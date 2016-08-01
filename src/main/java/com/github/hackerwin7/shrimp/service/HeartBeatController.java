package com.github.hackerwin7.shrimp.service;

import com.github.hackerwin7.jlib.utils.drivers.zk.ZkClient;
import com.github.hackerwin7.shrimp.common.Utils;
import com.github.hackerwin7.shrimp.thrift.client.ControllerClient;
import com.github.hackerwin7.shrimp.thrift.gen.TFileInfo;
import com.github.hackerwin7.shrimp.thrift.gen.TFilePool;
import com.github.hackerwin7.shrimp.thrift.server.MultipleProcServer;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import java.io.File;
import java.util.*;
import java.util.concurrent.*;
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
    public static final int QLEN = 4096;
    public static final int PARALLEL_DEGREE = 50;
    public static final int LONG_TIMER_SCAN = 5 * 60; //5 mins

    /* relative path */
    private String path = "";

    /* data */
    private String host = null;
    private int port = 9091;
    private String zks = null;
    private ZkClient zk = null;

    /* local server handler */
    private MultipleProcServer server = null;

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
    public void start() throws Exception {

        /* init , scan firstly */
        final Thread initThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    mProcInit();
                } catch (Exception e) {
                    LOG.error(e.getMessage(), e);
                }
            }
        });
        initThread.start();

        /* during running, no scan */
        Timer shortTimer = new Timer();
        shortTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                try {
                    if(!initThread.isAlive()) // after init thread complete, start the during running
                        mProcDuring();
                } catch (Exception e) {
                    LOG.error(e.getMessage(), e);
                }
            }
        }, 10000, SLEEP_INTERVAL);

        /* long running , scan and send, is it necessary????
         * cancel the long running temporarily */
//        Timer longTimer = new Timer();
//        longTimer.schedule(new TimerTask() {
//            @Override
//            public void run() {
//                try {
//                    mProc();
//                } catch (Exception e) {
//                    LOG.error(e.getMessage(), e);
//                }
//            }
//        }, LONG_TIMER_SCAN * 1000, LONG_TIMER_SCAN * 1000);
    }

    /**
     * multiple process
     * @throws Exception
     */
    private void mProcInit() throws Exception {
        TFilePool pool = multiScan(PARALLEL_DEGREE);
        send(pool);
        flush(pool);
    }

    /**
     * flush the pool to the local server
     * @param pool
     * @throws Exception
     */
    private void flush(TFilePool pool) throws Exception {
        server.setLocalPool(pool);
    }

    /**
     * multiple process and sleep random seconds
     * @throws Exception
     */
    private void mProc() throws Exception {
        TFilePool pool = multiScan(PARALLEL_DEGREE);
        randSleep();
        send(pool);
    }

    /**
     * not init, during running, hearteat send
     * @throws Exception
     */
    private void mProcDuring() throws Exception {
        TFilePool pool = server.getLocalPool();
        randSleep();
        send(pool);
    }

    /**
     * multiple thread scan
     * @param cnt parallel count
     * @return file info pool
     * @throws Exception
     */
    private TFilePool multiScan(int cnt) throws Exception {

        /* running time */
        long start = System.currentTimeMillis();

        /* get file list */
        File dir = new File(path);
        final String relPath = getPath(path);
        File[] files = dir.listFiles();
        final BlockingQueue<File> inq = new LinkedBlockingQueue<>(QLEN);
        final BlockingQueue<TFileInfo> outq = new LinkedBlockingQueue<>(QLEN);
        for(File file : files) {
            inq.put(file);
        }
        int len = files.length;

        /* parallel process */
        final ConcurrentMap<String, TFileInfo> infos = new ConcurrentHashMap<>();
        ExecutorService executor = Executors.newFixedThreadPool(cnt);
        for(final File file : files) {
            executor.execute(new Runnable() {
                @Override
                public void run() {
                    TFileInfo info = getInfo(file, relPath);
                    infos.put(info.getName(), info);
                }
            });
        }
        executor.shutdown();
        while (!executor.isTerminated()) {
            LOG.info("processing " + infos.size() + " files ......");
        }
        TFilePool pool = new TFilePool();
        pool.setHost(host);
        pool.setPort(port);
        pool.setPool(infos);

        /* running time */
        long end = System.currentTimeMillis();
        LOG.info("~~~~~~~~~~~~~~~~~~ multiple scan running " + (end - start) + " ms ...");
        return pool;
    }

    /**
     * File -> thrift file info
     * @param file
     * @param relPath
     * @return file info
     */
    private TFileInfo getInfo(File file, String relPath) {
        TFileInfo info = new TFileInfo();
        info.setName(file.getName());
        info.setLength(file.length());
        try {
            info.setMd5(Utils.md5Hex(relPath + info.getName()));
        } catch (Exception e) {
            LOG.error(e.getMessage());
        }
        return info;
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
        LOG.info("=====================================================> sending local server pool to controller : ");
        LOG.info("---------------------> " + pool);
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

    public void setServer(MultipleProcServer server) {
        this.server = server;
    }
}
