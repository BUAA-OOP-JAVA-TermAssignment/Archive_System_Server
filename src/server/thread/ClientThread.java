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
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            oos.writeObject(msg);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Send back message failed!");
        }
    }

    /**
     * 将文件下载给该客户端
     *
     * @param filePath 文件的地址
     * @return true 成功 false 失败
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
            outputStream.flush();
            byte[] bytes = {'E', 'O', 'F'};
            outputStream.write(bytes);
            outputStream.flush();
            bis.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Download in thread failed!");
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
                if (socket.isClosed()) {
                    System.out.println(socket + "lost connection!");
                    return;
                }
                //以message为单位不断接受客户端传来的数据
                ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
                BaseMsg msg = (BaseMsg) ois.readObject();
                System.out.println("Receive msg");
                if (msg.getMsgCode() == BaseMsg.DIS_CONNECT) {
                    System.out.println(socket + "disconnect");
                    return;
                }
                Thread.sleep(1000);
                BaseRequst request = msgToRequst(msg);

                if (request != null) {
                    request.execute();
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            System.out.println(this.socket + "lost connection!");
        } catch (InterruptedException e) {
            e.printStackTrace();
            System.out.println("模拟网络延迟失败");
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
            case BaseMsg.LOGIN:
                System.out.println("receive msg LOGIN");
                return new UserLoginRequst((UserLoginRequestMsg) msg, this);

            case BaseMsg.DOWNLOAD_FILE_REQUEST:
                System.out.println("receive msg DOWNLOAD_FILE_REQUEST");
                return new DownloadRequest((DownloadRequestMsg) msg, this);

            case BaseMsg.REGISTER:
                System.out.println("receive msg REGISTER");
                return new UserRegisterRequest((UserRegisterRequestMsg) msg, this);

            case BaseMsg.MODIFY_USER_INFO:
                System.out.println("receive msg MODIFY_USER_INFO");
                return new ModifyUserInfoRequest((ModifyUserInfo) msg, this);

            case BaseMsg.SEARCH_ARCHIVE:
                System.out.println("receive msg SEARCH_ARCHIVE");
                return new SearchRequest((SearchRequestMsg) msg, this);

            case BaseMsg.ADMIN_USER_EDIT:
                System.out.println("receive msg ADMIN_USER_EDIT");
                return new AdminUserEditRequest((AdminUserEditMsg) msg, this);

            case BaseMsg.ADMIN_USER_SEND:
                System.out.println("receive msg ADMIN_USER_SEND");
                return new UserListRequest(this);

            default:
                System.out.println("undefined msg " + msg.getMsgCode());
                return null;
        }
    }
}

