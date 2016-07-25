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

    /* write thread */
    private Thread wth = null;

    /* cache queue */
    private BlockingQueue<TFileChunk> queue = new LinkedBlockingQueue<>(QLEN);

    /* Error code */
    private Err error = null;

    /**
     * get the client send the file info, how to process it??
     * @param info
     * @return file information
     * @throws TException
     */
    @Override
    public void open(TFileInfo info) throws TException {
        error = new Err();
        try {
            writing(info);
        } catch (Exception | Error e) {
            LOG.error(e.getMessage(), e);
            throw new TException("writing error!");
        }
    }

    /**
     * client send the chunk to the server , we save these chunk into the queue
     * @param chunk
     * @throws TException
     */
    @Override
    public void sendChunk(TFileChunk chunk) throws TException {
        try {
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
     * @throws Exception
     */
    private void writing(final TFileInfo info) throws Exception {
        wth = new Thread(new Runnable() {
            @Override
            public void run() {
                long write = info.getStart();
                String path = ingPath + info.getName();
                File file = new File(path);
                RandomAccessFile raf = null;
                try {
                    raf = new RandomAccessFile(file, "rw");
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
     * @param info
     * @return thrift error code
     * @throws TException
     */
    @Override
    public TErr checking(TFileInfo info) throws TException {
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
     * @throws TException
     */
    @Override
    public void close() throws TException {
        if(queue != null)
            queue.clear();
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
