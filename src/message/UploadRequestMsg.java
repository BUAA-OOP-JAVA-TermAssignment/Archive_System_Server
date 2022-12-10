package message;

import java.util.Date;

/**
 * 该请求报文只用于请求，并不直接提供传输方式，相关ruturn为baseMsg的SUCCESS/UNDEFINED_FAILED/TIME_OUT
 */
public class UploadRequestMsg extends BaseMsg {

    public UploadRequestMsg(int msgCode) {
        super(msgCode);
    }


}
