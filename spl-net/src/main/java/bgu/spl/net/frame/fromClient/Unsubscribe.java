package bgu.spl.net.frame.fromClient;

import bgu.spl.net.api.StompMessagingProtocolImpl;

public class Unsubscribe implements Frame {
    public Unsubscribe(StompMessagingProtocolImpl stompMessagingProtocol) {
    }

    @Override
    public boolean process(String msg) {
        return false;
    }
}
