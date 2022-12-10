package message;

import java.util.ArrayList;
import java.util.Date;

/**
 * �������������Ƽ������
 * �������صĽ������Ŀ���Լ������ĵ���Ҫ��Ϣ��
 * ��Ҫ��Ϣ���������⣬���ߣ��ؼ��ʺ�ժҪ
 *
 * @author : AkashiSensei
 * &#064;date : 2022/12/8 22:04
 */
public class SearchReturnMsg extends BaseMsg {

    private ArrayList<Book> bookArrayList;

    class Book {
        private String Id;
        private String name;
        private String author;

        private int downloadCnt;
        private String matchedText;

        Book(String id, String name, String author, String matchedText, String downloadCnt) {
            this.Id = id;
            this.name = name;
            this.author = author;
            this.matchedText = matchedText;
            this.downloadCnt = Integer.parseInt(downloadCnt);
        }
    }

    public SearchReturnMsg() {
        super(-SEARCH_ARCHIVE);
        this.bookArrayList = new ArrayList<>();
    }

    public void addDoc(String id, String name, String author, String matchedText, String downloadCnt) {
        bookArrayList.add(new Book(id, name, author, matchedText, downloadCnt));
    }

    public String getBookId(int inx) {
        return bookArrayList.get(inx).Id;
    }

    public String getBookName(int inx) {
        return bookArrayList.get(inx).name;
    }

    public String getBookAuthor(int inx) {
        return bookArrayList.get(inx).author;
    }

    public String getBookMatchedText(int inx) {
        return bookArrayList.get(inx).matchedText;
    }

    public int getBookDownloadCnt(int inx) {
        return bookArrayList.get(inx).downloadCnt;
    }
}
