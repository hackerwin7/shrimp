package com.github.hackerwin7.shrimp.thrift.client;

import com.github.hackerwin7.shrimp.thrift.gen.TFileInfo;
import com.github.hackerwin7.shrimp.thrift.gen.TTransService;
import org.apache.log4j.Logger;
import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TMultiplexedProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;

/**
 * Created by IntelliJ IDEA.
 * User: hackerwin7
 * Date: 2016/07/06
 * Time: 1:55 PM
 * Desc:
 * Tips:
 */
public class TransClient {

    /* logger */
    private static final Logger LOG = Logger.getLogger(TransClient.class);

    /* data */
    private String host = null;
    private int port = 9091;

    /* driver */
    private TTransport transport = null;
    private TProtocol protocol = null;
    private TMultiplexedProtocol mp = null;
    private TTransService.Client client = null;

    public TransClient(String host, int port) {
        this.host = host;
        this.port = port;
    }

    /**
     * get the file info from the specific host and port
     * @param host
     * @param port
     * @param fileName
     * @return file info
     * @throws Exception
     */
    public TFileInfo shortFileInfo(String host, int port, String fileName) throws TException {
        TTransport transport = new TSocket(host, port);
        transport.open();
        TProtocol protocol = new TBinaryProtocol(transport);
        TMultiplexedProtocol mp = new TMultiplexedProtocol(protocol, "Trans");
        TTransService.Client client = new TTransService.Client(mp);
        TFileInfo info = perform(client, fileName);
        transport.close();
        return info;
    }

    /**
     * request a file info to the server
     * @param client
     * @param fileName
     * @return file info
     * @throws Exception
     */
    private TFileInfo perform(TTransService.Client client, String fileName) throws TException {
        return client.getFileInfo(fileName);
    }

    /**
     * open the conn
     * @throws TException
     */
    public void open() throws TException {
        transport = new TSocket(host, port);
        transport.open();
        protocol = new TBinaryProtocol(transport);
        mp = new TMultiplexedProtocol(protocol, "Trans");
        client = new TTransService.Client(mp);
    }

    /**
     * get the file info from the server
     * @param fileName
     * @return file info
     * @throws Exception
     */
    public TFileInfo fileInfo(String fileName) throws TException {
        return client.getFileInfo(fileName);
    }

    /**
     * close the conn
     * @throws TException
     */
    public void close() throws TException {
        transport.close();
    }
}
