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
     * 线程安全的单例模式
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
     * 通过关键词搜索
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
