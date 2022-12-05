package server.thread;

import controller.LoginController;

import java.io.*;
import java.net.Socket;

public class LoginThread implements Runnable {

    private Socket socket;

    public LoginThread(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        //获取客户端输出流,读取账号密码
        InputStream is = null;
        try {
            is = socket.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        assert is != null;
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(is));
        String account = null;
        String password = null;
        try {
            account = bufferedReader.readLine();
            password = bufferedReader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //把读取到的信息传送给LoginController
        LoginController.getInstance().adminLoginCheck(account, password);
    }
}
