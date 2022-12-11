package controller;

import dao.domain.User;
import message.AdminUserRequestMsg;
import message.BaseMsg;
import request.AdminUserEditRequest;
import request.UserListRequest;
import request.UserLoginRequst;
import service.AdminUserEditService;


public class AdminUserEditController {

    private static AdminUserEditController ac;

    private AdminUserEditService adminUserEditService;

    private AdminUserEditController(){setAdminUserEditMsgService(new AdminUserEditService());}

    public static AdminUserEditController getInstance(){
        if (ac == null){
            synchronized (AdminUserEditController.class) {
                if (ac == null){
                    ac = new AdminUserEditController();
                }
            }
        }
        return ac;
    }

    public void setAdminUserEditMsgService(AdminUserEditService as){adminUserEditService = as;}

    public void AdminEditUserMsg(AdminUserEditRequest am){
        int opcode = am.getopcode();
        String id = am.getId();
        String uesrName = am.getUserName();
        String password = am.getPassword();
        String email = am.getEmail();
        int downloadCnt = am.getDownloadCnt();
        String time = am.getDate();
        switch (opcode){
            case 1 -> {
                System.out.println("Admin Add User");
                boolean isAdd = adminUserEditService.hasAdminAdd(id, uesrName, password, email, time, downloadCnt);
                if(isAdd){
                    System.out.println("Admin Add Success");
                    am.getThread().sendMsgBack(new BaseMsg(BaseMsg.SUCCESS));
                }else{
                    System.out.println("Admin Add Failed");
                    am.getThread().sendMsgBack(new BaseMsg(BaseMsg.UNDEFINED_FAILED));
                }
            }
            case 2 -> {
                System.out.println("Admin Delete User");
                boolean isDelete = adminUserEditService.hasAdminDelete(id);
                if(isDelete){
                    System.out.println("Admin Delete Success");
                    am.getThread().sendMsgBack(new BaseMsg(BaseMsg.SUCCESS));
                }else{
                    System.out.println("Admin Delete Failed");
                    am.getThread().sendMsgBack(new BaseMsg(BaseMsg.UNDEFINED_FAILED));
                }
            }
            case 3 -> {
                System.out.println("Admin Change User");
                boolean isChange = adminUserEditService.hasAdminChange(id, uesrName, password, email, time, downloadCnt);
                if(isChange){
                    System.out.println("Admin Change Success");
                    am.getThread().sendMsgBack(new BaseMsg(BaseMsg.SUCCESS));
                }else{
                    System.out.println("Admin Change Failed");
                    am.getThread().sendMsgBack(new BaseMsg(BaseMsg.UNDEFINED_FAILED));
                }
            }
        }
    }

    public void UserListNeed(UserListRequest um){
        System.out.println("UserListNeed");
        AdminUserRequestMsg adminUserRequestMsg = new AdminUserRequestMsg();
        for(User user : adminUserEditService.hasUserList()){
            adminUserRequestMsg.add(user.getId(),user.getUserName(),user.getPassword(),user.getEmail(),user.getDownloadCnt(),user.getTime());
        }
        um.getThread().sendMsgBack(adminUserRequestMsg);
    }
}
