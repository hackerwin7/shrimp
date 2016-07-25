package com.github.hackerwin7.shrimp.thrift.client;

import com.github.hackerwin7.jlib.utils.test.drivers.zk.ZkClient;
import com.github.hackerwin7.shrimp.thrift.gen.TControllerService;
import com.github.hackerwin7.shrimp.thrift.gen.TFileInfo;
import com.github.hackerwin7.shrimp.thrift.gen.TFilePool;
import com.github.hackerwin7.shrimp.thrift.gen.TOperation;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TMultiplexedProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;

import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: hackerwin7
 * Date: 2016/07/06
 * Time: 3:46 PM
 * Desc:
 * Tips:
 */
public class ControllerClient {

    /* logger */
    private static final Logger LOG = Logger.getLogger(ControllerClient.class);

    /* constants */
    public static final String ZK_ROOT_PATH = "/shrimp";
    public static final String ZK_CONTROLLER_PATH = "/shrimp/controller";

    /* data */
    private String conhp = null;
    private String host = null;
    private int port = 9091;

    /* driver */
    private TTransport transport = null;
    private TControllerService.Client client = null;

    /**
     * init zk
     * @param zks
     * @throws Exception
     */
    public ControllerClient(String zks) throws Exception {
        init(zks);
    }

    /**
     * zk driver args
     * @param zk
     * @throws Exception
     */
    public ControllerClient(ZkClient zk) throws Exception {
        init(zk);
    }

    /**
     * constructor host and port
     * @param host, controller server's host not client it's target host
     * @param port, controller server's
     * @throws Exception
     */
    public ControllerClient(String host, int port) throws Exception {
        this.host = host;
        this.port = port;
    }

    /**
     * constructor with zk driver
     * why too many connections , I know the reason : maybe the exception between the new and close had been thrown
     * @param zks
     * @throws Exception
     */
    private void init(String zks) throws Exception {
        ZkClient zk = null;
        try {
            zk = new ZkClient(zks);
            conhp = zk.get(ZK_CONTROLLER_PATH);
            String[] arr = StringUtils.split(conhp, ":");
            host = arr[0];
            port = Integer.parseInt(arr[1]);
        } catch (Exception | Error e) {
            LOG.error(e.getMessage(), e);
        } finally {
            if(zk != null)
                zk.close();
        }
    }

    /**
     * find the controller server host and ip
     * @param zk
     * @throws Exception
     */
    private void init(ZkClient zk) throws Exception {
        conhp = zk.get(ZK_CONTROLLER_PATH);
        String[] arr = StringUtils.split(conhp, ":");
        host = arr[0];
        port = Integer.parseInt(arr[1]);
    }

    /**
     * open the client
     * @throws TException
     */
    public void open() throws TException {
        transport = new TSocket(host, port);
        transport.open();
        TProtocol protocol = new TBinaryProtocol(transport);
        TMultiplexedProtocol mp = new TMultiplexedProtocol(protocol, "Controller");
        client = new TControllerService.Client(mp);
    }

    /**
     * send op to controller server
     * @param op
     * @throws TException
     */
    public void sendOp(TOperation op) throws TException {
        client.sendSignal(op);
    }

    /**
     * send pools to controller
     * @param pools
     * @throws TException
     */
    public void sendPools(Map<String, TFilePool> pools) throws TException {
        client.sendFilePools(pools);
    }

    /**
     * send pool to controller
     * @param pool
     * @throws TException
     */
    public void sendPool(TFilePool pool) throws TException {
        client.sendFilePool(pool);
    }

    /**
     * send file
     * before upload and after download
     * @param info
     * @throws TException
     */
    public void sendInfo(TFileInfo info) throws TException {
        client.sendFileInfo(info);
    }

    /**
     * register server to the controller
     * @param hostport
     * @throws TException
     */
    public void register(String hostport) throws TException {
        client.registerServer(hostport);
    }

    /**
     * find the servers which own the specific file name
     * @param fileName
     * @return servers
     * @throws TException
     */
    public List<String> servers(String fileName) throws TException {
        return client.fileServers(fileName);
    }

    /**
     * close the conn
     * @throws TException
     */
    public void close() throws TException {
        transport.close();
    }
}
