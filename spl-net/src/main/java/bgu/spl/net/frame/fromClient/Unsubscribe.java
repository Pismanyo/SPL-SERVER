package bgu.spl.net.frame.fromClient;

import bgu.spl.net.api.Messageformat;
import bgu.spl.net.api.StompMessagingProtocol;
import bgu.spl.net.api.StompMessagingProtocolImpl;
import bgu.spl.net.frame.toClient.Error;
import bgu.spl.net.frame.toClient.Receipt;
import bgu.spl.net.srv.Connections;
import bgu.spl.net.srv.ConnectionsiImp;
import bgu.spl.net.srv.User;

public class Unsubscribe implements Frame {
    private StompMessagingProtocolImpl stomp;
    private String[] format={"id:"};
    private boolean hasbody=false;

    public Unsubscribe(StompMessagingProtocolImpl stompMessagingProtocol) {
        stomp=stompMessagingProtocol;
    }

    @Override
    public boolean process(String msg) {
        Messageformat a=new Messageformat();
        ConnectionsiImp connect=ConnectionsiImp.getInstance();
        String[] headers= a.messageformat(msg,format,hasbody);
        if(headers==null)
        {
            Error erro=new Error(msg,"Incorrect format.");
            ConnectionsiImp.getInstance().send(stomp.getconnectid(),erro.toString());
            return false;
        }

        User active=stomp.getuser();
        if(active==null)
        {
            Error erro=new Error(msg,"No User connected to");
            ConnectionsiImp.getInstance().send(stomp.getconnectid(),erro.toString());
            return false;


        }
        String topic =active.getTopic((Integer.parseInt(headers[0])));
        if(topic==null)
        {
            Error erro=new Error(msg,"Not subsribed under given id.");
            ConnectionsiImp.getInstance().send(stomp.getconnectid(),erro.toString());
            return false;

        }
        if(!active.unscribe((Integer.parseInt(headers[0]))))
        {
            Error erro=new Error(msg,"Not subsribed under given id to topic.");
            ConnectionsiImp.getInstance().send(stomp.getconnectid(),erro.toString());
            return false;

        }
        Receipt ans=new Receipt(Integer.parseInt(headers[0]));
        connect.send(stomp.getconnectid(),ans.toString());

        return true;
    }
}
