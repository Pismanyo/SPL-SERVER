package bgu.spl.net.frame.toClient;

import bgu.spl.net.frame.fromClient.Frame;

public class Error implements Frame {
    private String sentMessage;
    private String errorMessage;
    private String type;
    private String receiptId="";

    public Error(String msg,String errorMessage){
        this.sentMessage=msg;
        this.errorMessage=errorMessage;
    }

    public String getMsg() {
        return sentMessage;
    }
    public void setRecpitId(String receiptId) {
        this.receiptId = receiptId;
    }

    public void setErrorOfMsg(String msg) {
        this.errorMessage = msg;
    }
    public String toString()
    {
        String ans=  "ERROR"+'\n';
        if(receiptId!="")
        {
            ans=ans+"receipt-id:"+receiptId+'\n';
        }
        ans=ans +"message: malformed frame received"+'\n'
                +"-----"+"\n"
                +"\n"+
                sentMessage+"\n"
                +"-----"+"\n"
                +errorMessage+"\n"
                +"\n"+
                + '\u0000';
        return ans;
    }

    @Override
    public boolean process(String msg) {
        return false;
    }
}
