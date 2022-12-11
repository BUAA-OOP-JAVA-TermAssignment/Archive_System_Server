package request;

import controller.AdminUserEditController;
import server.thread.ClientThread;

public class UserListRequest extends BaseRequst{
    public UserListRequest(ClientThread ct){
        super(ct);
    }
    @Override
    public void execute() {
        AdminUserEditController.getInstance().UserListNeed(this);
    }
}
