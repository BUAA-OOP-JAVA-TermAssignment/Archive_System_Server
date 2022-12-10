package message;

public class ModifyUserInfo extends BaseMsg{
    private final String id;
    private final String password;
    private final String email;
    private final int downloadCnt;


    private ModifyUserInfo(String id,String password, String email, int downloadCnt) {
        super(MODIFY_USER_INFO);
        this.id = id;
        this.password = password;
        this.email = email;
        this.downloadCnt = downloadCnt;
    }

    /**
     * 创建ModifyUserInfo的实例
     * @param password 密码，直接更新，如果没有修改会把旧的传过来
     * @param email 电子邮箱
     * @param downloadCnt 下载量
     * @return 返回新建的实例
     */
    public static ModifyUserInfo createModifyUserInfo(String id, String password, String email, int downloadCnt) {
        return new ModifyUserInfo(id, password, email, downloadCnt);
    }

    public String getId(){return id;}
    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public int getDownloadCnt() {
        return downloadCnt;
    }
}
