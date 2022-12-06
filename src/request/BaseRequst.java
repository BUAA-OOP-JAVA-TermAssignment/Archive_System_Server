package request;

import controller.LoginController;
import server.thread.ClientThread;

public abstract class BaseRequst {
    protected ClientThread ct;

    public void setThread(ClientThread thread) {
        ct = thread;
    }

    public ClientThread getThread() {
        return ct;
    }
    public abstract void execute();

}
