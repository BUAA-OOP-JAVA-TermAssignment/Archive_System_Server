package request;

import controller.UploadController;
import message.UploadRequestMsg;
import server.thread.ClientThread;

import java.io.File;

/**
 * 上传要求
 */
public class UploadRequst extends BaseRequst{
    UploadRequestMsg um;

    public UploadRequst(UploadRequestMsg um, ClientThread ct){
        super(ct);
        this.um = um;
    }

    //注意改
    public File getFile(){return null;}


    public void execute(){
        //UploadController.getInstance().uploadFile(this);
    }
}
