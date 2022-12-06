package server.thread;

import dao.domain.User;
import message.AdminLoginMsg;
import message.BaseMsg;
import message.UserLoginMsg;
import request.AdminLoginRequst;
import request.BaseRequst;
import request.UserLoginRequst;

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
                BaseRequst requst = msgToRequst(msg);
                assert requst != null;
                requst.execute();
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private BaseRequst msgToRequst(BaseMsg msg) {
        if (msg instanceof UserLoginMsg) {
            return new UserLoginRequst((UserLoginMsg) msg, this);
        } else if (msg instanceof AdminLoginMsg) {
            return new AdminLoginRequst((AdminLoginMsg) msg, this);
        }
        return null;
    }
}
