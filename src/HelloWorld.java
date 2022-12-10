import controller.LoginController;
import controller.UploadController;
import dao.domain.Document;
import dao.domain.User;
import dao.lucene.LuceneCore;
import dao.utils.FileUtils;
import server.ServerCore;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

/**
 * @author pcpas
 */
public class HelloWorld {

    public static void main(String[] args) {
        ServerCore.getMyServer().runServer();

    }

    void lucene() throws IOException {
        LuceneCore lc = new LuceneCore();
        lc.buildIndex();
        lc.search("我爱JAVA");
    }


}
