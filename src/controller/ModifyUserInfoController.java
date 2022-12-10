package controller;

import dao.domain.User;
import message.BaseMsg;
import request.ModifyUserInfoRequest;
import service.ModifyUserInfoService;

public class ModifyUserInfoController {

    private static ModifyUserInfoController mc;

    private ModifyUserInfoService modifyUserInfoService;

    private ModifyUserInfoController(){setModifyUserInfoService(new ModifyUserInfoService());}

    public static ModifyUserInfoController getInstance(){
        if(mc == null){
            synchronized (ModifyUserInfoController.class) {
                if (mc == null){
                    mc = new ModifyUserInfoController();
                }
            }
        }
        return mc;
    }

    public void setModifyUserInfoService(ModifyUserInfoService ms){
        modifyUserInfoService = ms;
    }

    public void userModifyUserInfo(ModifyUserInfoRequest mm){
        String id = mm.getId();
        String password = mm.getPassword();
        String email = mm.getEmail();
        int downloadCnt = mm.getDownloadCnt();
        if (id != null && password != null && email != null){
            System.out.println("userModifyUserInfo");
            boolean isModify = ModifyUserInfoService.hasModify(id, password, email, downloadCnt);
            if (isModify){
                System.out.println("Modify Success");
                mm.getThread().sendMsgBack(new BaseMsg(BaseMsg.SUCCESS));
            }else {
                System.out.println("Modify Failed");
                mm.getThread().sendMsgBack(new BaseMsg(BaseMsg.UNDEFINED_FAILED));
            }
        }
    }
}
