package message;

import java.io.File;

public class UploadRequestMsg {
    File file = null;
    public UploadRequestMsg(File file){
        this.file = file;
    }
    public File getFile(){return file;}
}
