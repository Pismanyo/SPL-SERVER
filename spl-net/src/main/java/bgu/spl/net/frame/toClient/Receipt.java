package bgu.spl.net.frame.toClient;

import bgu.spl.net.frame.fromClient.Frame;

public class Receipt implements Frame {
    private String msg;

    public Receipt(String msg){
        this.msg=msg;
    }
}
