package message;

import com.mysql.cj.x.protobuf.MysqlxNotice;

import java.util.ArrayList;
import java.util.Date;

/**
 * 返回搜索或者推荐结果。
 * 包含返回的结果的数目，以及各份文档简要信息。
 * 简要信息包括，标题，作者，关键词和摘要
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
        private float score;
        private String matchedText;

        Book(String id, String name, String author, String matchedText, String downloadCnt, float score) {
            this.Id = id;
            this.name = name;
            this.author = author;
            this.matchedText = matchedText;
            this.downloadCnt = Integer.parseInt(downloadCnt);
            this.score = score;
        }
    }

    public SearchReturnMsg() {
        super(-SEARCH_ARCHIVE);
        this.bookArrayList = new ArrayList<>();
    }

    public void addDoc(String id, String name, String author, String matchedText, String downloadCnt, float score) {
        bookArrayList.add(new Book(id, name, author, matchedText, downloadCnt, score));
    }

    public int getBookNum() {
        return bookArrayList.size();
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

    public float gteBookMatchedScore(int inx) {
        return bookArrayList.get(inx).score;
    }
}
