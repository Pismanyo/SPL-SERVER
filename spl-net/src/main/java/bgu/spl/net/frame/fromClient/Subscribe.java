package bgu.spl.net.frame.fromClient;

import bgu.spl.net.api.StompMessagingProtocol;
import bgu.spl.net.api.StompMessagingProtocolImpl;
import bgu.spl.net.srv.Connections;
import bgu.spl.net.srv.User;

public class Subscribe implements Frame {
    private StompMessagingProtocol stomp;
    private String[] format={"destination:","id:"};


    public Subscribe(StompMessagingProtocolImpl stompMessagingProtocol) {
        stomp=stompMessagingProtocol;
    }

    @Override
    public boolean process(String msg) {


        return false;
    }
}
