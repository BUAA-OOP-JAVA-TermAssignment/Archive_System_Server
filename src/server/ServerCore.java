package server;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import server.thread.LoginThread;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.*;

public class ServerCore {

    private static final ThreadFactory namedThreadFactory = new ThreadFactoryBuilder()
            .setNameFormat("demo-pool-%d").build();
    /**
     * 新建线程池
     *
     * corePoolSize:5
     * maximumPoolSize:200
     * keepAliveTime:0L
     */
    private static final ExecutorService POOL = new ThreadPoolExecutor(5, 200,
            0L, TimeUnit.MILLISECONDS,
            new LinkedBlockingQueue<Runnable>(1024), namedThreadFactory, new ThreadPoolExecutor.AbortPolicy());

    /**
     * 开始接受客户端请求
     * @throws IOException
     */
    public static void runServer() throws IOException {
        ServerSocket serverSocket = new ServerSocket(8888);
        System.out.println("服务器socket创建成功");

        while (true) {
            Socket socket = serverSocket.accept();
            POOL.execute(new LoginThread(socket));
        }

    }
}
