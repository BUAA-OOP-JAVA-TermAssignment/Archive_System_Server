package request;

import controller.UploadController;
import message.UploadRequestMsg;
import server.thread.ClientThread;

import java.io.File;

public class UploadRequst extends BaseRequst{
    UploadRequestMsg um;

    public UploadRequst(UploadRequestMsg um, ClientThread ct){
        this.um = um;
        this.ct = ct;
    }

    //×¢Òâ¸Ä
    public File getFile(){return null;}


    public void execute(){
        UploadController.getInstance().uploadFile(this);
    }
}
