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

    /* client id */
    private String clientId = null;

    /* downloaded file info */
    private TFileInfo downInfo = null;

    /**
     * default constructor
     * @throws Exception
     */
    public DownloadClient() throws Exception {

    }

    /**
     * zk args
     * @param zks
     * @throws Exception
     */
    public DownloadClient(String zks) throws Exception {
        controller = new ControllerClient(zks);
    }

    /**
     * controller is needed
     * @param cont
     */
    public DownloadClient(ControllerClient cont) {
        controller = cont;
    }

    /**
     * startCon client to download the file
     * @param host
     * @param port
     * @param fileName
     * @param offset , this offset indicate that the current offset has not been write
     *               startCon is the offset which haven't been write, write = (startCon - 1) + 1, (startCon - 1) indicate the offset have been write
     * @return Err class
     * @throws Exception
     */
    public Err download(String host, int port, String fileName, long offset) throws Exception {

        /* generate client id */
        clientId = Utils.getClientId("download", fileName, offset);

        /* build error code */
        error = new Err();
        error.setCommitOffset(offset);

        /* startCon client */
        TTransport transport = null;
        try {
            transport = new TSocket(host, port);
            transport.open();
            TProtocol protocol = new TBinaryProtocol(transport);
            TMultiplexedProtocol mp = new TMultiplexedProtocol(protocol, "Download");
            TDFileService.Client client = new TDFileService.Client(mp);
            perform(client, fileName, offset);
        } catch (Exception | Error e) {
            LOG.error(e.getMessage(), e);
            error.setErrCode(Err.DOWNLOAD_FAIL);
        } finally {
            if(transport != null)
                transport.close();
        }
        return error;
    }

    /**
     * download with the controller
     * @param fileName
     * @param offset
     * @return error code
     * @throws Exception
     */
    public Err download(String fileName, long offset) throws Exception {
        //controller find the target to download
        Err err = null;
        try {
            controller.open();
            List<String> hps = controller.servers(fileName);
            if(hps.size() == 0)
                throw new Exception("no server have the file " + fileName);
            String[] arr = StringUtils.split(hps.get(0), ":");
            String host = arr[0];
            int port = Integer.parseInt(arr[1]);
            err = download(host, port, fileName, offset);
        } catch (Exception | Error e) {
            LOG.error(e.getMessage(), e);
            throw new TException("controller encounter error !!!");
        } finally {
            if(controller != null)
                controller.close();
        }
        return err;
    }

    /**
     * default offset is 0
     * @param fileName
     * @return error code
     * @throws Exception
     */
    public Err download(String fileName) throws Exception {
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
        long fetch = start; // startCon is offset that haven't been write
        int code = 0;
        try {
            TFileInfo info = client.open(clientId, fileName, start);

            /* startCon writing */
            writing(fileName, start, info);

            /* startCon receive file chunk from the server */
            while (fetch < info.length) {
                //TFileChunk chunk = client.getChunk(clientId);
                TFileChunk chunk = null;
                /* retry when occur exception */
                int tryCnt = 0;
                while (true) {
                    try {
                        chunk = client.getChunk(clientId);
                        break;
                    } catch (TException te) {
                        LOG.error(te.getMessage(), te);
                        Thread.sleep(3000);
                        tryCnt++;
                        if(tryCnt >= 4)//retry 3 times
                            throw new Exception("failed retry 3 times for client get chunk......");
                    }
                }
                if(chunk != null && chunk.getLength() > 0) { // put "not null and not empty" chunk into the queue
                    queue.put(chunk);
                    fetch += chunk.length;
                }
            }

            /* check md5 */
            code = checking(info, fileName);
        } catch (Exception | Error e) {
            LOG.error(e.getMessage(), e);
            error.setErrCode(Err.DOWNLOAD_FAIL);
        } finally {
            if(client != null)
                client.close(clientId);
        }

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
     * startCon writing thread
     * @throws Exception
     */
    private void writing(final String fileName, final long start, final TFileInfo info) throws Exception {
        writeThread = new Thread(new Runnable() {
            @Override
            public void run() {
                //record write offset for error code using
                long write = start; // startCon is the offset which haven't been write, write = (startCon - 1) + 1, (startCon - 1) indicate the offset have been write
                RandomAccessFile raf = null;
                try {
                    boolean isTaken = false;
                    raf = new RandomAccessFile(new File(ingPath + fileName),
                            "rw");
                    raf.setLength(info.getLength());//fixed file length
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

                } catch (Throwable e) {
                    LOG.error(e.getMessage(), e);
                    error.setErrCode(Err.DOWNLOAD_FAIL);
                    error.setCommitOffset(write);//write - 1 is the offset which have been write , we record the first offset haven't been write
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

        /* checking */
        if(error.getErrCode() != Err.OK)
            return -1;// before checking , there has been a error occured

        /* update the download download file info */
        downInfo = getInfo(ingPath, fileName);
        String src = info.getMd5();
        String des = downInfo.getMd5();
        if(src.equals(des)) {
            error.setErrCode(Err.OK);
            return 0;
        } else {
            error.setErrCode(Err.MD5_CHECKING);
            return Err.MD5_CHECKING;
        }
    }

    /**
     * get file info from the file name and the path
     * @param relPath
     * @param name
     * @return file info
     * @throws Exception
     */
    private TFileInfo getInfo(String relPath, String name) throws Exception {
        TFileInfo info = new TFileInfo();
        File file = new File(relPath + name);
        info.setName(name);
        info.setMd5(Utils.md5Hex(relPath + name));
        info.setLength(file.length());
        return info;
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

    public TFileInfo getDownInfo() {
        return downInfo;
    }
}
