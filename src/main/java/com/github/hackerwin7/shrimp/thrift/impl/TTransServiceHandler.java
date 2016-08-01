package com.github.hackerwin7.shrimp.thrift.impl;

import com.github.hackerwin7.shrimp.common.Utils;
import com.github.hackerwin7.shrimp.thrift.gen.TFileInfo;
import com.github.hackerwin7.shrimp.thrift.gen.TFilePool;
import com.github.hackerwin7.shrimp.thrift.gen.TMessage;
import com.github.hackerwin7.shrimp.thrift.gen.TTransService;
import org.apache.log4j.Logger;
import org.apache.thrift.TException;

import java.io.File;
import java.util.HashMap;

/**
 * Created by IntelliJ IDEA.
 * User: hackerwin7
 * Date: 2016/07/06
 * Time: 11:12 AM
 * Desc:
 * Tips:
 */
public class TTransServiceHandler implements TTransService.Iface {

    /* logger */
    private static final Logger LOG = Logger.getLogger(TUFileServiceHandler.class);

    /* data */
    private String relPath = null;
    private TFilePool localPool = new TFilePool();

    /**
     * client send pool to the server
     * @param pool
     * @throws TException
     */
    @Override
    public void sendPool(TFilePool pool) throws TException {
        localPool = pool;
    }

    /**
     * add file to the local pool
     * @param info
     * @throws TException
     */
    @Override
    public void addFile(TFileInfo info) throws TException {
        if(localPool == null)
            localPool = new TFilePool();
        if(localPool.getPool() == null)
            localPool.setPool(new HashMap<String, TFileInfo>());
        localPool.getPool().put(info.getName(), info);
    }

    /**
     * other nodes send the msg to the local
     * @param msg
     * @throws TException
     */
    @Override
    public void sendMsg(TMessage msg) throws TException {
        /* no op temporarily */
    }

    /**
     * get the file info
     * @param name
     * @return file info, null demonstrate not exists
     * @throws TException
     */
    @Override
    public TFileInfo getFileInfo(String name) throws TException {
        TFileInfo info = new TFileInfo();
        String path = relPath + name;
        File file = new File(path);
        if(!file.exists())
            return info;
        info.setName(name);
        info.setLength(file.length());
        try {
            info.setMd5(Utils.md5Hex(path));
        } catch (Exception | Error e) {
            LOG.error(e.getMessage(), e);
            throw new TException("md5 error !");
        }
        return info;
    }

    /* getter and setter */

    public void setRelPath(String relPath) {
        this.relPath = relPath;
    }

    public TFilePool getLocalPool() {
        return localPool;
    }

    public void setLocalPool(TFilePool pool) {
        localPool = pool;
    }
}
