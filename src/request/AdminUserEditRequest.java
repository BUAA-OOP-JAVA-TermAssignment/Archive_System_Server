package request;

import controller.AdminUserEditController;
import message.AdminUserEditMsg;
import server.thread.ClientThread;

public class AdminUserEditRequest extends BaseRequst {
    AdminUserEditMsg au;

    public AdminUserEditRequest(AdminUserEditMsg au, ClientThread ct){
        super(ct);
        this.au = au;
    }

    public String getId(){return au.getId();}

    public String getUserName(){return au.getUserName();}

    public String getPassword(){return au.getPassword();}

    public String getEmail(){return au.getEmail();}

    public String getDate(){return au.getDate();}

    public int getDownloadCnt(){return au.getDownloadCnt();}

    public int getopcode(){return au.getOpCode();}

    public void execute(){
        AdminUserEditController.getInstance().AdminEditUserMsg(this);
    }
}
