package server;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import server.thread.WaitForClientThread;

import java.io.*;
import java.net.ServerSocket;
import java.util.concurrent.*;

public class ServerCore {

    private static ServerCore serverCore;

    private ServerCore() {
    }

    /**
     * 单例获取服务器对象
     *
     * @return
     */
    public static ServerCore getMyServer() {
        if (serverCore == null) {
            serverCore = new ServerCore();
        }
        return serverCore;
    }

    /**
     * 服务器套接字
     */
    ServerSocket server = null;

    private static final ThreadFactory namedThreadFactory = new ThreadFactoryBuilder()
            .setNameFormat("demo-pool-%d").build();
    /**
     * 新建线程池
     * <p>
     * corePoolSize:5
     * maximumPoolSize:200
     * keepAliveTime:0L
     */
    public static final ExecutorService POOL = new ThreadPoolExecutor(5, 200,
            0L, TimeUnit.MILLISECONDS,
            new LinkedBlockingQueue<Runnable>(1024), namedThreadFactory, new ThreadPoolExecutor.AbortPolicy());

    /**
     * 开始接受客户端请求
     *
     * @throws IOException
     */
    public boolean runServer() {
        try {
            server = new ServerSocket(8888);
            System.out.println("服务器启动成功");
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        POOL.execute(new WaitForClientThread(server));
        return false;
    }


}
