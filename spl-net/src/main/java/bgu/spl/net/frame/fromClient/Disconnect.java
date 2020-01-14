package bgu.spl.net.frame.fromClient;

import bgu.spl.net.api.Messageformat;
import bgu.spl.net.api.StompMessagingProtocol;
import bgu.spl.net.api.StompMessagingProtocolImpl;
import bgu.spl.net.frame.toClient.Connected;
import bgu.spl.net.frame.toClient.Error;
import bgu.spl.net.frame.toClient.Receipt;
import bgu.spl.net.srv.Connections;
import bgu.spl.net.srv.ConnectionsiImp;
import bgu.spl.net.srv.User;
import bgu.spl.net.srv.UserDatabase;

public class Disconnect implements Frame {
    private StompMessagingProtocolImpl stomp;
    private String[] format={"receipt:"};
    private boolean hasbody=false;

    public Disconnect(StompMessagingProtocolImpl stompMessagingProtocol) {
        stomp=stompMessagingProtocol;
    }

    @Override
    public boolean process(String msg) {
        Messageformat a = new Messageformat();
        ConnectionsiImp connect = ConnectionsiImp.getInstance();
        String[] headers = a.messageformat(msg, format,hasbody);
        if (headers == null) {
            Error erro = new Error("Incorrect format.");
            erro.setMsg(msg);
            connect.send(stomp.getconnectid(), erro.toString());
            return false;
        }
        if (stomp.getuser() == null) {
            Error erro = new Error("No user to disconnect.");
            erro.setMsg(msg);
            connect.send(stomp.getconnectid(), erro.toString());
            return false;
        }
        stomp.getuser().setActive(false);
        stomp.setactiveUser(null);
        Receipt ans = new Receipt(Integer.parseInt(headers[0]));
        connect.send(stomp.getconnectid(), ans.toString());

        return true;
    }

}
