package request;

import message.AdminUserEditMsg;
import message.BaseMsg;
import server.thread.ClientThread;

public class AdminUserEditMsgRequest extends BaseRequst {
    AdminUserEditMsg au;

    public AdminUserEditMsgRequest(AdminUserEditMsg au, ClientThread ct){
        super(ct);
        this.au = au;
    }

    public AdminUserEditMsg getAu(){return au;}

    public void execute(){}
}
