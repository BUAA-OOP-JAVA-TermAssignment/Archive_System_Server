package dao.domain;

import com.google.common.util.concurrent.AbstractIdleService;

import java.util.Date;

public class Document {
    private String Id;
    private String name;
    private String author;

    private String content;
    private String publish;
    private String introduction;
    private String language;
    private String uploadDate;
    private int downloadCnt;

    public Document() {
    }

    /**
     * @param Id
     * @param name
     * @param author
     * @param publish
     * @param introduction
     * @param language
     * @param uploadDate
     * @param downloadCnt
     */
    public Document(String Id, String name, String author, String publish, String introduction, String language, String uploadDate, int downloadCnt) {
        this.Id = Id;
        this.name = name;
        this.author = author;
        this.content = null;
        this.publish = publish;
        this.language = language;
        this.introduction = introduction;
        this.uploadDate = uploadDate;
        this.downloadCnt = downloadCnt;
    }

    /**
     * @param Id
     * @param name
     * @param author
     * @param content
     * @param publish
     * @param introduction
     * @param language
     * @param uploadDate
     * @param downloadCnt
     */
    public Document(String Id, String name, String author, String content, String publish, String introduction, String language, String uploadDate, int downloadCnt) {
        this.Id = Id;
        this.name = name;
        this.author = author;
        this.content = content;
        this.publish = publish;
        this.language = language;
        this.introduction = introduction;
        this.uploadDate = uploadDate;
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

    public void setLanguage(String language) {
        this.language = language;
    }

    public void setPublish(String publish) {
        this.publish = publish;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public void setUploadDate(String uploadDate) {
        this.uploadDate = uploadDate;
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

    public String getIntroduction() {
        return introduction;
    }

    public String getLanguage() {
        return language;
    }

    public String getUploadDate() {
        return uploadDate;
    }

    public int getDownloadCnt() {
        return downloadCnt;
    }

    public String getPublish() {
        return publish;
    }
}
