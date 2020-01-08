package bgu.spl.net.frame.fromClient;

import bgu.spl.net.api.StompMessagingProtocolImpl;

public class Subscribe implements Frame {


    public Subscribe(StompMessagingProtocolImpl stompMessagingProtocol) {
    }

    @Override
    public boolean process(String msg) {
        return false;
    }
}
