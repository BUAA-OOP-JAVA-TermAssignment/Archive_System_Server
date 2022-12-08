package message;

/**
 * @author pcpas
 */
public class AdminLoginRequestMsg extends BaseMsg {
    String username = null;
    String password = null;

    AdminLoginRequestMsg(String username, String password) {
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