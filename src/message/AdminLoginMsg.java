package message;

/**
 * @author pcpas
 */
public class AdminLoginMsg extends BaseMsg {
    String username = null;
    String password = null;

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

}
