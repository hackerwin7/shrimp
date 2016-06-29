package com.github.hackerwin7.shrimp.thrift.client;

import com.github.hackerwin7.shrimp.common.Err;
import com.github.hackerwin7.shrimp.common.Utils;
import com.github.hackerwin7.shrimp.thrift.gen.*;
import org.apache.log4j.Logger;
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
    private String relPath = "src/test/resources";

    /* read thread */
    private Thread readThread = null;

    /* read queue */
    private BlockingQueue<TFileChunk> queue = new LinkedBlockingQueue<>(QLEN);

    /* error code */
    private Err error = null;

    /**
     * start client to upload file
     * @param host
     * @param port
     * @param fileName
     * @param offset
     * @return
     * @throws Exception
     */
    public Err upload(String host, int port, String fileName, long offset) throws Exception {
        error = new Err();
        error.setCommitOffset(offset);
        TTransport transport = new TSocket(host, port);
        transport.open();
        TProtocol protocol = new TBinaryProtocol(transport);
        TMultiplexedProtocol mp = new TMultiplexedProtocol(protocol, "Upload");
        TUFileService.Client client = new TUFileService.Client(mp);
        try {
            perform(client, fileName, offset);
        } catch (Exception | Error e) {
            LOG.error(e.getMessage(), e);
            error.setErrCode(Err.UPLOAD_FAIL);
        }
        transport.close();
        return error;
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
        TFileInfo info = open(fileName, start);
        client.open(info); // server receive the info, and start writing to wait the file chunk from the queue

        /* client processing */
        reading(info);// reading and sending
        sending(client, info);
        checking(client, info);

        client.close();
        return 0;
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
        String path = relPath + fileName;
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
    private void reading(TFileInfo info) throws Exception {
        readThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String path = relPath + info.getName();
                    RandomAccessFile raf = new RandomAccessFile(new File(path), "r");
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
                    raf.close();
                } catch (Exception | Error e) {
                    LOG.error(e.getMessage(), e);
                    error.setErrCode(Err.UPLOAD_FAIL);
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

    public void setRelPath(String relPath) {
        this.relPath = relPath;
    }
}
