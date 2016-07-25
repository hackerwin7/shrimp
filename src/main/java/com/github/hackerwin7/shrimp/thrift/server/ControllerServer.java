package com.github.hackerwin7.shrimp.thrift.server;

import com.github.hackerwin7.shrimp.thrift.gen.TControllerService;
import com.github.hackerwin7.shrimp.thrift.gen.TFilePool;
import com.github.hackerwin7.shrimp.thrift.gen.TOperation;
import com.github.hackerwin7.shrimp.thrift.impl.TControllerServiceHandler;
import org.apache.log4j.Logger;
import org.apache.thrift.TException;
import org.apache.thrift.TMultiplexedProcessor;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TThreadPoolServer;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TServerTransport;

import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: hackerwin7
 * Date: 2016/07/07
 * Time: 11:23 AM
 * Desc:
 * Tips:
 */
public class ControllerServer {

    /* logger */
    private static final Logger LOG = Logger.getLogger(ControllerServer.class);

    /* data */
    private int port = 9097;

    /* driver */
    private TControllerServiceHandler handler = null;

    /* driver */
    private TServer server = null;
    private Thread thread = null;

    /**
     * startCon controller server using the port
     * @param port
     * @throws Exception
     */
    public void start(int port) throws TException {
        this.port = port;
        handler = new TControllerServiceHandler();
        final TMultiplexedProcessor processor = new TMultiplexedProcessor();
        processor.registerProcessor("Controller", new TControllerService.Processor<>(handler));
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
        thread = new Thread(simple);
        thread.start();
    }

    /**
     * default is 9097
     * @throws Exception
     */
    public void start() throws TException {
        start(port);//default is 9097
    }

    /**
     * thrift startCon
     * @param processor
     * @throws Exception
     */
    private void simple(TMultiplexedProcessor processor) throws Exception {
        TServerTransport serverTransport = new TServerSocket(port);
        server = new TThreadPoolServer(new TThreadPoolServer.Args(serverTransport).processor(processor));
        LOG.debug("starting the thrift controller ......");
        server.serve();
    }

    /**
     * close the thrift server
     * @throws Exception
     */
    public void close() throws Exception {
        if(server != null)
            server.stop();
        while (thread.isAlive()) {
            LOG.info("waiting the thrift server thread stopping ......");
            Thread.sleep(3000);
        }
    }

    /**
     * get pools info from the handler
     * @return pools
     */
    public Map<String, TFilePool> getPools() {
        return handler.getPools();
    }

    /* getter and setter */

    public TOperation getOp() throws Exception {
        return handler.getOp();
    }
}
