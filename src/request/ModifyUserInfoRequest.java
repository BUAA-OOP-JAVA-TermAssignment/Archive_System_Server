package request;

import controller.ModifyUserInfoController;
import message.ModifyUserInfo;
import server.thread.ClientThread;

/**
 * 用户个人修改信息要求
 * @author Hathoric
 */
public class ModifyUserInfoRequest extends BaseRequst {
    ModifyUserInfo mi;

    public ModifyUserInfoRequest(ModifyUserInfo mi, ClientThread ct){
        super(ct);
        this.mi = mi;
    }

    public String getId(){return mi.getId();}

    public String getPassword(){return mi.getPassword();}

    public String getEmail(){return mi.getEmail();}

    public int getDownloadCnt(){return mi.getDownloadCnt();}

    public void execute(){
        ModifyUserInfoController.getInstance().ModifyUserInfo(this);
    }
}
