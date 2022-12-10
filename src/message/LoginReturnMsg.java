package message;

import java.util.Date;

public class LoginReturnMsg extends BaseMsg{
    private String userName;
    private String id;
    private String password;
    private final int downloadCnt;
    private final Date date;
    private final String email;


    private LoginReturnMsg(String userName,String id, String email, String password, int downloadCnt, Date date) {
        super(- LOGIN);
        this.userName = userName;
        this.id = id;
        this.email = email;
        this.password = password;
        this.downloadCnt = downloadCnt;
        this.date = date;
    }


    /**
     * 创建LoginReturnMsg实例
     * @param downloadCnt 用户总计下载量，这个值由客户端统计，服务器只需要记录这个值
     * @param email 用户邮箱
     */
    public static LoginReturnMsg createLoginReturnMsg(String userName,String id, String email, String password, int downloadCnt, Date date) {
        return new LoginReturnMsg(userName,id,email,password,downloadCnt, date);
    }

    public String getUserName() {
        return userName;
    }

    public String getId() {
        return id;
    }


    public int getDownloadCnt() {
        return downloadCnt;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public Date getDate() {
        return date;
    }
}
