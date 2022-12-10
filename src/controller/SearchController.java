package controller;

import dao.lucene.LuceneCore;
import message.SearchReturnMsg;
import request.SearchRequest;

import java.security.Key;

/**
 * @author pcpas
 */
public class SearchController {
    private static SearchController sc;

    /**
     * �̰߳�ȫ�ĵ���ģʽ
     *
     * @return
     */
    public static SearchController getInstance() {
        if (sc == null) {
            synchronized (SearchController.class) {
                if (sc == null) {
                    sc = new SearchController();
                }
            }
        }
        return sc;
    }

    /**
     * ͨ���ؼ�������
     *
     * @param keyWords
     * @param offset
     * @param max
     */
    public void search(SearchRequest sr, String keyWords, int offset, int max) {
        SearchReturnMsg srm = LuceneCore.getInstance().search(keyWords, offset, max);
        sr.getThread().sendMsgBack(srm);
    }


}
