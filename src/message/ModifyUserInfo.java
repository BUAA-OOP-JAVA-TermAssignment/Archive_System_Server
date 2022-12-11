package message;

/**
 * 用户修改自己的个人信息时发送的数据。
 * 用户修改个人信息之前，将会有密码重新输入的要求，但这时应用将用户输入的密码与本地保存的密码比对，不需要访问服务器
 * 发送的数据将包括新的密码，用户邮箱，也可以包括用户下载数量。
 * 相应的返回消息是成功与否，以及发生错误的类型。
 *
 * @author : AkashiSensei
 * @date : 2022/12/8 20:53
 */
public class ModifyUserInfo extends BaseMsg {
    private final String id;
    private final String password;
    private final String email;
    private final int downloadCnt;


    private ModifyUserInfo(String id, String password, String email, int downloadCnt) {
        super(MODIFY_USER_INFO);
        this.id = id;
        this.password = password;
        this.email = email;
        this.downloadCnt = downloadCnt;
    }

    /**
     * 创建ModifyUserInfo的实例
     *
     * @param password    密码，直接更新，如果没有修改会把旧的传过来
     * @param email       电子邮箱
     * @param downloadCnt 下载量
     * @return 返回新建的实例
     */
    public static ModifyUserInfo createModifyUserInfo(String id, String password, String email, int downloadCnt) {
        return new ModifyUserInfo(id, password, email, downloadCnt);
    }

    public String getId() {
        return id;
    }

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
