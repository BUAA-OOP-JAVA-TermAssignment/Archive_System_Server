import controller.LoginController;
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

        ServerCore.runServer();

        //LoginController.getInstance().adminLoginCheck("pcpa", "123456");

    }
}
