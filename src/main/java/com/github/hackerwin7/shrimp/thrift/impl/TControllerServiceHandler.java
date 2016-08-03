package com.github.hackerwin7.shrimp.thrift.impl;

import com.github.hackerwin7.shrimp.thrift.client.TransClient;
import com.github.hackerwin7.shrimp.thrift.gen.TControllerService;
import com.github.hackerwin7.shrimp.thrift.gen.TFileInfo;
import com.github.hackerwin7.shrimp.thrift.gen.TFilePool;
import com.github.hackerwin7.shrimp.thrift.gen.TOperation;
import io.netty.util.internal.ConcurrentSet;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.thrift.TException;

import java.util.*;
import java.util.concurrent.*;

/**
 * Created by IntelliJ IDEA.
 * User: hackerwin7
 * Date: 2016/07/06
 * Time: 11:23 AM
 * Desc:
 * Tips:
 */
public class TControllerServiceHandler implements TControllerService.Iface {

    /* logger */
    private static final Logger LOG = Logger.getLogger(TControllerServiceHandler.class);

    /* data */
    private Map<String, TFileInfo> filePool = new ConcurrentHashMap<>();
    private Set<String> servers = new ConcurrentSet<>();// 127.0.0.1:9098, MAX = INTEGER.MAX
    private Map<String, TransClient> clients = new ConcurrentHashMap<>();
    private Map<String, TFilePool> pools = new ConcurrentHashMap<>();// key = host:port

    /* signal */
    private int findAccount = 3;

    /* signal queue */
    private BlockingQueue<TOperation> signalQ = new LinkedBlockingQueue<>(1024);

    /**
     * send operation signal
     * @param op
     * @throws TException
     */
    @Override
    public void sendSignal(TOperation op) throws TException {
        try {
            if(op != null)
                signalQ.put(op);
        } catch (Exception | Error e) {
            LOG.error(e.getMessage(), e);
            throw new TException("signal queue put error");
        }
    }

    /**
     * add file to the controller's pool
     * @param host
     * @param port
     * @param info
     * @throws TException
     */
    @Override
    public void addFile(String host, int port, TFileInfo info) throws TException {
        String key = host + ":" + port;
        if(!pools.containsKey(key) || pools.get(key) == null)
            pools.put(key, new TFilePool());
        if(pools.get(key).getPool() == null)
            pools.get(key).setPool(new HashMap<String, TFileInfo>());
        pools.get(key).getPool().put(info.getName(), info); // add the file info to the specific host , specific ip, specific file name
    }

    /**
     * send pools info to controller server
     * @param pools
     * @throws TException
     */
    @Override
    public void sendFilePools(Map<String, TFilePool> pools) throws TException {
        this.pools.clear();
        this.pools.putAll(pools);

        //debug show pools
        LOG.debug("=======> pools refresh : " + this.pools);
    }

    /**
     * send file pool to the controller server
     * @param pool
     * @throws TException
     */
    @Override
    public void sendFilePool(TFilePool pool) throws TException {
        pools.put(pool.getHost() + ":" + pool.getPort(), pool);
    }

    /**
     * send the file info to the controller and update the info pool
     * @param info
     * @throws TException
     */
    @Override
    public void sendFileInfo(TFileInfo info) throws TException {
        if(info != null) {
            String name = info.getName();
            filePool.put(name, info);
        }
    }

    /**
     * register the new-started server to the controller
     * @param hostport
     * @throws TException
     */
    @Override
    public void registerServer(String hostport) throws TException {
        servers.add(hostport);// thread safe
        // when registering the server, we open the client conn
        clients.put(hostport, generateClient(hostport));
    }

    /**
     * generate the client according to host and port
     * @param hp
     * @return trans client
     * @throws TException
     */
    private TransClient generateClient(String hp) throws TException {
        String[] arr = StringUtils.split(hp, ":");
        String host = arr[0];
        int port = Integer.parseInt(arr[1]);
        TransClient client = new TransClient(host, port);
        try {
            client.open();// long conn ??
        } catch (Exception | Error e) {
            LOG.error(e.getMessage(), e);
            throw new TException("controller client pool client open error!!!");
        }
        return client;
    }

    /**
     * close the controller
     * @throws Exception
     */
    public void close() throws Exception {
        for (Map.Entry<String, TransClient> entry : clients.entrySet()) {
            TransClient client = entry.getValue();
            client.close();
        }
    }

    /**
     * close the single client
     * @param hp
     * @throws Exception
     */
    public void close(String hp) throws TException {
        TransClient client = clients.get(hp);
        client.close();
        clients.remove(hp);
    }

    /**
     * reload the client
     * @param hp
     * @throws Exception
     */
    public void reload(String hp) throws TException {
        TransClient client = clients.get(hp);
        client.close();
        client.open();
    }

    /**
     * check the mem-data and return result
     * server directory path : complete, downloading
     * how to get the least conn account of the download servers ??
     *      how to get the running connection (through short heartbeat ??)
     * @param name
     * @return list of servers own the specific file
     * @throws TException
     */
    @Override
    public List<String> fileServers(String name) throws TException {
        List<String> hpList = new LinkedList<>();
        for(Map.Entry<String, TFilePool> entry : pools.entrySet()) {
            String hostport = entry.getKey();
            TFilePool pool = entry.getValue();
            Map<String, TFileInfo> infos = pool.getPool();
            if (infos.containsKey(name)) {
                hpList.add(hostport);
                if(hpList.size() >= findAccount)
                    break;
            }
        }
        return hpList;
    }

    /* getter and setter */

    public void setFindAccount(int findAccount) {
        this.findAccount = findAccount;
    }

    public Map<String, TFilePool> getPools() {
        return pools;
    }

    public TOperation getOp() throws Exception {
        return signalQ.take();
    }
}
