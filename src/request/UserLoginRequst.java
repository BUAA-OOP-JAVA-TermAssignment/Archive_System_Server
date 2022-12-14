package request;

import controller.LoginController;
import message.UserLoginRequestMsg;
import message.UserRegisterRequestMsg;
import server.thread.ClientThread;

/**
 * ??¼Ҫ??
 * @author Hathoric
 */
public class UserLoginRequst extends BaseRequst {

    UserLoginRequestMsg um;


    public UserLoginRequst(UserLoginRequestMsg um, ClientThread ct) {
        super(ct);
        this.um = um;
    }

    public int getUserType(){return um.getUserType();}

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
