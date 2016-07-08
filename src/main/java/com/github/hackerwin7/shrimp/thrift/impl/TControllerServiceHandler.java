package com.github.hackerwin7.shrimp.thrift.impl;

import com.github.hackerwin7.shrimp.thrift.client.TransClient;
import com.github.hackerwin7.shrimp.thrift.gen.TControllerService;
import com.github.hackerwin7.shrimp.thrift.gen.TFileInfo;
import org.apache.commons.lang3.StringUtils;
import org.apache.hadoop.io.file.tfile.TFile;
import org.apache.log4j.Logger;
import org.apache.thrift.TException;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

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
    private Map<String, TFileInfo> filePool = new HashMap<>();
    private List<String> servers = new LinkedList<>();//127.0.0.1:9098
    private Map<String, TransClient> clients = new HashMap<>();

    /* setting signal data */
    private int findAccount = 3;

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
        servers.add(hostport);
        // when registering the server, we open the client conn
        clients.put(hostport, generateClient(hostport));

        // DEBUG
        System.out.println("calling register ...");

        LOG.debug("register call ...");
        LOG.debug("servers : " + servers);
        LOG.debug("clients : " + clients);
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
     * spread to call the get file info
     * @param name
     * @return list of servers own the specific file
     * @throws TException
     */
    @Override
    public List<String> fileServers(String name) throws TException {
        // get the complete file info from the file pool
        TFileInfo info = filePool.get(name);
        //spread to server to get the their local file info
        List<String> hpList = new LinkedList<>();
        if(info != null) {
            for (Map.Entry<String, TransClient> entry : clients.entrySet()) {
                String hp = entry.getKey();
                try {
                    TransClient client = entry.getValue();
                    TFileInfo local = client.fileInfo(name);
                    if(StringUtils.isBlank(local.getName())) // not exists
                        continue;
                    if (StringUtils.equals(info.getMd5(), local.getMd5())) {
                        hpList.add(hp);
                        if (hpList.size() >= findAccount)
                            break;
                    }
                } catch (TException e) {
                    LOG.error("spread call error ;" + e.getMessage(), e);
                    reload(hp);
                    //retry once
                    TransClient client = entry.getValue();
                    TFileInfo local = client.fileInfo(name);
                    if(StringUtils.isBlank(local.getName())) // not exists
                        continue;
                    if (StringUtils.equals(info.getMd5(), local.getMd5())) {
                        hpList.add(hp);
                        if (hpList.size() >= findAccount)
                            break;
                    }
                }
            }
        } else { // the file pool dose not exists the specific file
            HashMap<String, TFileInfo> md5Info = new HashMap<>();
            HashMap<String, Integer> md5Int = new HashMap<>(); // counter
            List<TFileInfo> infos = new LinkedList<>();
            Map<String, TFileInfo> hostInfo = new HashMap<>();
            for (Map.Entry<String, TransClient> entry : clients.entrySet()) {
                String hp = entry.getKey();
                try {
                    TransClient client = entry.getValue();
                    TFileInfo local = client.fileInfo(name);
                    if(StringUtils.isBlank(local.getName())) // not exists
                        continue;
                    hostInfo.put(hp, local);
                    infos.add(local);
                    if(!md5Info.containsKey(hp)) {
                        md5Info.put(local.getMd5(), local);
                        md5Int.put(local.getMd5(), 1);
                    } else {
                        int num = md5Int.get(local.getMd5()) + 1;
                        md5Int.put(local.getMd5(), num);
                    }
                } catch (TException e) {
                    LOG.error("spread call error ;" + e.getMessage(), e);
                    reload(hp);
                    //retry once
                    TransClient client = entry.getValue();
                    TFileInfo local = client.fileInfo(name);
                    if(StringUtils.isBlank(local.getName())) // not exists
                        continue;
                    hostInfo.put(hp, local);
                    infos.add(local);
                    if(!md5Info.containsKey(hp)) {
                        md5Info.put(local.getMd5(), local);
                        md5Int.put(local.getMd5(), 1);
                    } else {
                        int num = md5Int.get(local.getMd5()) + 1;
                        md5Int.put(local.getMd5(), num);
                    }
                }
            }
            //find the max account of the md5 file info
            String maxMd5 = findMaxMd5(md5Int, md5Info);
            //update the file pool
            filePool.put(name, md5Info.get(maxMd5));
            //add the max mad5 file info into the hpList
            for(Map.Entry<String, TFileInfo> entry : hostInfo.entrySet()) {
                String hostport = entry.getKey();
                TFileInfo single = entry.getValue();
                if(StringUtils.equals(maxMd5, single.getMd5())) {
                    hpList.add(hostport);
                    if(hpList.size() >= findAccount)
                        break;
                }
            }
        }
        return hpList;
    }

    /**
     * find max account of md5
     * @param mp
     * @return max md5
     */
    private String findMaxMd5(HashMap<String, Integer> mp, HashMap<String, TFileInfo> infop) {
        int max = 0;
        String maxMd5 = null;
        for(Map.Entry<String, Integer> entry : mp.entrySet()) {
            String md5 = entry.getKey();
            int count = entry.getValue();
            if(count > max) {
                max = count;
                maxMd5 = md5;
            } else if(count == max) { // length bigger is choosed
                TFileInfo cur = infop.get(maxMd5);
                TFileInfo next = infop.get(md5);
                if(cur == null || next.getLength() > cur.getLength()) {
                    max = count;
                    maxMd5 = md5;
                }
            }
        }
        return maxMd5;
    }

    /* getter and setter */

    public void setFindAccount(int findAccount) {
        this.findAccount = findAccount;
    }
}
