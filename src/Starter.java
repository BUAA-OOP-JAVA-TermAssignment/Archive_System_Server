import controller.UploadController;
import dao.DaoSet;
import dao.domain.Admin;
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

        //后台命令
        Scanner scanner = new Scanner(System.in);
        String command;
        while (true) {
            command = scanner.nextLine();
            switch (command) {
                case "ADD ADMIN":
                    addAdmin(scanner);
                    break;
                case "ADD DOCUMENT":
                    addDocument(scanner);
                    break;
                case "QUIT":
                    try {
                        ServerCore.getMyServer().closeServer();
                        return;
                    } catch (IOException e) {
                        System.out.println("Close failed!");
                        return;
                    }
                default:
                    System.out.println("Undefined command!");
            }
        }
    }



    static void addFile1() {
        //导入一些本地文件测试
        for (int i = 1; i <= 9; i++) {
            String id = "EX_000" + i;
            String name = "SPhomework" + i;
            String author = "pcpas";
            String path = "D:\\Archive_System\\origin_pdfs\\EX_000" + i + ".pdf";
            Document doc = new Document(id, name, author, new Date().toString(), 0);
            UploadController.getInstance().saveDocument(doc, path);
        }
    }

    static void addFile2() {
        for (int i = 10; i <= 16; i++) {
            String id = "EX_00" + i;
            String name = "SPhomework" + i;
            String author = "pcpas";
            String path = "D:\\Archive_System\\origin_pdfs\\EX_00" + i + ".pdf";
            Document doc = new Document(id, name, author, new Date().toString(), 0);
            UploadController.getInstance().saveDocument(doc, path);
        }
    }

    static void addAdmin(Scanner sc) {
        String id, name, password, email, time;
        System.out.println("请输入管理员学工号:");
        id = sc.nextLine();
        System.out.println("请输入管理员姓名:");
        name = sc.nextLine();
        System.out.println("请输入管理员密码:");
        password = sc.nextLine();
        System.out.println("请输入管理员邮箱:");
        email = sc.nextLine();
        time = new Date().toString();
        Admin admin = new Admin(id, name, password, email, time);
        if (DaoSet.adminDao.addAdmin(admin)) {
            System.out.println("注册成功!");
        } else {
            System.out.println("出现未知错误，请联系技术人员");
        }

    }

    static void addDocument(Scanner sc) {
        String id, name, author, path;
        System.out.println("请输入文档id:");
        id = sc.nextLine();
        System.out.println("请输入文档名:");
        name = sc.nextLine();
        System.out.println("请输入作者:");
        author = sc.nextLine();
        System.out.println("请输入文档路径:");
        path = sc.nextLine();
        Document doc = new Document(id, name, author, new Date().toString(), 0);
        UploadController.getInstance().saveDocument(doc, path);
    }
}
