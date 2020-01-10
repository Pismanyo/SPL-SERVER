package bgu.spl.net.api;

import bgu.spl.net.frame.fromClient.*;
import bgu.spl.net.frame.toClient.Error;
import bgu.spl.net.srv.Connections;
import bgu.spl.net.srv.User;

public class StompMessagingProtocolImpl implements StompMessagingProtocol {
    private boolean toTerminate=false;
    private int connectionId;
    private Connections<String> connections;
    private User activeUser;


    @Override
    public void start(int connectionId, Connections<String> connections) {
        this.connectionId = connectionId;
        this.connections = connections;
        activeUser=null;
    }

    @Override
    public void process(String message) {
        String a= message.substring(0, message.indexOf('\n'));
        Frame f;
        switch (a) {
            case "SEND":
                f = new Send(this);
                break;
            case "SUBSCRIBE":
                f = new Subscribe(this);
                break;
            case "UNSUBSCRIBE":
                f = new Unsubscribe(this);
                break;
            case "CONNECT":
                f = new Connect(this);
                break;
            case "DISCONNECT":
                f = new Disconnect(this);
                break;
            default:
                f = new Error("Invalid input");
                break;
        }
        if(!f.process(message))
            toTerminate=true;
    }

    @Override
    public boolean shouldTerminate() {
        return toTerminate;
    }
    public User getuser()
    {
        return activeUser;
    }
    public void setactiveUser(User a)
    {
        activeUser=a;
    }
    public int getconnectid()
    {
        return connectionId;
    }
}
