package controller;

import message.UploadReturnMsg;
import request.UploadRequst;
import service.UploadService;

import java.io.File;

public class UploadController {

    private static UploadController uc;

    private UploadService uploadService;

    private UploadController(){setUploadService(new UploadService());}

    public static UploadController getInstance() {
        if (uc == null){
            synchronized (UploadController.class) {
                if (uc == null){
                    uc = new UploadController();
                }
            }
        }
        return uc;
    }

    public void setUploadService(UploadService us){uploadService = us;}

    /**
     * 上传pdf文件
     */
    public void uploadFile(UploadRequst um) {
        //TODO:这里返回值需要修改！
        File file = um.getFile();
        if(file != null){
            boolean haveUpload = uploadService.hasUploadFile(file);
            if(haveUpload){
                um.getThread().sendMsgBack(new UploadReturnMsg());
            }
            else{
                um.getThread().sendMsgBack(new UploadReturnMsg());
            }
        }
        else{
            um.getThread().sendMsgBack(new UploadReturnMsg());
        }
    }
}
