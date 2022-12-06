package request;

import controller.LoginController;
import message.AdminLoginMsg;
import message.UserLoginMsg;
import server.thread.ClientThread;

public class AdminLoginRequst extends BaseRequst {

    AdminLoginMsg am;

    public AdminLoginRequst(AdminLoginMsg am, ClientThread ct) {
        this.am = am;
        this.ct = ct;
    }

    public String getUsername() {
        return am.getUsername();
    }

    public String getPassword() {
        return am.getPassword();
    }

    @Override
    public void execute() {
        LoginController.getInstance().adminLoginCheck(this);
    }
}
