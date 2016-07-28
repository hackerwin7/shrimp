package com.github.hackerwin7.shrimp.service;

import com.github.hackerwin7.jlib.utils.commons.CommonUtils;
import com.github.hackerwin7.jlib.utils.drivers.zk.ZkClient;
import com.github.hackerwin7.shrimp.common.Utils;
import com.github.hackerwin7.shrimp.thrift.client.ControllerClient;
import com.github.hackerwin7.shrimp.thrift.gen.TFilePool;
import com.github.hackerwin7.shrimp.thrift.gen.TOperation;
import com.github.hackerwin7.shrimp.thrift.server.ControllerServer;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.zookeeper.CreateMode;

import java.io.InputStream;
import java.util.Map;
import java.util.Properties;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by IntelliJ IDEA.
 * User: hackerwin7
 * Date: 2016/07/12
 * Time: 10:41 AM
 * Desc: controller and secondary controller
 *          controller is master running, secondary is also running but do not provide any service
 *          controller send heartbeat (file pool info replica) to the secondary, when master crash (zk ephemeral node not exists) :
 *                                                      secondary kill the master
 *                                                      secondary change the zk (create new ephemeral node) that all server target to secondary
 *                                                      secondary startCon the killed master controller as the new secondary controller
 *                                                      secondary -> master, master -> secondary
 *       controller and secondary controller is all controller service, but a different is that :
 *          1. zk controller and sec-controller
 *          2. secondary controller do not send the heartbeat but it also must startCon thrift controller to receive the heartbeat
 *
 *
 *
 *       load config from the file
 * Tips:
 */
public class ControllerService {

    /* logger */
    private static final Logger LOG = Logger.getLogger(ControllerService.class);

    /* data */
    private String zkstr = null;
    private String ip = null; // local controller ip (maybe also secondary controller)
    private int port = 9097; // local controller port
    private String secIp = null; // secondary controller ip
    private int secPort = 9097; // secondary controller port

    /* constants */
    public static final String ZK_ROOT = "/shrimp";
    public static final String ZK_CONTROLLER = "/controller";
    public static final String ZK_SEC_CONTROLLER = "/sec-controller";
    public static final String CONF_CONTROLLER = "controller.properties";
    public static final String CONF_SHELL = "config.controller";

    /* driver */
    private ZkClient zk = null;

    /* thread start and sub*/
    private ControllerServer tController = null;
    private Timer timer = null;
    private Thread signal = null;
    private Thread trans = null;
    private Thread sth = null;

    /* signal */
    private AtomicBoolean transRunning = new AtomicBoolean(true);
    private AtomicBoolean statusRunning = new AtomicBoolean(true);

    /**
     * default reload constructor
     */
    public ControllerService() {

    }

    /**
     * constructor
     * @param zks
     * @param ip
     * @param port
     */
    public ControllerService(String zks, String ip, int port) {
        this.zkstr = zks;
        this.ip = ip;
        this.port = port;
    }

    /**
     * start the controller service
     * @throws Exception
     */
    public void start() throws Exception {
        load();
        run();
    }

    /**
     * load config from file
     * @throws Exception
     */
    private void load() throws Exception {
        if(StringUtils.isBlank(zkstr) || StringUtils.isBlank(ip)) {
            InputStream is = null;
            is = CommonUtils.file2in(CONF_CONTROLLER, CONF_SHELL);
            Properties prop = new Properties();
            prop.load(is);
            ip = prop.getProperty("host");
            if (StringUtils.isBlank(ip))
                ip = Utils.ip();
            port = Integer.parseInt(prop.getProperty("port"));
            zkstr = prop.getProperty("zookeeper.connect");
            is.close();
        }
    }

    /**
     * service start
     * check the zk to decide to start controller or secondary controller
     * @throws Exception
     */
    private void run() throws Exception {
        ZkClient client = null;
        try {
            client = new ZkClient(zkstr);
            if(!client.exists(ZK_ROOT)) {
                startCon();
            } else {
                if(!client.exists(ZK_ROOT + ZK_CONTROLLER))
                    startCon();
                else
                if(!client.exists(ZK_ROOT + ZK_SEC_CONTROLLER))
                    startSec();
                else
                    LOG.error("no need to start service, all path have own value...");
            }
        } catch (Exception | Error e) {
            LOG.error(e.getMessage(), e);
        } finally {
            if(client != null)
                client.close();
        }
    }

    /**
     * start the controller service
     * 1.register zk info
     * 2.startCon thrift controller server as start thread
     * 3.startCon the heartbeat to send the info to secondary (timer heartbeat send) as sub-thread
     * 4.startCon signal as sub-thread
     * @throws Exception
     */
    public void startCon() throws Exception {
        zk();
        thrift();
        heartbeat();
        signal();
        LOG.info("starting as a controller......");
    }

    /**
     * startCon as a secondary controller
     * @throws Exception
     */
    public void startSec() throws Exception {
        zkSec();
        thrift();
        signal();
        transfer();
        status();
        LOG.info("starting as a secondary controller......");
    }

    /**
     * controller transfer to secondary controller
     * whole close and startCon as a secondary controller
     * @throws Exception
     */
    public void trans2Sec() throws Exception {
        LOG.info("changing from controller into secondary controller......");
        close();
        startSec();
    }

    /**
     * sec-controller change into controller
     * 1. change zk (waiting the old zk node killed), kill the sec and change into master
     * 2. startCon heartbeat
     * how to trigger the transfer ???
     * @throws Exception
     */
    public void trans2Cont() throws Exception {
        LOG.info("changing from secondary controller into controller......");
        /* waiting and change zk */
        while (zk.exists(ZK_ROOT + ZK_CONTROLLER)) {
            Thread.sleep(3000);
            LOG.info("waiting the old master controller exiting ......");
        }
        String val = null;
        if(zk.exists(ZK_ROOT + ZK_SEC_CONTROLLER)) {
            val = zk.get(ZK_ROOT + ZK_SEC_CONTROLLER);
            zk.delete(ZK_ROOT + ZK_SEC_CONTROLLER);
        } else {
            val = ip + ":" + port;
        }
        zk.create(ZK_ROOT + ZK_CONTROLLER, val, CreateMode.EPHEMERAL);

        /* heartbeat */
        heartbeat();

        /* close some secondary thread */
        if(trans != null) {
            transRunning.set(false);
            trans = null;
        }
        if(sth != null) {
            statusRunning.set(false);
            sth = null;
        }
    }

    /**
     * zk info register
     * @throws Exception
     */
    private void zk() throws Exception {
        if(zk == null)
            zk = new ZkClient(zkstr);
        /* zk node config */
        if(!zk.exists(ZK_ROOT))
            zk.create(ZK_ROOT, null);
        if(!zk.exists(ZK_ROOT + ZK_CONTROLLER))
            zk.create(ZK_ROOT + ZK_CONTROLLER, ip + ":" + port, CreateMode.EPHEMERAL);
        else
            throw new Exception("zk node is not clean !!! ");
    }

    /**
     * secondary zk startCon
     * @throws Exception
     */
    private void zkSec() throws Exception {
        if(zk == null)
            zk = new ZkClient(zkstr);
        /* zk node config */
        if(!zk.exists(ZK_ROOT))
            zk.create(ZK_ROOT, null);
        while (zk.exists(ZK_ROOT + ZK_SEC_CONTROLLER)) {
            Thread.sleep(3000); // waiting the old second controller exiting......
        }
        zk.create(ZK_ROOT + ZK_SEC_CONTROLLER, ip + ":" + port, CreateMode.EPHEMERAL);
    }

    /**
     * startCon the thrift controller server and startCon to provide service
     * @throws Exception
     */
    private void thrift() throws Exception {
        tController = new ControllerServer();
        tController.start(port);
    }

    /**
     * send heartbeat to secondary controller
     * @throws Exception
     */
    private void heartbeat() throws Exception {
        timer = new Timer();
        timer.schedule(new HeartBeatTask(), 10000, 30000);
    }

    /*
    * timer task, send heartbeat
    * */
    public class HeartBeatTask extends TimerTask {

        public static final int RETRY_COUNT = 3;
        private int cnt = 0;

        /**
         * running process :
         * 1.zk secondary check, if >= 3 times zk secondary not exits, the secondary is crash , reload the secondary controller
         * 2.send info pool to secondary
         */
        @Override
        public void run() {

            try {
                if(!zk.exists(ZK_ROOT + ZK_SEC_CONTROLLER)) {
                    LOG.info("not found zk secondary controller info......");
                    cnt++;
                    if(cnt >= RETRY_COUNT) {
                        int signal = reloadSecController();
                        if(signal == 1)
                            LOG.error("restart secondary controller failed, no zk sec-controller node value !!!");
                        cnt = 0;
                    }
                } else {
                    /* get secondary info from zk */
                    String data = zk.get(ZK_ROOT + ZK_SEC_CONTROLLER);
                    String[] arr = StringUtils.split(data, ":");
                    secIp = arr[0];
                    secPort = Integer.parseInt(arr[1]);
                    /* get the pool info from the controller handler */
                    Map<String, TFilePool> pools = tController.getPools();
                    /* startCon the client to send the info to the secondary controller */
                    ControllerClient client = null;
                    try {
                        LOG.info("sending pool to secondary controller that is " + pools);
                        client = new ControllerClient(secIp, secPort);
                        client.open();
                        client.sendPools(pools);
                    } catch (Exception | Error e) {
                        LOG.error(e.getMessage(), e);
                    } finally {
                        if(client != null)
                            client.close();
                    }
                }
            } catch (Exception | Error e) {
                LOG.error(e.getMessage(), e);
            }
        }
    }

    /**
     * send a signal to restart the secondary controller
     * @return signal 0 success, 1 failure
     * @throws Exception
     */
    private int reloadSecController() throws Exception {
        if(!StringUtils.isBlank(secIp)) { // if secIp null, waiting the secondary fill the zk node value
            ControllerClient client = null;
            try {
                client = new ControllerClient(secIp, secPort);
                client.open();
                client.sendOp(TOperation.restart);
            } catch (Exception | Error e) {
                LOG.error(e.getMessage(), e);
            } finally {
                if(client != null)
                    client.close();
            }
            return 0;
        } else {
            return 1;
        }
    }

    /**
     * signal thread to receive operation
     * @throws Exception
     */
    private void signal() throws Exception {
        if(signal == null || !signal.isAlive()) {
            signal = new Thread(new Runnable() {
                @Override
                public void run() {
                    while (true) {
                        try {
                            TOperation op = tController.getOp();
                            if (op == TOperation.restart) { // restart the whole service
                                close();
                                startSec();
                            }
                            Thread.sleep(2000);
                        } catch (Exception | Error e) {
                            LOG.error(e.getMessage(), e);
                        }
                    }
                }
            });
            signal.start();
        }
    }

    /**
     * transfer thread to check the master controller zk path available
     * @throws Exception
     */
    private void transfer() throws Exception {
        if(trans == null || !trans.isAlive()) {
            trans = new Thread(new Runnable() {
                @Override
                public void run() {
                    transRunning.set(true);
                    while (transRunning.get()) {
                        try {
                            if(!zk.exists(ZK_ROOT + ZK_CONTROLLER)) {
                                //transfer the sec-controller to controller and restart the controller to sec-controller
                                LOG.debug("transfer to master controller......");
                                trans2Cont();
                            }
                            Thread.sleep(3000);
                        } catch (Exception | Error e) {
                            LOG.error(e.getMessage(), e);
                        }
                    }
                }
            });
            trans.start();
        }
    }

    /**
     * status the log every minute
     * @throws Exception
     */
    private void status() throws Exception {
        if(sth == null || !sth.isAlive()) {
            sth = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        statusRunning.set(true);
                        while (statusRunning.get()) {
                            Map<String, TFilePool> pools = tController.getPools();
                            LOG.info("backup pool info: " + pools);
                            Thread.sleep(30 * 1000);
                        }
                    } catch (Exception | Error e) {
                        LOG.error(e.getMessage(), e);
                    }
                }
            });
            sth.start();
        }
    }

    /**
     * close the zk conn
     * @throws Exception
     */
    public void close() throws Exception {
        if(zk != null) {
            zk.close();
            zk = null;
        }
        if(tController != null) {
            tController.close();
            tController = null;
        }
        if(timer != null) {
            timer.cancel();
            timer = null;
        }
        if(trans != null) {
            transRunning.set(false);
            trans = null;
        }
        if(sth != null) {
            statusRunning.set(false);
            sth = null;
        }
        //could not close the signal, it can not kill itself (signal can not close signal)
    }
}
