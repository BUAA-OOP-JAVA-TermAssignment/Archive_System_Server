package request;

import controller.LoginController;
import message.BaseMsg;
import server.thread.ClientThread;

public abstract class BaseRequst {
    protected ClientThread ct;

    protected BaseRequst(ClientThread thread)
    {
        this.ct = thread;
    }

    public ClientThread getThread() {
        return ct;
    }
    public abstract void execute();

}
