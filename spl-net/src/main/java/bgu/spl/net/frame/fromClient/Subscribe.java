package bgu.spl.net.frame.fromClient;

import bgu.spl.net.api.Messageformat;
import bgu.spl.net.api.StompMessagingProtocol;
import bgu.spl.net.api.StompMessagingProtocolImpl;
import bgu.spl.net.frame.toClient.Error;
import bgu.spl.net.frame.toClient.Receipt;
import bgu.spl.net.srv.Connections;
import bgu.spl.net.srv.ConnectionsiImp;
import bgu.spl.net.srv.User;
import bgu.spl.net.frame.toClient.Error;
public class Subscribe implements Frame {
    private StompMessagingProtocolImpl stomp;
    private String[] format={"destination:","id:","receipt:"};


    public Subscribe(StompMessagingProtocolImpl stompMessagingProtocol) {
        stomp=stompMessagingProtocol;
    }

    @Override
    public boolean process(String msg) {
        Messageformat a=new Messageformat();
        ConnectionsiImp connect=ConnectionsiImp.getInstance();
        String[] headers= a.messageformat(msg,format);
        if(headers==null)
        {
            Error erro=new Error("Incorrect format.");
            erro.setMsg(msg);
            connect.send(stomp.getconnectid(),erro.toString());
            return false;
        }

        User active=stomp.getuser();
       if( !active.addSubscribe((Integer.parseInt(headers[1])),headers[0]))
       {
            Error erro=new Error("already subsribed under given id, or already subsribed to topic");
            erro.setMsg(msg);
           connect.send(stomp.getconnectid(),erro.toString());
            return false;

        }
        connect.subscribe(headers[0],stomp.getconnectid());
        Receipt ans= new Receipt(Integer.parseInt(headers[2]));
       connect.send(stomp.getconnectid(),ans.toString());

        return true;

    }
}
