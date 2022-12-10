package server.thread;

import server.ServerCore;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 等待client的连接
 *
 * @author pcpas
 */
public class WaitForClientThread extends Thread {
    ServerSocket server;

    public WaitForClientThread(ServerSocket server) {
        this.server = server;
    }

    @Override
    public void run() {
        try {
            while (true) {
                Socket socket = server.accept();
                System.out.println(socket + "连接服务器");
                ServerCore.POOL.execute(new ClientThread(socket));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
