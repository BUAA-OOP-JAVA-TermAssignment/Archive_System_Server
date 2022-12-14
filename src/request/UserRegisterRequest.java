package request;

import controller.LoginController;
import dao.domain.User;
import message.UserRegisterRequestMsg;
import server.thread.ClientThread;

import java.util.Date;

/**
 * ע??Ҫ??
 * @author Hathoric
 */
public class UserRegisterRequest extends BaseRequst{
    UserRegisterRequestMsg um;



    public UserRegisterRequest(UserRegisterRequestMsg um, ClientThread ct) {
        super(ct);
        this.um = um;
    }

    public String getName(){return um.getName();}

    public String getId() {
        return um.getId();
    }

    public String getPassword() {
        return um.getPassword();
    }

    public String getEmail(){return um.getEmail();}

    @Override
    public void execute() {
        LoginController.getInstance().userRegister(this);
    }
}
