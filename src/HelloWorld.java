import controller.UploadController;
import dao.domain.Document;
import dao.lucene.LuceneCore;
import server.ServerCore;

import java.io.IOException;
import java.util.Date;

/**
 * @author pcpas
 */
public class HelloWorld {

    public static void main(String[] args) throws IOException {
        luceneInit();

    }

    void serverInit() {
        ServerCore.getMyServer().runServer();
    }

    static void luceneInit() throws IOException {
        LuceneCore.getInstance().buildIndex();
        LuceneCore.getInstance().search("电路", 0, 5);
    }

    void addFile() {
        //导入一些本地文件测试
        for (int i = 1; i < 6; i++) {
            String id = "EX-000" + i;
            String name = "Love2Java" + i;
            String author = "Hathoric";
            String path = "D:\\Archive_System\\origin_pdfs\\EX_000" + i + ".pdf";
            Document doc = new Document(id, name, author, new Date().toString(), 0);
            UploadController.getInstance().saveDocument(doc, path);
        }
    }

}
