package com.github.hackerwin7.shrimp.thrift.client;

import com.github.hackerwin7.jlib.utils.test.drivers.zk.ZkClient;
import com.github.hackerwin7.shrimp.thrift.gen.TControllerService;
import com.github.hackerwin7.shrimp.thrift.gen.TFileInfo;
import com.github.hackerwin7.shrimp.thrift.gen.TFilePool;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TMultiplexedProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;

import java.util.List;

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
     * load the zk string with the conf file
     * @throws Exception
     */
    public ControllerClient() throws Exception {
        //load the zk conn string
        String zks = "127.0.0.1:2181";
        init(zks);
    }

    /**
     * constructor with zk driver
     * @param zks
     * @throws Exception
     */
    private void init(String zks) throws Exception {
        ZkClient zk = new ZkClient(zks);
        conhp = zk.get(ZK_CONTROLLER_PATH);
        String[] arr = StringUtils.split(conhp, ":");
        host = arr[0];
        port = Integer.parseInt(arr[1]);
        zk.close();
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
