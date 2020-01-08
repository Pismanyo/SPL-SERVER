package bgu.spl.net.frame.fromClient;

import bgu.spl.net.api.StompMessagingProtocolImpl;

public class Disconnect implements Frame {

    public Disconnect(StompMessagingProtocolImpl stompMessagingProtocol) {
    }

    @Override
    public boolean process(String msg) {

        return false;
    }
}
