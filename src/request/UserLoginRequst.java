package request;

import controller.LoginController;
import message.UserLoginRequestMsg;
import server.thread.ClientThread;

public class UserLoginRequst extends BaseRequst {

    UserLoginRequestMsg um;

    public UserLoginRequst(UserLoginRequestMsg um, ClientThread ct) {
        this.um = um;
        this.ct = ct;
    }

    public String getId() {
        return um.getId();
    }

    public String getPassword() {
        return um.getPassword();
    }

    @Override
    public void execute() {
        LoginController.getInstance().userLoginCheck(this);
    }
}
