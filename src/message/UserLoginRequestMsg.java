package message;

public class UserLoginRequestMsg extends BaseMsg {

    String username = null;
    String password = null;

    public UserLoginRequestMsg(String username, String password) {
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
