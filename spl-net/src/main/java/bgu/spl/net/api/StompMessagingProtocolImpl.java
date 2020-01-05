package bgu.spl.net.api;

import bgu.spl.net.frame.Connect;
import bgu.spl.net.frame.Error;
import bgu.spl.net.frame.Message;
import bgu.spl.net.frame.Receipt;
import bgu.spl.net.srv.Connections;

public class StompMessagingProtocolImpl implements StompMessagingProtocol {
    @Override
    public void start(int connectionId, Connections<String> connections) {

    }

    @Override
    public void process(String message) {
        String a= message.substring(0, message.indexOf('\n'));
        switch (a) {
            case "MESSAGE":
                Message m = new Message(message.substring(message.indexOf('\n')));
                break;
            case "CONNECT":
                Connect c = new Connect(message.substring(message.indexOf('\n')));
                break;
            case "ERROR":
                Error e = new Error(message.substring(message.indexOf('\n')));
                break;
            case "RECEIPT":
                Receipt r = new Receipt(message.substring(message.indexOf('\n')));
                break;
        }


    }

    @Override
    public boolean shouldTerminate() {
        return false;
    }
}
