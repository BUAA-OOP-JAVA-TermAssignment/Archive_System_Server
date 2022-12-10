package server.thread;

import dao.domain.User;
import message.*;
import request.*;

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

    /**
     * 向客户端发送报文
     *
     * @param msg
     */
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

    /**
     * 将文件下载给该客户端
     *
     * @param filePath 文件的地址
     * @return 结果
     */
    public boolean downloadFile(String filePath) {
        try {
            OutputStream outputStream = socket.getOutputStream();
            BufferedInputStream bis = new BufferedInputStream(new FileInputStream(filePath));
            byte[] data = new byte[1024];
            int len = 0;
            while ((len = bis.read(data)) != -1) {
                outputStream.write(data, 0, len);
            }
            bis.close();
            outputStream.close();

        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }


    /**
     * 一个线程代表一个客户端
     */
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

    /**
     * 将msg转换为交由服务端处理的请求
     *
     * @param msg
     * @return
     */
    private BaseRequst msgToRequst(BaseMsg msg) {

        switch (msg.getMsgCode()) {
            case BaseMsg.LOGIN -> {
                System.out.println("成功解析报文3！");
                return new UserLoginRequst((UserLoginRequestMsg) msg, this);
            }
            case BaseMsg.DOWNLOAD_FILE_REQUEST -> {
                System.out.println("receive downloadMsg!");
                return new DownloadRequest((DownloadRequestMsg) msg, this);
            }
            case BaseMsg.REGISTER -> {
                System.out.println("成功解析报文4！");
                return new UserRegisterRequest((UserRegisterRequestMsg) msg, this);
            }
            case BaseMsg.MODIFY_USER_INFO -> {
                System.out.println("成功解析报文7！");
                return new ModifyUserInfoRequest((ModifyUserInfo) msg, this);
            }
            default -> {
                return null;
            }
        }
    }
}
