package bgu.spl.net.impl.stomp;

import bgu.spl.net.api.MessageEncoderDecoder;
import bgu.spl.net.api.MessageEncoderDecoderImpl;
import bgu.spl.net.api.StompMessagingProtocol;
import bgu.spl.net.api.StompMessagingProtocolImpl;
import bgu.spl.net.srv.Server;

public class StompServer {

    public static void main(String[] args) {

        Server.reactor(
                Runtime.getRuntime().availableProcessors(),
                7777, //port
                ()->new StompMessagingProtocolImpl(), //protocol factory
                ()->new MessageEncoderDecoderImpl() {} //message encoder decoder factory
        ).serve();

    }


}
