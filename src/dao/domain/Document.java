package dao.domain;

import com.google.common.util.concurrent.AbstractIdleService;

import java.util.Date;

public class Document {
    private String Id;
    private String name;
    private String author;
    private String content;
    private int downloadCnt;

    public Document() {
    }

    /**
     * Document的不含content构造，content默认为null
     *
     * @param Id
     * @param name
     * @param author
     * @param downloadCnt
     */
    public Document(String Id, String name, String author, int downloadCnt) {
        this.Id = Id;
        this.name = name;
        this.author = author;
        this.content = null;
        this.downloadCnt = downloadCnt;
    }

    /**
     * Document的含content构造
     *
     * @param Id
     * @param name
     * @param author
     * @param content
     * @param downloadCnt
     */
    public Document(String Id, String name, String author, String content, int downloadCnt) {
        this.Id = Id;
        this.name = name;
        this.author = author;
        this.content = content;
        this.downloadCnt = downloadCnt;
    }

    public void setId(String id) {
        this.Id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setDownloadCnt(int downloadCnt) {
        this.downloadCnt = downloadCnt;
    }

    public String getId() {
        return Id;
    }

    public String getName() {
        return name;
    }

    public String getAuthor() {
        return author;
    }

    public String getContent() {
        return content;
    }

    public int getDownloadCnt() {
        return downloadCnt;
    }

}
