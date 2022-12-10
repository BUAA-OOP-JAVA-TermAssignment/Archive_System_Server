package message;

public class UserLoginRequestMsg extends BaseMsg {
    private int userType;
    private String id;
    private String password;
    private UserLoginRequestMsg(int userType,String id, String password) {
        super(LOGIN);
        this.userType = userType;
        this.id = id;
        this.password = password;
    }

    /**
     * 创建LoginRequestMsg实例
     * @param id 用户学工号
     * @param password 用户密码
     * @return 返回新建的实例
     */
    public static UserLoginRequestMsg createLoginRequestMsg(int userType, String id, String password) {
        return new UserLoginRequestMsg(userType, id, password);
    }

    public int getUserType(){return userType;}

    public String getId() {
        return id;
    }

    public String getPassword() {
        return password;
    }

}
