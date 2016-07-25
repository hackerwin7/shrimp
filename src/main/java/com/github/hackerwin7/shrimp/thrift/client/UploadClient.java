package com.github.hackerwin7.shrimp.thrift.client;

import com.github.hackerwin7.shrimp.common.Err;
import com.github.hackerwin7.shrimp.common.Utils;
import com.github.hackerwin7.shrimp.thrift.gen.*;
import org.apache.log4j.Logger;
import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TMultiplexedProtocol;
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
 * Date: 2016/06/27
 * Time: 3:26 PM
 * Desc: upload f**king file chunk
 * Tips:
 */
public class UploadClient {

    /* logger */
    private static final Logger LOG = Logger.getLogger(UploadClient.class);

    /* constants */
    public static final int QLEN = 1024;

    /* relative path */
    private String edPath = "src/test/resources";
    private String ingPath = null;

    /* read thread */
    private Thread readThread = null;

    /* read queue */
    private BlockingQueue<TFileChunk> queue = new LinkedBlockingQueue<>(QLEN);

    /* error code */
    private Err error = null;

    /* data */
    private String zks = null;

    public UploadClient(String zks) {
        this.zks = zks;
    }

    /**
     * startCon client to upload file
     * @param host
     * @param port
     * @param fileName
     * @param offset
     * @return
     * @throws Exception
     */
    public Err upload(String host, int port, String fileName, long offset) throws TException {
        error = new Err();
        error.setCommitOffset(offset);
        TTransport transport = null;
        try {
            transport = new TSocket(host, port);
            transport.open();
            TProtocol protocol = new TBinaryProtocol(transport);
            TMultiplexedProtocol mp = new TMultiplexedProtocol(protocol, "Upload");
            TUFileService.Client client = new TUFileService.Client(mp);
            perform(client, fileName, offset);
        } catch (Exception | Error e) {
            LOG.error(e.getMessage(), e);
            error.setErrCode(Err.UPLOAD_FAIL);
        } finally {
            if(transport != null)
                transport.close();
        }
        return error;
    }

    /**
     * default offset is zero
     * @param host
     * @param port
     * @param fileName
     * @return error code
     * @throws TException
     */
    public Err upload(String host, int port, String fileName) throws TException {
        return upload(host, port, fileName, 0L);
    }

    /**
     * upload a file according to the offset
     * @param client
     * @param fileName
     * @param start
     * @return signal code
     * @throws Exception
     */
    private int perform(TUFileService.Client client, String fileName, long start) throws Exception {

        try {
            TFileInfo info = open(fileName, start);

            //before the upload , inform the controller the file information
            inform(info);

            client.open(info); // server receive the info, and startCon writing to wait the file chunk from the queue

        /* client processing */
            reading(info);// reading and sending
            sending(client, info);
            checking(client, info);
        } catch (Exception | Error e) {
            LOG.error(e.getMessage(), e);
        } finally {
            if(client != null)
                client.close();
        }
        return 0;
    }

    /**
     * inform the controller to update the file info
     * @param info
     * @throws Exception
     */
    private void inform(TFileInfo info) throws Exception {
        ControllerClient controller = null;
        try {
            controller = new ControllerClient(zks);
            controller.open();
            controller.sendInfo(info);
        } catch (Exception | Error e) {
            LOG.error(e.getMessage(), e);
        } finally {
            if(controller != null)
                controller.close();
        }
    }

    /**
     * open the file and get the file info
     * @param fileName
     * @param start
     * @return file info
     * @throws Exception
     */
    private TFileInfo open(String fileName, long start) throws Exception {
        TFileInfo info = new TFileInfo();
        String path = edPath + fileName;
        File file = new File(path);
        info.setTs(System.currentTimeMillis());
        info.setMd5(Utils.md5Hex(path));
        info.setLength(file.length());
        info.setName(fileName);
        info.setStart(start);
        return info;
    }

    /**
     * reading the file and send reading file chunk to the server's queue
     * @param info
     * @throws Exception
     */
    private void reading(final TFileInfo info) throws Exception {
        readThread = new Thread(new Runnable() {
            @Override
            public void run() {
                RandomAccessFile raf = null;
                try {
                    String path = edPath + info.getName();
                    raf = new RandomAccessFile(new File(path), "r");
                    raf.seek(info.getStart());
                    long index = info.getStart();
                    long left = info.length - info.getStart();
                    int read = TransFileConstants.CHUNK_UNIT;
                    if(left < read)
                        read = (int) left;
                    while (index <= info.getLength() - 1) {
                        byte[] bytes = new byte[read];
                        raf.read(bytes, 0, read);
                        TFileChunk chunk = new TFileChunk();
                        chunk.setBytes(bytes);
                        chunk.setName(info.getName());
                        chunk.setLength(read);
                        chunk.setOffset(index);
                        queue.put(chunk);

                        index += read;
                        left = info.getLength() - index;//length is account, index is also account not offset, index = (index - 1) + 1
                        if(left < read)
                            read = (int) left;
                    }
                } catch (Exception | Error e) {
                    LOG.error(e.getMessage(), e);
                    error.setErrCode(Err.UPLOAD_FAIL);
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
        readThread.start();
    }

    /**
     * sending the file chunk from the queue to the server
     * @param client
     * @param info
     * @throws Exception
     */
    private void sending(TUFileService.Client client, TFileInfo info) throws Exception {
        long send = info.getStart();
        while (send < info.getLength()) {
            TFileChunk chunk = queue.take();
            client.sendChunk(chunk);
            send += chunk.getLength();
        }
    }

    /**
     * checking the status of upload
     * @param client
     * @param info
     * @throws Exception
     */
    private void checking(TUFileService.Client client, TFileInfo info) throws Exception {

        /* receive the response of the error */
        TErr te = client.checking(info);
        error.setErrCode(te.getErr());
        error.setCommitOffset(te.getCommit());
    }

    /* getter and setter */

    public void setEdPath(String edPath) {
        this.edPath = edPath;
    }

    public void setIngPath(String ingPath) {
        this.ingPath = ingPath;
    }
}
