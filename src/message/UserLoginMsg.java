package message;

public class UserLoginMsg extends BaseMsg {

    String username = null;
    String password = null;

    public UserLoginMsg(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

}
