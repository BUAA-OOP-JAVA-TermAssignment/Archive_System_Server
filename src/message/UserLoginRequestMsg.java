package message;

/**
 * 用户登陆时向主机发送的请求
 *
 * @author : AkashiSensei
 * &#064;date  : 2022/12/8 20:19
 */
public class UserLoginRequestMsg extends BaseMsg {
    public static final int GUEST = 1, ADMIN = 2;
    private int userType;
    private String id;
    private String password;

    private UserLoginRequestMsg(int userType, String id, String password) {
        super(LOGIN);
        this.userType = userType;
        this.id = id;
        this.password = password;
    }

    /**
     * 创建LoginRequestMsg实例
     *
     * @param id       用户学工号
     * @param password 用户密码
     * @return 返回新建的实例
     */
    public static UserLoginRequestMsg createLoginRequestMsg(int userType, String id, String password) {
        return new UserLoginRequestMsg(userType, id, password);
    }

    public int getUserType() {
        return userType;
    }

    public String getId() {
        return id;
    }

    public String getPassword() {
        return password;
    }
}
