package bgu.spl.net.frame.fromClient;

import bgu.spl.net.api.Messageformat;
import bgu.spl.net.api.StompMessagingProtocol;
import bgu.spl.net.api.StompMessagingProtocolImpl;
import bgu.spl.net.frame.toClient.Error;
import bgu.spl.net.srv.Connections;
import bgu.spl.net.srv.ConnectionsiImp;
import bgu.spl.net.srv.User;

public class Unsubscribe implements Frame {
    private StompMessagingProtocolImpl stomp;
    private String[] format={"id:"};

    public Unsubscribe(StompMessagingProtocolImpl stompMessagingProtocol) {
        stomp=stompMessagingProtocol;
    }

    @Override
    public boolean process(String msg) {
        Messageformat a=new Messageformat();
        String[] headers= a.messageformat(msg,format);
        if(headers==null)
        {
            Error erro=new Error("Incorrect format.");
            erro.setMsg(msg);
            ConnectionsiImp.getInstance().send(stomp.getconnectid(),erro.toString());
            return false;
        }

        User active=stomp.getuser();
        String topic =active.getTopic((Integer.parseInt(headers[0])));
        if(topic==null)
        {
            Error erro=new Error("Not subsribed under given id.");
            erro.setMsg(msg);
            ConnectionsiImp.getInstance().send(stomp.getconnectid(),erro.toString());
            return false;

        }
        active.unscribe((Integer.parseInt(headers[0])));
        return true;
    }
}
