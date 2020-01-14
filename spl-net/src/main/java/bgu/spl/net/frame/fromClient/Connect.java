package bgu.spl.net.frame.fromClient;

import bgu.spl.net.api.Messageformat;
import bgu.spl.net.api.StompMessagingProtocol;
import bgu.spl.net.api.StompMessagingProtocolImpl;
import bgu.spl.net.frame.toClient.Connected;
import bgu.spl.net.frame.toClient.Error;
import bgu.spl.net.srv.ConnectionsiImp;
import bgu.spl.net.srv.User;
import bgu.spl.net.srv.UserDatabase;

public class Connect implements Frame {
    private StompMessagingProtocolImpl stomp;
    private String[] format={"accept-version:","host:","login:","passcode:"};
    private boolean hasbody=false;




    public Connect(StompMessagingProtocolImpl stompMessagingProtocol){
        stomp=stompMessagingProtocol;
    }

    public boolean process(String msg) {
        Messageformat a = new Messageformat();
        ConnectionsiImp connect = ConnectionsiImp.getInstance();
        String[] headers = a.messageformat(msg, format,hasbody);
        for(String s:headers)
            System.out.println(s);
        if (headers == null) {
            Error erro = new Error("Incorrect format.");
            erro.setMsg(msg);
            connect.send(stomp.getconnectid(), erro.toString());
            return false;
        }
        if (stomp.getuser() != null) {
            Error erro = new Error("Already connected to user.");
            erro.setMsg(msg);
            connect.send(stomp.getconnectid(), erro.toString());
            return false;
        }
        UserDatabase data = UserDatabase.getInstance();

        User user = data.findUser(headers[2],headers[3]);
        if (user!=null&&user.isActive()) {
            Error erro = new Error("User already in use.");
            erro.setMsg(msg);
            connect.send(stomp.getconnectid(), erro.toString());
            return false;
        }
        if(user==null)
        {
            user=new User(headers[2],headers[3]);
            if (!data.addUser(user)) {
                Error erro = new Error("Username already used.");
                erro.setMsg(msg);
                connect.send(stomp.getconnectid(), erro.toString());
                return false;
            }
            data.addUser(user);

        }
        stomp.setactiveUser(user);
        Connected ans = new Connected(Double.parseDouble(headers[0]));
        connect.send(stomp.getconnectid(), ans.toString());
        return true;
    }

    }
