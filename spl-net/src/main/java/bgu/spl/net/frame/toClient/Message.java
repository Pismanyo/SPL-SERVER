package bgu.spl.net.frame.toClient;

import bgu.spl.net.frame.fromClient.Frame;

public class Message implements Frame {
    private String msg;
    public Message(String msg){
        this.msg=msg;
    }
}
