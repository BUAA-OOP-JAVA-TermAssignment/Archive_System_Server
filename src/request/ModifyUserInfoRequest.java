package request;

import controller.ModifyUserInfoController;
import message.ModifyUserInfo;
import server.thread.ClientThread;

public class ModifyUserInfoRequest extends BaseRequst {
    ModifyUserInfo mi;

    public ModifyUserInfoRequest(ModifyUserInfo mi, ClientThread ct){
        this.mi = mi;
        this.ct = ct;
    }

    public String getId(){return mi.getId();}

    public String getPassword(){return mi.getPassword();}

    public String getEmail(){return mi.getEmail();}

    public int getDownloadCnt(){return mi.getDownloadCnt();}

    public void execute(){
        ModifyUserInfoController.getInstance().userModifyUserInfo(this);
    }
}
