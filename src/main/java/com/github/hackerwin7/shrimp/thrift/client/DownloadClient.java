package com.github.hackerwin7.shrimp.thrift.client;

import com.github.hackerwin7.shrimp.common.Err;
import com.github.hackerwin7.shrimp.common.Utils;
import com.github.hackerwin7.shrimp.thrift.gen.TFileChunk;
import com.github.hackerwin7.shrimp.thrift.gen.TFileInfo;
import com.github.hackerwin7.shrimp.thrift.gen.TDFileService;
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
public class DownloadClient {

    /* logger */
    private static final Logger LOG = Logger.getLogger(DownloadClient.class);

    /* constants */
    public static final int QLEN = 1024;

    /* relative path */
    private String relPath = "src/test/resources";

    /* queue */
    private BlockingQueue<TFileChunk> queue = new LinkedBlockingQueue<>(QLEN);

    /* write thread */
    private Thread writeThread = null;

    /* error code */
    private Err error = null;

    /**
     * start client to download the file
     * @param host
     * @param port
     * @param fileName
     * @param offset
     * @return Err class
     * @throws Exception
     */
    public Err download(String host, int port, String fileName, long offset) throws Exception {

        /* build error code */
        error = new Err();
        error.setCommitOffset(offset);

        /* start client */
        TTransport transport = new TSocket(host, port);
        transport.open();
        TProtocol protocol = new TBinaryProtocol(transport);
        TDFileService.Client client = new TDFileService.Client(protocol);
        try {
            perform(client, fileName, offset);
        } catch (Exception | Error e) {
            LOG.error(e.getMessage(), e);
            error.setErrCode(Err.DOWNLOAD_FAIL);
        }
        transport.close();
        return error;
    }

    /**
     * download file performance
     * @param client
     * @param fileName
     * @param start
     * @return err code
     * @throws Exception
     */
    private int perform(TDFileService.Client client, String fileName, long start) throws Exception {
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
                //record write offset for error code using
                long write = start + 1;
                try {
                    boolean isTaken = false;
                    RandomAccessFile raf = new RandomAccessFile(new File(relPath + fileName),
                            "rw");
                    raf.seek(start);

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
                    error.setErrCode(Err.DOWNLOAD_FAIL);
                    error.setCommitOffset(write - 1);//offset not fetch account
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
        if(error.getErrCode() != Err.OK)
            return -1;// before checking , there has been a error occured
        String src = info.getMd5();
        String des = Utils.md5Hex(relPath + fileName);
        if(src.equals(des)) {
            error.setErrCode(Err.OK);
            return 0;
        } else {
            error.setErrCode(Err.MD5_CHECKING);
            return Err.MD5_CHECKING;
        }
    }

    /* getter and setter */

    public String getRelPath() {
        return relPath;
    }

    public void setRelPath(String relPath) {
        this.relPath = relPath;
    }

}
