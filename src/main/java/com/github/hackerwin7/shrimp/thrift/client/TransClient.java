package com.github.hackerwin7.shrimp.thrift.client;

import com.github.hackerwin7.shrimp.common.Utils;
import com.github.hackerwin7.shrimp.thrift.gen.TFileChunk;
import com.github.hackerwin7.shrimp.thrift.gen.TFileInfo;
import com.github.hackerwin7.shrimp.thrift.gen.TFileService;
import org.apache.log4j.Logger;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;

import java.io.File;
import java.io.RandomAccessFile;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by IntelliJ IDEA.
 * User: hackerwin7
 * Date: 2016/06/15
 * Time: 2:30 PM
 * Desc: file transfer client
 * Tips:
 */
public class TransClient {

    /* logger */
    private static final Logger LOG = Logger.getLogger(TransClient.class);

    /* constants */
    public static final int QLEN = 1024;

    /* relative path */
    private String relPath = null;

    /* queue */
    private BlockingQueue<TFileChunk> queue = new LinkedBlockingQueue<>(QLEN);

    /* write thread */
    private Thread writeThread = null;

    /**
     * start client to download the file
     * @param host
     * @param port
     * @param fileName
     * @param offset
     * @return err code, 0 indicate no error
     * @throws Exception
     */
    public int download(String host, int port, String fileName, long offset) throws Exception {
        TTransport transport = new TSocket(host, port);
        transport.open();
        TProtocol protocol = new TBinaryProtocol(transport);
        TFileService.Client client = new TFileService.Client(protocol);
        int code = perform(client, fileName, offset);
        transport.close();
        return code;
    }

    /**
     * download file performance
     * @param client
     * @param fileName
     * @param start
     * @return err code
     * @throws Exception
     */
    private int perform(TFileService.Client client, String fileName, long start) throws Exception {
        long fetch = start + 1; // have fetched data
        TFileInfo info = client.open(fileName, start);

        /* start writing */
        writing(fileName, start, info);

        /* start receive file chunk from the server */
        while (fetch < info.length) {
            TFileChunk chunk = client.getChunk();
            if(chunk != null) {
                queue.put(chunk);
                fetch += chunk.length;
            }
        }

        /* check md5 */
        int code = checking(info, fileName);

        client.close();

        return code;
    }

    /**
     * start writing thread
     * @throws Exception
     */
    private void writing(String fileName, long start, TFileInfo info) throws Exception {
        writeThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    boolean isTaken = false;
                    RandomAccessFile raf = new RandomAccessFile(new File(relPath + fileName),
                            "rw");
                    long write = start + 1;

                    /* writing */
                    while (!isTaken) {
                        TFileChunk chunk = queue.take();
                        byte[] bytes = chunk.getBytes();
                        raf.write(bytes, 0, (int) chunk.getLength());
                        write += chunk.length;
                        if(write == info.length)
                            isTaken = true;
                    }
                    raf.close();
                } catch (Throwable e) {
                    LOG.error(e.getMessage(), e);
                }
            }
        });
        writeThread.start();
    }

    /**
     * check the md5 code
     * @param info
     * @param fileName
     * @return err code; 0 is no err
     * @throws Exception
     */
    private int checking(TFileInfo info, String fileName) throws Exception {
        writeThread.join();//waiting writing ended
        String src = info.getMd5();
        String des = Utils.md5Hex(relPath + fileName);
        if(src.equals(des))
            return 0;
        else
            return 1;
    }

    /* getter and setter */

    public String getRelPath() {
        return relPath;
    }

    public void setRelPath(String relPath) {
        this.relPath = relPath;
    }

}
