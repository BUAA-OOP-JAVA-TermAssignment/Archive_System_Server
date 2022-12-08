package request;

import controller.LoginController;
import message.AdminLoginRequestMsg;
import server.thread.ClientThread;

public class AdminLoginRequst extends BaseRequst {

    AdminLoginRequestMsg am;

    public AdminLoginRequst(AdminLoginRequestMsg am, ClientThread ct) {
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
