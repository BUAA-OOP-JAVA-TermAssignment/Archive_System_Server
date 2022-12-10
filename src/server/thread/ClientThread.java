package server.thread;

import dao.domain.User;
import message.BaseMsg;
import message.DownloadRequestMsg;
import message.UserLoginRequestMsg;
import request.BaseRequst;
import request.DownloadRequest;
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

        switch (msg.getMsgCode()) {
            case BaseMsg.LOGIN:
                return new UserLoginRequst((UserLoginRequestMsg) msg, this);
            case BaseMsg.DOWNLOAD_FILE_REQUEST:
                System.out.println("receive downloadMsg!");
                return new DownloadRequest((DownloadRequestMsg) msg, this);
            default:
        }

        return null;
    }
}
