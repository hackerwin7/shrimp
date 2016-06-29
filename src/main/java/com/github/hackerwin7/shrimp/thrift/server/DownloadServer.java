package com.github.hackerwin7.shrimp.thrift.server;

import com.github.hackerwin7.shrimp.thrift.gen.TDFileService;
import com.github.hackerwin7.shrimp.thrift.impl.TDFileServiceHandler;
import org.apache.log4j.Logger;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TThreadPoolServer;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TServerTransport;

/**
 * Created by IntelliJ IDEA.
 * User: hackerwin7
 * Date: 2016/06/15
 * Time: 2:31 PM
 * Desc: file transfer server
 * Tips:
 */
public class DownloadServer {

    /* logger */
    private static final Logger LOG = Logger.getLogger(DownloadServer.class);

    /* driver */
    private TDFileServiceHandler handler;
    private TDFileService.Processor processor;

    /* data */
    private int port = 9090;

    /**
     * download server
     * @throws Exception
     */
    public void start(int port) throws Exception {
        this.port = port;
        handler = new TDFileServiceHandler();
        processor = new TDFileService.Processor(handler);
        Runnable simple = new Runnable() {
            @Override
            public void run() {
                try {
                    simple(processor);
                } catch (Throwable e) {
                    LOG.error(e.getMessage(), e);
                }
            }
        };
        new Thread(simple).start();
    }

    /**
     * simple connection
     * @param processor
     * @throws Exception
     */
    private void simple(TDFileService.Processor processor) throws Exception {
        TServerTransport serverTransport = new TServerSocket(port);
        TServer server = new TThreadPoolServer(new TThreadPoolServer.Args(serverTransport).processor(processor));
        server.serve();
    }
}
