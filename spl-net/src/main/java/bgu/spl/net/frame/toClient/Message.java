package bgu.spl.net.frame.toClient;

import bgu.spl.net.frame.fromClient.Frame;
import bgu.spl.net.srv.ConnectionsiImp;

public class Message {
    private String msg;
    private String subsriptionid;
    private String messageid;
    private String topic;
    public Message(String topic,String msg){
        this.topic=topic;
        this.msg=msg;
    }

    public void setSubsriptionid(Integer subsriptionid)
    {
        this.subsriptionid=subsriptionid.toString();
    }


    public void setMessageid(Integer messageNum) {

        this.messageid = messageNum.toString();
        while (messageid.length()<5)
            messageid="0"+messageid;
    }

    public String toString()
    {
        return "MESSAGE"+"\n"
                +"subscription:"+subsriptionid+"\n"
                +"Message-id:"+messageid+"\n"
                +"destination:"+topic+"\n\n"+
                msg
                +"\u0000";
    }
}