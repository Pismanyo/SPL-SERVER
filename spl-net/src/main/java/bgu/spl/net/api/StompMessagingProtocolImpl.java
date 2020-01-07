package bgu.spl.net.api;

import bgu.spl.net.frame.fromClient.Connect;
import bgu.spl.net.frame.fromClient.User;
import bgu.spl.net.frame.toClient.Error;
import bgu.spl.net.frame.toClient.Message;
import bgu.spl.net.frame.toClient.Receipt;
import bgu.spl.net.srv.Connections;

import java.util.HashMap;
import java.util.Queue;

public class StompMessagingProtocolImpl implements StompMessagingProtocol {
    private int connectionId;
    private Connections<String> connections;
    private HashMap<String, Queue<Integer>> chanel;
    private HashMap<String, User> users;


    @Override
    public void start(int connectionId, Connections<String> connections) {
            this.connectionId=connectionId;
            this.connections=connections;

    }

    @Override
    public void process(String message) {
//        String a= message.substring(0, message.indexOf('\n'));
//        switch (a) {
//            case "MESSAGE":
//                Message m = new Message(message.substring(message.indexOf('\n')));
//                break;
//            case "CONNECT":
//                Connect c = new Connect(message.substring(message.indexOf('\n')));
//                break;
//            case "ERROR":
//                Error e = new Error(message.substring(message.indexOf('\n')));
//                break;
//            case "RECEIPT":
//                Receipt r = new Receipt(message.substring(message.indexOf('\n')));
//                break;
//        }


    }

    @Override
    public boolean shouldTerminate() {
        return false;
    }
}
