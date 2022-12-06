package message;

import controller.LoginController;

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

    @Override
    public void execute() {
        LoginController.getInstance().adminLoginCheck(this);
    }
}
