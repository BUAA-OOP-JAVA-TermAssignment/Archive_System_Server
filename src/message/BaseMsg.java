package message;

import server.thread.ClientThread;

import java.io.Serializable;
import java.net.Socket;

public abstract class BaseMsg implements Serializable {

    protected ClientThread ct;

    public void setThread(ClientThread thread) {
        ct = thread;
    }

    public ClientThread getThread() {
        return ct;
    }

    public abstract void execute();

}
