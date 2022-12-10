package message;

import java.util.ArrayList;
import java.util.Date;

public class SearchReturnMsg extends BaseMsg{

    private ArrayList<AdminArchiveEditMsg.Book> bookArrayList;
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

    public SearchReturnMsg(ArrayList<AdminArchiveEditMsg.Book> bookArrayList) {
        super(- SEARCH_ARCHIVE);
        this.bookArrayList = bookArrayList;
    }

    public static SearchReturnMsg createSearchReturnMsg(ArrayList<AdminArchiveEditMsg.Book> bookArrayList){
        return new SearchReturnMsg(bookArrayList);
    }

    public int getBookNum(){return bookArrayList.size();}
    public AdminArchiveEditMsg.Book getBookInfo(int i){return bookArrayList.get(i);}
    public String getBookId(SearchReturnMsg.Book book){return book.Id;}
    public String getBookName(SearchReturnMsg.Book book){return book.name;}
    public String getBookAuthor(SearchReturnMsg.Book book){return book.author;}
    public String getBookPublish(SearchReturnMsg.Book book){return book.publish;}
    public String getBookLanguage(SearchReturnMsg.Book book){return book.language;}
    public String getBookIntroduction(SearchReturnMsg.Book book){return book.introduction;}
    public Date getBookUploadDate(SearchReturnMsg.Book book){return book.uploadDate;}
    private int getBookDownloadCnt(SearchReturnMsg.Book book){return book.downloadCnt;}
}
