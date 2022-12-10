package message;

import javax.print.Doc;

/**
 * @author pcpas
 */
public class DownloadRequestMsg extends BaseMsg {

    String docID;

    public DownloadRequestMsg(int msgCode, String docID) {
        super(msgCode);
        this.docID = docID;
    }

    public String getDocID() {
        return docID;
    }
}
