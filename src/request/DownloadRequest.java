package request;

import controller.UploadController;
import message.BaseMsg;
import message.DownloadRequestMsg;
import server.thread.ClientThread;

public class DownloadRequest extends BaseRequst {

    DownloadRequestMsg drm;

    public DownloadRequest(DownloadRequestMsg drm, ClientThread ct) {
        super(ct);
        this.drm = drm;
    }

    public String getDocId() {
        return drm.getDocID();
    }

    @Override
    public void execute() {
        System.out.println("download execute!");
        UploadController.getInstance().download(this);
    }
}
