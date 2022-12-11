package message;


import java.util.*;

/**
 * 管理员请求操作用户信息的消息
 *
 * @author : AkashiSensei
 * &#064;date  : 2022/12/8 20:57
 */
public class AdminUserEditMsg extends BaseMsg {
    public static final int ADD = 1;
    public static final int DELETE = 2;
    public static final int CHANGE = 3;
    private String userName;
    private String id;
    private String password;
    private final int downloadCnt;
    private final String date;
    private final String email;
    private int opCode;


    private AdminUserEditMsg(int opCode, String userName, String id, String email, String password, int downloadCnt, String date) {
        super(-ADMIN_USER_EDIT);
        this.opCode = opCode;
        this.userName = userName;
        this.id = id;
        this.email = email;
        this.password = password;
        this.downloadCnt = downloadCnt;
        this.date = date;
    }


    /**
     * 创建LoginReturnMsg实例
     *
     * @param downloadCnt 用户总计下载量，这个值由客户端统计，服务器只需要记录这个值
     * @param email       用户邮箱
     */
    public static AdminUserEditMsg createAdminUserEditMsg(int opCode, String userName, String id, String email, String password, int downloadCnt, String date) {
        return new AdminUserEditMsg(opCode, userName, id, email, password, downloadCnt, date);
    }

    public int getOpCode() {
        return opCode;
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

    public String getDate() {
        return date;
    }
}
