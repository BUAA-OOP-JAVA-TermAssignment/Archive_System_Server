package message;

import javax.print.Doc;

/**
 * ����PDF�ļ���������
 * @author pcpas
 */
public class DownloadRequestMsg extends BaseMsg {

    String docID;

    /**
     * ����PDF���ĵĹ�����
     * @param msgCode hash��
     * @param docID �ļ�ID
     */
    public DownloadRequestMsg(int msgCode, String docID) {
        super(msgCode);
        this.docID = docID;
    }

    public String getDocID() {
        return docID;
    }
}
