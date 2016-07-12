package com.github.hackerwin7.shrimp.service;

import com.github.hackerwin7.jlib.utils.test.drivers.zk.ZkClient;
import com.github.hackerwin7.shrimp.thrift.client.ControllerClient;
import com.github.hackerwin7.shrimp.thrift.gen.TFilePool;
import com.github.hackerwin7.shrimp.thrift.server.ControllerServer;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.zookeeper.CreateMode;

import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

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
 *                                                      secondary start the killed master controller as the new secondary controller
 *                                                      secondary -> master, master -> secondary
 * Tips:
 */
public class ControllerService {

    /* logger */
    private static final Logger LOG = Logger.getLogger(ControllerService.class);

    /* data */
    private String zkstr = null;
    private String ip = null;
    private int port = 9097;

    /* constants */
    public static final String ZK_ROOT = "/shrimp";
    public static final String ZK_CONTROLLER = "/controller";
    public static final String ZK_SEC_CONTROLLER = "/sec-controller";

    /* driver */
    private ControllerServer tController = null;
    private ZkClient zk = null;
    private Timer timer = null;

    /**
     * start the controller service
     * 1.register zk info
     * 2.start thrift controller server
     * 3.start the heartbeat to send the info to secondary (timer heartbeat send)
     * @throws Exception
     */
    public void start() throws Exception {
        zk();
        thrift();
        heartbeat();
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
    }

    /**
     * start the thrift controller server and start to provide service
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
                    cnt++;
                    if(cnt >= RETRY_COUNT)
                        reloadSecController();
                } else {
                    /* get secondary info from zk */
                    String data = zk.get(ZK_ROOT + ZK_SEC_CONTROLLER);
                    String[] arr = StringUtils.split(data, ":");
                    String host = arr[0];
                    int port = Integer.parseInt(arr[1]);
                    /* get the pool info from the controller handler */
                    Map<String, TFilePool> pools = tController.getPools();
                    /* start the client to send the info to the secondary controller */
                    ControllerClient client = new ControllerClient(host, port);
                    client.open();
                    client.sendPools(pools);
                    client.close();
                }
            } catch (Exception | Error e) {
                LOG.error(e.getMessage(), e);
            }
        }
    }

    /**
     * restart the crash secondary controller
     * how to kill and reload the remote controller ??
     *  1.ssh ?
     * @throws Exception
     */
    private void reloadSecController() throws Exception {

    }

    /**
     * close the zk conn
     * @throws Exception
     */
    public void close() throws Exception {
        if(zk != null)
            zk.close();
        if(timer != null)
            timer.cancel();
    }
}
