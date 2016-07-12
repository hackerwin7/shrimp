package com.github.hackerwin7.shrimp.thrift.client;

import com.github.hackerwin7.shrimp.common.Err;
import com.github.hackerwin7.shrimp.common.Utils;
import com.github.hackerwin7.shrimp.thrift.gen.TFileChunk;
import com.github.hackerwin7.shrimp.thrift.gen.TFileInfo;
import com.github.hackerwin7.shrimp.thrift.gen.TDFileService;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TMultiplexedProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;

import java.io.File;
import java.io.RandomAccessFile;
import java.util.List;
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

    /* relative path , complete file move from ing to ed*/
    private String ingPath = "src/test/resources";
    private String edPath = null;

    /* queue */
    private BlockingQueue<TFileChunk> queue = new LinkedBlockingQueue<>(QLEN);

    /* write thread */
    private Thread writeThread = null;

    /* error code */
    private Err error = null;

    /* controller */
    private ControllerClient controller = null;

    public DownloadClient() throws Exception {
        controller = new ControllerClient();
    }

    /**
     * controller is needed
     * @param cont
     */
    public DownloadClient(ControllerClient cont) {
        controller = cont;
    }

    /**
     * start client to download the file
     * @param host
     * @param port
     * @param fileName
     * @param offset , this offset indicate that the current offset has not been write
     *               start is the offset which haven't been write, write = (start - 1) + 1, (start - 1) indicate the offset have been write
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
        TMultiplexedProtocol mp = new TMultiplexedProtocol(protocol, "Download");
        TDFileService.Client client = new TDFileService.Client(mp);
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
     * download with the controller
     * @param fileName
     * @param offset
     * @return error code
     * @throws TException
     */
    public Err download(String fileName, long offset) throws TException {
        //controller find the target to download
        try {
            controller.open();
            List<String> hps = controller.servers(fileName);
            if(hps.size() == 0)
                throw new Exception("no server have the file " + fileName);
            String[] arr = StringUtils.split(hps.get(0), ":");
            String host = arr[0];
            int port = Integer.parseInt(arr[1]);
            Err err = download(host, port, fileName, offset);
            controller.close();
            return err;
        } catch (Exception | Error e) {
            LOG.error(e.getMessage(), e);
            throw new TException("controller encounter error !!!");
        }
    }

    /**
     * default offset is 0
     * @param fileName
     * @return error code
     * @throws TException
     */
    public Err download(String fileName) throws TException {
        return download(fileName, 0);
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
        long fetch = start; // start is offset that haven't been write
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

        /* move the file from ing to ed if ok */
        if (error.getErrCode() == Err.OK)
            moving(fileName);

        return code;
    }

    /**
     * move the download complete file from downloading to downloaded
     * @param fileName
     * @throws Exception
     */
    private void moving(String fileName) throws Exception {
        String path = ingPath + fileName;
        File file = new File(path);
        boolean code = file.renameTo(new File(edPath + fileName));
        if(!code)
            throw new Exception("move file failed......");
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
                long write = start; // start is the offset which haven't been write, write = (start - 1) + 1, (start - 1) indicate the offset have been write
                try {
                    boolean isTaken = false;
                    RandomAccessFile raf = new RandomAccessFile(new File(ingPath + fileName),
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
                    error.setCommitOffset(write);//write - 1 is the offset which have been write , we record the first offset haven't been write
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
        String des = Utils.md5Hex(ingPath + fileName);
        if(src.equals(des)) {
            error.setErrCode(Err.OK);
            return 0;
        } else {
            error.setErrCode(Err.MD5_CHECKING);
            return Err.MD5_CHECKING;
        }
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

    public void setController(ControllerClient controller) {
        this.controller = controller;
    }

}
