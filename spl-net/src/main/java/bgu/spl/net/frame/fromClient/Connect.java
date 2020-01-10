package bgu.spl.net.frame.fromClient;

import bgu.spl.net.api.StompMessagingProtocol;
import bgu.spl.net.api.StompMessagingProtocolImpl;
import bgu.spl.net.frame.toClient.Connected;
import bgu.spl.net.frame.toClient.Error;
import bgu.spl.net.srv.User;

public class Connect implements Frame {
    private User u;
    private Connected c;
    private Error error= new Error("");
    private StompMessagingProtocol stomp;
    private String[] format={"accept-version:","host:","login:"};




    public Connect(StompMessagingProtocolImpl stompMessagingProtocol){
        stomp=stompMessagingProtocol;
        c=null;
        u=null;
    }

    public boolean process(String msg){
        c = new Connected();
        u = new User();
        String line = msg.substring(0,'\n');
        if(lineprocess(line,"accept-version"))
        {
            int a=line.indexOf(':');
            String vernum=line.substring(a+1);
            if(vernum.length()!=0) {
                c.setVersion(Double.parseDouble(vernum));
            }
            else {
                String errorMsg = error.getMsg();
                errorMsg+="Not a valid version input"+'\n';
                error.setMsg(errorMsg);
                return false;}
        }
        else return false;
        msg=msg.substring('\n');
        line=msg.substring(0,'\n');
        if(lineprocess(line,"login")){
            if(line.substring(line.indexOf(':')).length()>1){
                u.setUsername( line.substring(line.indexOf(':')+1));
            }
            else {

                return false;
            }
        }
        else return false;

        msg=msg.substring('\n');
        line = msg.substring(0,'\n');
        if(lineprocess(line,"passcode")){
            if(line.substring(line.indexOf(':')).length()>1){
                u.setPassword(line.substring(line.indexOf(':')+1));
            }
            else return false;
        }
        else return false;
        Error e;
        if(u.getPassword()==null || u.getUsername()==null || c.getVersion()==null)
             e = new Error("Malformed frame received");
          return true;

        }
        public boolean lineprocess(String line, String wantedHeader)
        {
            return (line.indexOf(':')!=-1&&(line.substring(line.indexOf(':')).equals(wantedHeader)));
        }
        public User  getUser()
        {
            return u;
        }
        public Connected getConnected()
        {
            return c;
        }
    }
