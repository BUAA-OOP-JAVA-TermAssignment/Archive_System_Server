package message;

import java.util.ArrayList;
import java.util.Date;

/**
 * 管理员编辑文档的操作消息
 *
 * @author : AkashiSensei
 * @date : 2022/12/8 21:00
 */
public class AdminArchiveEditMsg extends BaseMsg{
    public static final int REQUEST_INFO = 0;
    private final int adminArchiveEditCode;
    //TODO:还没有设计具体包含什么

    Book book;

    class Book{
        private String Id;
        private String name;
        private String author;
        private String publish;
        private String introduction;
        private String language;
        private String uploadDate;
        private int downloadCnt;
    }
    public AdminArchiveEditMsg(int adminArchiveEditCode, Book book) {
        super(ADMIN_ARCHIVE_EDIT);
        this.adminArchiveEditCode = adminArchiveEditCode;
        this.book = book;
    }

    public static AdminArchiveEditMsg createAdminArchiveEditMsg(int adminArchiveEditCode, Book book){
        return new AdminArchiveEditMsg(adminArchiveEditCode,book);
    }

    public String getBookId(){return book.Id;}
    public String getBookName(){return book.name;}
    public String getBookAuthor(){return book.author;}
    public String getBookPublish(){return book.publish;}
    public String getBookLanguage(){return book.language;}
    public String getBookIntroduction(){return book.introduction;}
    public String getBookUploadDate(){return book.uploadDate;}
    private int getBookDownloadCnt(){return book.downloadCnt;}
}
