package com.github.hackerwin7.shrimp.thrift.impl;

import com.github.hackerwin7.shrimp.common.Utils;
import com.github.hackerwin7.shrimp.thrift.gen.*;
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
public class TDFileServiceHandler implements TDFileService.Iface {

    /* logger */
    private static final Logger LOG = Logger.getLogger(TDFileServiceHandler.class);

    /* constants */
    public static final int QLEN = 1024;

    /* file transfer path, downloading and downloaded*/
    private String edPath = null;
    private String ingPath = null;

    /* data */
    private RandomAccessFile raf = null;

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
        String path = edPath + name;
        File file = new File(path);
        try {
            raf = new RandomAccessFile(file, "r");
            raf.seek(start);//set offset
            info.setName(name);
            info.setLength(file.length());
            info.setTs(System.currentTimeMillis());
            info.setMd5(Utils.md5Hex(path));
            info.setStart(start);

            /* debug */
            System.out.println(info);
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
    private void reading(final long startOffset, final TFileInfo info) {
        Thread readThread = new Thread(new Runnable() {
            @Override
            public void run() {
                long index = startOffset;
                long left = info.length - startOffset;//account - account, startOffset = (startoffset - 1) + 1; ='s left is account, ='s right is offset
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
            queue.clear();
        } catch (Throwable e) {
            LOG.error(e.getMessage(), e);
            err = 1;
        }
    }

    /* setter and getter */
    public String getEdPath() {
        return edPath;
    }

    public void setEdPath(String edPath) {
        this.edPath = edPath;
    }

    public void setIngPath(String ingPath) {
        this.ingPath = ingPath;
    }

    public int getErr() {
        return err;
    }
}
