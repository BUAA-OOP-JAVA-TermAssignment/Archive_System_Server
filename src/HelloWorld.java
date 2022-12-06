import controller.LoginController;
import dao.domain.User;
import server.ServerCore;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author pcpas
 */
public class HelloWorld {

    public static void main(String[] args) throws IOException {

        ServerCore.getMyServer().runServer();

        //LoginController.getInstance().adminLoginCheck("pcpa", "123456");
//
//        User u = new User();
//        u.setUserName("zzq");
//        u.setPassword("123456");
//        u.setEmail("qq@qq.com");
        //LoginController.getInstance().userRegister(u);
        //LoginController.getInstance().userLoginCheck("zzq", "123456");
    }
}
