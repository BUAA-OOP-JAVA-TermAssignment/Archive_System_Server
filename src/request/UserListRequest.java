package request;

import controller.AdminUserEditController;
import server.thread.ClientThread;

/**
 * 返回用户列表要求
 * @author Hathoric
 */
public class UserListRequest extends BaseRequst{
    public UserListRequest(ClientThread ct){
        super(ct);
    }
    @Override
    public void execute() {
        AdminUserEditController.getInstance().UserListNeed(this);
    }
}
