package controller;

import dao.domain.User;
import request.AdminUserEditMsgRequest;
import service.AdminUserEditMsgService;


public class AdminUserEditMsgController {

    private static AdminUserEditMsgController ac;

    private AdminUserEditMsgService adminUserEditMsgService;

    private AdminUserEditMsgController(){setAdminUserEditMsgService(new AdminUserEditMsgService());}

    public static AdminUserEditMsgController getInstance(){
        if (ac == null){
            synchronized (AdminUserEditMsgController.class) {
                if (ac == null){
                    ac = new AdminUserEditMsgController();
                }
            }
        }
        return ac;
    }

    public void setAdminUserEditMsgService(AdminUserEditMsgService as){adminUserEditMsgService = as;}

    public void AdminEditUserMsg(AdminUserEditMsgRequest am){
            //TODO:还没实现呢！！！！！

    }
}
