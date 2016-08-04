package com.github.hackerwin7.shrimp.thrift.impl;

import com.github.hackerwin7.shrimp.common.Err;
import com.github.hackerwin7.shrimp.common.Utils;
import com.github.hackerwin7.shrimp.thrift.gen.TErr;
import com.github.hackerwin7.shrimp.thrift.gen.TFileChunk;
import com.github.hackerwin7.shrimp.thrift.gen.TFileInfo;
import com.github.hackerwin7.shrimp.thrift.gen.TUFileService;
import org.apache.log4j.Logger;
import org.apache.thrift.TException;

import java.io.File;
import java.io.RandomAccessFile;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by IntelliJ IDEA.
 * User: hackerwin7
 * Date: 2016/06/24
 * Time: 3:13 PM
 * Desc: implement upload interface
 * Tips:
 */
public class TUFileServiceHandler implements TUFileService.Iface {

    /* logger */
    private static final Logger LOG = Logger.getLogger(TUFileServiceHandler.class);

    /* constants */
    public static final int QLEN = 1024;

    /* file transfer path */
    private String ingPath = null;
    private String edPath = null;

    /* cache queue */
    private ConcurrentHashMap<String, LinkedBlockingQueue<TFileChunk>> queues = new ConcurrentHashMap<>();// key: clientId, val: queue

    /* error pool */
    private ConcurrentHashMap<String, Err> errors = new ConcurrentHashMap<>();// key: clientId, val: error

    /**
     * get the client send the file info, how to process it??
     * @param clientId
     * @param info
     * @return file information
     * @throws TException
     */
    @Override
    public void open(String clientId, TFileInfo info) throws TException {

        /* generate exclusive error and queue */
        LinkedBlockingQueue<TFileChunk> queue = new LinkedBlockingQueue<>(QLEN);
        queues.put(clientId, queue);
        Err error = new Err();
        errors.put(clientId, error);

        try {
            writing(info, queue, error);
        } catch (Exception | Error e) {
            LOG.error(e.getMessage(), e);
            throw new TException("writing error!");
        }
    }

    /**
     * client send the chunk to the server , we save these chunk into the queue
     * @param clientId
     * @param chunk
     * @throws TException
     */
    @Override
    public void sendChunk(String clientId, TFileChunk chunk) throws TException {
        try {
            LinkedBlockingQueue<TFileChunk> queue = queues.get(clientId);
            queue.put(chunk);
        } catch (Exception | Error e) {
            LOG.error(e.getMessage(), e);
            throw new TException("queue operation error !");
        }
    }

    /**
     * write the chunk into the disk from the queue
     * startCon the writing thread
     * @param info
     * @param queue
     * @throws Exception
     */
    private void writing(final TFileInfo info, final LinkedBlockingQueue<TFileChunk> queue, final Err error) throws Exception {
        Thread wth = new Thread(new Runnable() {
            @Override
            public void run() {
                long write = info.getStart();
                String path = ingPath + info.getName();
                File file = new File(path);
                RandomAccessFile raf = null;
                try {
                    raf = new RandomAccessFile(file, "rw");
                    raf.setLength(info.getLength());
                    raf.seek(info.getStart());
                    while (write < info.getLength()) {
                        TFileChunk chunk = queue.take();
                        byte[] bytes = chunk.getBytes();
                        raf.write(bytes, 0, (int) chunk.getLength());
                        write += chunk.getLength();
                    }
                } catch (Exception | Error e) {
                    LOG.error(e.getMessage(), e);
                    error.setErrCode(Err.UPLOAD_FAIL);
                    error.setCommitOffset(write);//write is first offset that haven't been write
                } finally {
                    if(raf != null)
                        try {
                            raf.close();
                        } catch (Exception | Error e) {
                            LOG.error(e.getMessage(), e);
                        }
                }
            }
        });
        wth.start();
    }

    /**
     * checking the upload cases, error code or md5 match
     * @param clientId
     * @param info
     * @return thrift error code
     * @throws TException
     */
    @Override
    public TErr checking(String clientId, TFileInfo info) throws TException {
        Err error = errors.get(clientId);
        TErr terror = new TErr();
        if(error.getErrCode() != Err.OK) {
            terror.setErr(error.getErrCode());
            terror.setCommit(error.getCommitOffset());
            return terror;
        }
        /* md5 match */
        String path = ingPath + info.getName();
        try {
            String client = info.getMd5();
            String server = Utils.md5Hex(path);
            if(client.equals(server))
                terror.setErr(Err.OK);
            else
                terror.setCommit(Err.MD5_CHECKING);
        } catch (Exception | Error e) {
            LOG.error(e.getMessage(), e);
            terror.setErr(Err.MD5_CHECKING);
        }

        /* if OK moving */
        if (terror.getErr() == Err.OK)
            moving(info.getName());

        return terror;
    }

    /**
     * move file from ing to ed
     * @param fileName
     * @throws TException
     */
    private void moving(String fileName) throws TException {
        File ingFile = new File(ingPath + fileName);
        File edFile = new File(edPath + fileName);
        if(!ingFile.renameTo(edFile))
            throw new TException("moving file error");
    }

    /**
     * get the file info from the upload server
     * @param name
     * @return file info
     * @throws TException
     */
    @Override
    public TFileInfo fileInfo(String name) throws TException {
        TFileInfo info = new TFileInfo();
        try {
            String path = ingPath + name;
            File file = new File(path);
            info.setName(name);
            info.setLength(file.length());
            info.setMd5(Utils.md5Hex(path));
            info.setTs(System.currentTimeMillis());
        } catch (Exception | Error e) {
            throw new TException("file info error!");
        }
        return info;
    }

    /**
     * close the cache, connection etc..
     * @param clientId
     * @throws TException
     */
    @Override
    public void close(String clientId) throws TException {
        LinkedBlockingQueue<TFileChunk> queue = queues.get(clientId);
        if(queue != null)
            queue.clear();
        queues.remove(clientId);
        errors.remove(clientId);
    }

    /* getter and setter */

    public String getIngPath() {
        return ingPath;
    }

    public void setIngPath(String ingPath) {
        this.ingPath = ingPath;
    }

    public void setEdPath(String edPath) {
        this.edPath = edPath;
    }
}
