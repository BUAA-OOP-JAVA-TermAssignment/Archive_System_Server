package dao.domain;

import com.google.common.util.concurrent.AbstractIdleService;

import java.util.Date;

public class Document {
    private String Id;
    private String name;
    private String author;
    private String publish;
    private String introduction;
    private String language;
    private Date uploadDate;
    private int state;

    public Document(){}
    public Document(String Id, String name, String author, String publish, String introduction, String language, Date uploadDate){
        this.Id = Id;
        this.name = name;
        this.author = author;
        this.publish = publish;
        this.introduction = introduction;
        this.uploadDate = uploadDate;
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

    public void setLanguage(String language) {
        this.language = language;
    }

    public void setPublish(String publish) {
        this.publish = publish;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public void setUploadDate(Date uploadDate) {
        this.uploadDate = uploadDate;
    }

    public void setState(int state) {
        this.state = state;
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

    public String getIntroduction() {
        return introduction;
    }

    public String getLanguage() {
        return language;
    }

    public Date getUploadDate() {
        return uploadDate;
    }

    public int getState() {
        return state;
    }

    public String getPublish() {
        return publish;
    }
}
