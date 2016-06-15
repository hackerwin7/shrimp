package com.github.hackerwin7.shrimp.thrift.impl;

import com.github.hackerwin7.shrimp.common.Utils;
import com.github.hackerwin7.shrimp.thrift.gen.TFileChunk;
import com.github.hackerwin7.shrimp.thrift.gen.TFileInfo;
import com.github.hackerwin7.shrimp.thrift.gen.TFileService;
import com.github.hackerwin7.shrimp.thrift.gen.TransFileConstants;
import org.apache.log4j.Logger;
import org.apache.thrift.TException;

import java.io.File;
import java.io.RandomAccessFile;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by IntelliJ IDEA.
 * User: hackerwin7
 * Date: 2016/06/15
 * Time: 11:36 AM
 * Desc: implement thrift interface for file transportation
 * Tips:
 */
public class TFileServiceHandler implements TFileService.Iface {

    /* logger */
    private static final Logger LOG = Logger.getLogger(TFileServiceHandler.class);

    /* constants */
    public static final int QLEN = 1024;

    /* file transfer path */
    private String relPath = null;

    /* data */
    private RandomAccessFile raf = null;

    /* read thread */
    private Thread readThread = null;

    /* cache queue */
    private BlockingQueue<TFileChunk> queue = new LinkedBlockingQueue<>(QLEN);

    /* signal */
    private int err = 0;

    /**
     * open the file, download the reading data thread and then return the opened file info
     * @param name
     * @param start
     * @return file info
     * @throws TException
     */
    @Override
    public TFileInfo open(String name, long start) throws TException {
        TFileInfo info = new TFileInfo();
        String path = relPath + name;
        File file = new File(path);
        try {
            raf = new RandomAccessFile(file, "r");
            info.setName(name);
            info.setLength(file.length());
            info.setTs(System.currentTimeMillis());
            info.setMd5(Utils.md5Hex(path));
        } catch (Throwable e) {
            LOG.error(e.getMessage(), e);
            err = 1;
            throw new TException("open encounter error !");
        }

        /* download reading */
        reading(start, info);

        return info;
    }

    /**
     * download the thread to read data from the file
     * @param startOffset
     * @param info
     */
    private void reading(long startOffset, TFileInfo info) {
        readThread = new Thread(new Runnable() {
            @Override
            public void run() {
                long index = startOffset;
                long left = info.length - startOffset;
                int read = TransFileConstants.CHUNK_UNIT;
                if(left < read)
                    read = (int) left;

                /* download reading */
                try {
                    while (index <= info.length - 1) {

                        /* read */
                        byte[] bytes = new byte[read];
                        raf.read(bytes, 0, read);

                        /* build file chunk */
                        TFileChunk chunk = new TFileChunk();
                        chunk.setBytes(bytes);
                        chunk.setName(info.name);
                        chunk.setLength(read);
                        chunk.setOffset(index);

                        /* cache */
                        queue.put(chunk);

                        /* index, length adjusting */
                        index += read;
                        left = info.length - index;
                        if(left < read)
                            read = (int) left;
                    }
                } catch (Throwable e) {
                    LOG.error(e.getMessage(), e);
                    err = 1;
                }
            }
        });
        readThread.start();
    }

    /**
     * get a file chunk from the queue
     * @return file chunk
     * @throws TException
     */
    @Override
    public TFileChunk getChunk() throws TException {
        try {
            if(queue.isEmpty())
                return null;
            else
                return queue.take();
        } catch (Throwable e) {
            LOG.error(e.getMessage(), e);
            err = 1;
            throw new TException("queue take chunk error!");
        }
    }

    /**
     * close the connection
     * @throws TException
     */
    @Override
    public void close() throws TException {
        try {
            if(raf != null)
                raf.close();
        } catch (Throwable e) {
            LOG.error(e.getMessage(), e);
            err = 1;
        }
    }

    /* setter and getter */
    public String getRelPath() {
        return relPath;
    }

    public void setRelPath(String relPath) {
        this.relPath = relPath;
    }

    public int getErr() {
        return err;
    }
}
