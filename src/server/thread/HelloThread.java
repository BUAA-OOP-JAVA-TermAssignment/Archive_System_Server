package server.thread;

import java.io.*;
import java.net.Socket;

/**
 * @author pcpas
 */
public class HelloThread implements Runnable {
    private Socket socket;

    public HelloThread(Socket socket) {
        this.socket = socket;

    }

    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName()+":");
        //获取输入流，回写给客户端
        OutputStream os = null;
        try {
            os = socket.getOutputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        assert os != null;
        //回写给客户端“hello client”
        PrintStream printStream = new PrintStream(os);
        printStream.println("Hello!");
        //获取客户端输出流
        InputStream is = null;
        try {
            is = socket.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        assert is != null;
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(is));
        String text = null;
        try {
            text = bufferedReader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
