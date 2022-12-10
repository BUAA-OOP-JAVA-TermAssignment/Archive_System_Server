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

    public static void main(String[] args) throws IOException {
        LuceneCore lc = new LuceneCore();
        lc.buildIndex();
        lc.search("我爱JAVA");

    }

    private void addFile() {
        //导入一些本地文件测试
        for (int i = 1; i < 6; i++) {
            String id = "EX-000" + i;
            String name = "Love2Java" + i;
            String author = "Hathoric";
            String publisher = "HATHORIC PRESS";
            String ab = "我爱JAVA，特别是JAVA 1." + i;
            String language = "中文";
            String path = "D:\\Archive_System\\origin_pdfs\\EX_000" + i + ".pdf";
            Document doc = new Document(id, name, author, publisher, ab, language, new Date(), 0);
            UploadController.getInstance().saveDocument(doc, path);
        }
    }

}
