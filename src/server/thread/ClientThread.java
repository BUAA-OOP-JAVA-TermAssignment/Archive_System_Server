package server.thread;

import dao.domain.User;
import message.BaseMsg;

import java.io.*;
import java.net.Socket;

/**
 * @author pcpas
 */
public class ClientThread implements Runnable {

    private User user;
    private Socket socket;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Socket getClientSocket() {
        return socket;
    }

    public ClientThread(Socket socket) {
        this.socket = socket;
    }

    public void sendMsgBack(BaseMsg msg) {
        try {
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            oos.writeObject(msg);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            while (true) {
                //以message为单位不断接受客户端传来的数据
                ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
                BaseMsg msg = (BaseMsg) ois.readObject();
                msg.setThread(this);
                System.out.println("收到数据" + msg);
                msg.execute();
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
