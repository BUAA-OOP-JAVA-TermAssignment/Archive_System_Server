package message;

/**
 * ����Ա�༭�ĵ��Ĳ�����Ϣ
 *
 * @author : AkashiSensei
 * @date : 2022/12/8 21:00
 */
public class DownloadCntAddMsg extends BaseMsg{

    private String Id;
    private int downloadCnt;

    public DownloadCntAddMsg(String Id, int downloadCnt) {
        super(DOWNLOADCNT_ADD);
    }

    public String getId(){return Id;}

    private int getDownloadCnt(){return downloadCnt;}

}
