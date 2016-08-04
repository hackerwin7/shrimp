package com.github.hackerwin7.shrimp.thrift.impl;

import com.github.hackerwin7.shrimp.common.Utils;
import com.github.hackerwin7.shrimp.thrift.gen.*;
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

    /* cache queue */
    private ConcurrentHashMap<String, LinkedBlockingQueue<TFileChunk>> queues = new ConcurrentHashMap<>();// key: clientId, val: queue

    /**
     * open the file, download the reading data thread and then return the opened file info
     * @param clientId
     * @param name
     * @param start
     * @return file info
     * @throws TException
     */
    @Override
    public TFileInfo open(String clientId, String name, long start) throws TException {
        TFileInfo info = new TFileInfo();
        String path = edPath + name;
        File file = new File(path);
        try {
            info.setName(name);
            info.setLength(file.length());
            info.setTs(System.currentTimeMillis());
            info.setMd5(Utils.md5Hex(path));
            info.setStart(start);

            /* debug */
            System.out.println(info);
        } catch (Throwable e) {
            LOG.error(e.getMessage(), e);
            throw new TException("open encounter error !");
        }

        /* creating exclusive queue for client */
        LinkedBlockingQueue<TFileChunk> queue = new LinkedBlockingQueue(QLEN);
        queues.put(clientId, queue);

        /* download reading */
        reading(start, info, queue);

        return info;
    }

    /**
     * download the thread to read data from the file
     * @param startOffset
     * @param info
     * @param queue
     */
    private void reading(final long startOffset, final TFileInfo info, final LinkedBlockingQueue<TFileChunk> queue) {
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
                    /* building driver */
                    RandomAccessFile raf = new RandomAccessFile(new File(edPath + info.getName()), "r");
                    raf.seek(startOffset);

                    /* reading */
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

                    /* close */
                    if(raf != null)
                        raf.close();
                } catch (Throwable e) {
                    LOG.error(e.getMessage(), e);
                }
            }
        });
        readThread.start();
    }

    /**
     * get a file chunk from the queue
     * @param clientId
     * @return file chunk
     * @throws TException
     */
    @Override
    public TFileChunk getChunk(String clientId) throws TException {
        try {
            LinkedBlockingQueue<TFileChunk> queue = queues.get(clientId);
            if(queue.isEmpty())
                return null;
            else
                return queue.take();
        } catch (Throwable e) {
            LOG.error(e.getMessage(), e);
            throw new TException("queue take chunk error!");
        }
    }

    /**
     * close the connection
     * @param clientId
     * @throws TException
     */
    @Override
    public void close(String clientId) throws TException {
        try {
            LinkedBlockingQueue<TFileChunk> queue = queues.get(clientId);
            if(queue != null)
                queue.clear();
            queues.remove(clientId); // dispose the client exclusive queue
        } catch (Throwable e) {
            LOG.error(e.getMessage(), e);
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
}
