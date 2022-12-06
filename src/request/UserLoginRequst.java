package request;

import controller.LoginController;
import dao.domain.User;
import message.AdminLoginMsg;
import message.UserLoginMsg;
import server.thread.ClientThread;

public class UserLoginRequst extends BaseRequst {

    UserLoginMsg um;

    public UserLoginRequst(UserLoginMsg um, ClientThread ct) {
        this.um = um;
        this.ct = ct;
    }

    public String getUsername() {
        return um.getUsername();
    }

    public String getPassword() {
        return um.getPassword();
    }

    @Override
    public void execute() {
        LoginController.getInstance().userLoginCheck(this);
    }
}
