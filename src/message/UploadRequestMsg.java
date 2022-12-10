package message;

import java.io.File;

/**
 *UploadRequestMsg只传递能否上传的信息
 */
public class UploadRequestMsg extends BaseMsg{

    public UploadRequestMsg(int msgCode) {
        super(msgCode);
    }
}
