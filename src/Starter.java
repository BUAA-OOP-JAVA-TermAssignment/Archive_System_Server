import controller.UploadController;
import dao.domain.Document;
import dao.lucene.LuceneCore;
import server.ServerCore;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;
import java.util.Scanner;

/**
 * @author pcpas
 */
public class Starter {

    public static void main(String[] args) {
        //初始化网络
        try {
            ServerCore.getMyServer().runServer();
        } catch (IOException e) {
            System.out.println("Server initialize failed!");
            return;
        }
        //初始化Lucene
        try {
            LuceneCore.getInstance().buildIndex();
        } catch (SQLException | IOException e) {
            System.out.println("Lucene initialize failed!");
            return;
        }

        //服务器退出
        Scanner scanner = new Scanner(System.in);
        if ("QUIT".equals(scanner.next())) {
            try {
                ServerCore.getMyServer().closeServer();
            } catch (IOException e) {
                System.out.println("Close failed!");
                return;
            }
        }
    }

    static void addFile() {
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
