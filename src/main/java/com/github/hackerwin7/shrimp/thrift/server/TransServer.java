package com.github.hackerwin7.shrimp.thrift.server;

import com.github.hackerwin7.shrimp.thrift.gen.TFileService;
import com.github.hackerwin7.shrimp.thrift.impl.TFileServiceHandler;
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
public class TransServer {

    /* logger */
    private static final Logger LOG = Logger.getLogger(TransServer.class);

    /* driver */
    private TFileServiceHandler handler;
    private TFileService.Processor processor;

    /* data */
    private int port = 9090;

    /**
     * download server
     * @throws Exception
     */
    public void start() throws Exception {
        handler = new TFileServiceHandler();
        processor = new TFileService.Processor(handler);
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
    private void simple(TFileService.Processor processor) throws Exception {
        TServerTransport serverTransport = new TServerSocket(port);
        TServer server = new TThreadPoolServer(new TThreadPoolServer.Args(serverTransport).processor(processor));
        server.serve();
    }
}
