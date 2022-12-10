package request;

import controller.LoginController;
import dao.domain.User;
import message.UserRegisterRequestMsg;
import server.thread.ClientThread;

import java.util.Date;

public class UserRegisterRequest extends BaseRequst{
    UserRegisterRequestMsg um;



    public UserRegisterRequest(UserRegisterRequestMsg um, ClientThread ct) {
        this.um = um;
        this.ct = ct;
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
