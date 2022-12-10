package server.thread;

import dao.domain.User;
import message.BaseMsg;
import message.UserLoginRequestMsg;
import message.UserRegisterRequestMsg;
import request.BaseRequst;
import request.UserLoginRequst;
import request.UserRegisterRequest;

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
            System.out.println("返回报文！");
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            oos.writeObject(msg);
            System.out.println("成功返回报文！");
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
                System.out.println("成功收到报文！");
                BaseRequst request = msgToRequst(msg);
                assert request != null;
                request.execute();
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private BaseRequst msgToRequst(BaseMsg msg) {
        switch (msg.getMsgCode()){
            case 1 << 3 : {
                System.out.println("成功解析报文1！");
                return new UserLoginRequst((UserLoginRequestMsg) msg, this);
            }
            case 1 << 4 : {
                System.out.println("成功解析报文1！");
                return new UserRegisterRequest((UserRegisterRequestMsg) msg, this);
            }
        }
        return null;
    }
}
