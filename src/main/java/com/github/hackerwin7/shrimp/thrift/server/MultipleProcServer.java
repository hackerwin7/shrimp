package com.github.hackerwin7.shrimp.thrift.server;

import com.github.hackerwin7.shrimp.thrift.gen.TControllerService;
import com.github.hackerwin7.shrimp.thrift.gen.TDFileService;
import com.github.hackerwin7.shrimp.thrift.gen.TTransService;
import com.github.hackerwin7.shrimp.thrift.gen.TUFileService;
import com.github.hackerwin7.shrimp.thrift.impl.TControllerServiceHandler;
import com.github.hackerwin7.shrimp.thrift.impl.TDFileServiceHandler;
import com.github.hackerwin7.shrimp.thrift.impl.TTransServiceHandler;
import com.github.hackerwin7.shrimp.thrift.impl.TUFileServiceHandler;
import org.apache.log4j.Logger;
import org.apache.thrift.TMultiplexedProcessor;
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
public class MultipleProcServer {

    /* logger */
    private static final Logger LOG = Logger.getLogger(MultipleProcServer.class);

    /* driver */
    private TMultiplexedProcessor processor = new TMultiplexedProcessor();

    /* data */
    private int port = 9090;

    /* path */
    private String downPath = null;
    private String upPath = null;
    private String transPath = null;

    /**
     * download server
     * @throws Exception
     */
    public void start(int port) throws Exception {
        this.port = port;
        TDFileServiceHandler down = new TDFileServiceHandler();
        down.setRelPath(downPath);
        TUFileServiceHandler up = new TUFileServiceHandler();
        up.setRelPath(upPath);
        TTransServiceHandler trans = new TTransServiceHandler();
        trans.setRelPath(transPath);
        processor.registerProcessor("Download", new TDFileService.Processor(down));
        processor.registerProcessor("Upload", new TUFileService.Processor(up));
        processor.registerProcessor("Trans", new TTransService.Processor(trans));
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
    private void simple(TMultiplexedProcessor processor) throws Exception {
        TServerTransport serverTransport = new TServerSocket(port);
        TServer server = new TThreadPoolServer(new TThreadPoolServer.Args(serverTransport).processor(processor));
        server.serve();
    }

    /* setter and getter */

    public void setDownPath(String downPath) {
        this.downPath = downPath;
    }

    public void setUpPath(String upPath) {
        this.upPath = upPath;
    }

    public void setTransPath(String transPath) {
        this.transPath = transPath;
    }
}
