package message;

import javax.print.Doc;

/**
 * 下载PDF文件的请求报文
 * @author pcpas
 */
public class DownloadRequestMsg extends BaseMsg {

    String docID;

    /**
     * 下载PDF报文的构造类
     * @param msgCode hash码
     * @param docID 文件ID
     */
    public DownloadRequestMsg(int msgCode, String docID) {
        super(msgCode);
        this.docID = docID;
    }

    public String getDocID() {
        return docID;
    }
}
