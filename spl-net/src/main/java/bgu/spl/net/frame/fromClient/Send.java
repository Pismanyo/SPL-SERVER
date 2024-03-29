package bgu.spl.net.frame.fromClient;

import bgu.spl.net.api.Messageformat;
import bgu.spl.net.api.StompMessagingProtocol;
import bgu.spl.net.api.StompMessagingProtocolImpl;
import bgu.spl.net.frame.toClient.Error;
import bgu.spl.net.frame.toClient.Message;
import bgu.spl.net.frame.toClient.Receipt;
import bgu.spl.net.srv.Connections;
import bgu.spl.net.srv.ConnectionsiImp;
import bgu.spl.net.srv.User;

public class Send implements Frame{
    private Message toSend;
    private String[] format={"destination:"};
    private StompMessagingProtocolImpl stomp;
    private boolean hasbody=true;

    public Send(StompMessagingProtocolImpl stompMessagingProtocol) {
        stomp=stompMessagingProtocol;
    }


    public boolean process(String msg) {
        Messageformat a=new Messageformat();
        ConnectionsiImp connect=ConnectionsiImp.getInstance();
        String[] headers= a.messageformat(msg,format,hasbody);
        if(headers==null)
        {
            Error erro=new Error(msg,"Incorrect format.");
            connect.send(stomp.getconnectid(),erro.toString());
            return false;
        }
        if(!stomp.getuser().isSubscribed(headers[0]))
        {
            Error erro=new Error(msg,"Not subscribed to topic");
            connect.send(stomp.getconnectid(),erro.toString());
            return false;

        }
        Message ans=new Message(headers[0],headers[1]);
        connect.send(headers[0],ans);
        return true;
    }

}
