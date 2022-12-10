package dao.domain;

import java.util.Date;

public class User {
    private String id;

    private String userName;

    private String password;

    private String email;

    private int downloadCnt;

    private String time;

    /**
     * 用户的实体
     *
     * @param id
     * @param userName
     * @param password
     * @param email
     * @param downloadCnt
     * @param time
     */
    public User(String id, String userName, String password, String email, int downloadCnt, String time) {
        this.id = id;
        this.userName = userName;
        this.password = password;
        this.email = email;
        this.downloadCnt = downloadCnt;
        this.time = time;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getDownloadCnt() {
        return downloadCnt;
    }

    public void setDownloadCnt(int downloadCnt) {
        this.downloadCnt = downloadCnt;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
