package dao.domain;

import java.util.Date;

public class Admin {
    private String id;

    private String userName;

    private String password;

    private String email;

    private Date time;

    public Admin(String id, String userName, String password, String email, Date time){
        this.id = id;
        this.userName = userName;
        this.password = password;
        this.email = email;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getTime(){
        return time;
    }

    public void setTime(Date time){
        this.time = time;
    }
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
