package bgu.spl.net.frame.fromClient;

import bgu.spl.net.api.StompMessagingProtocol;
import bgu.spl.net.api.StompMessagingProtocolImpl;
import bgu.spl.net.srv.Connections;
import bgu.spl.net.srv.User;

public class Disconnect implements Frame {
    private StompMessagingProtocol stomp;

    public Disconnect(StompMessagingProtocolImpl stompMessagingProtocol) {
        stomp=stompMessagingProtocol;
    }

    @Override
    public boolean process(String msg) {

        return false;
    }

}
