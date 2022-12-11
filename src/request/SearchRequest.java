package request;

import controller.SearchController;
import message.SearchRequestMsg;
import server.thread.ClientThread;

/**
 * ËÑË÷ÒªÇó
 */
public class SearchRequest extends BaseRequst {

    SearchRequestMsg srm = null;

    public SearchRequest(SearchRequestMsg srm, ClientThread thread) {
        super(thread);
        this.srm = srm;
    }

    @Override
    public void execute() {
        SearchController.getInstance().search(this, srm.getSearchText(), srm.getOffset(), srm.getCount());
    }
}
