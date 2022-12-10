package message;

import java.util.ArrayList;
import java.util.Date;

/**
 * 管理员编辑文档的操作消息
 */
public class AdminArchiveEditMsg extends BaseMsg{
    public static final int REQUEST_INFO = 0;
    private final int adminArchiveEditCode;
    //TODO:还没有设计具体包含什么

    private ArrayList<Book> bookArrayList;
    class Book{
        private String Id;
        private String name;
        private String author;
        private String publish;
        private String introduction;
        private String language;
        private Date uploadDate;
        private int downloadCnt;
    }
    public AdminArchiveEditMsg(int adminArchiveEditCode, ArrayList<Book> bookArrayList) {
        super(ADMIN_ARCHIVE_EDIT);
        this.adminArchiveEditCode = adminArchiveEditCode;
        this.bookArrayList = bookArrayList;
    }

    public static AdminArchiveEditMsg createAdminArchiveEditMsg(int adminArchiveEditCode, ArrayList<AdminArchiveEditMsg.Book> bookArrayList){
        return new AdminArchiveEditMsg(adminArchiveEditCode,bookArrayList);
    }

    public int getBookNum(){return bookArrayList.size();}
    public Book getBookInfo(int i){return bookArrayList.get(i);}
    public String getBookId(Book book){return book.Id;}
    public String getBookName(Book book){return book.name;}
    public String getBookAuthor(Book book){return book.author;}
    public String getBookPublish(Book book){return book.publish;}
    public String getBookLanguage(Book book){return book.language;}
    public String getBookIntroduction(Book book){return book.introduction;}
    public Date getBookUploadDate(Book book){return book.uploadDate;}
    private int getBookDownloadCnt(Book book){return book.downloadCnt;}
}
