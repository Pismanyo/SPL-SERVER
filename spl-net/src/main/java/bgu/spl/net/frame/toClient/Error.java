package bgu.spl.net.frame.toClient;

import bgu.spl.net.frame.fromClient.Frame;

public class Error implements Frame {
    private String sentMessage;
    private String errorMessage;
    private String type;
    private String receiptId;

    public Error(String msg){
        this.errorMessage=msg;
    }

    public String getMsg() {
        return errorMessage;
    }

    public void setMsg(String msg) {
        this.errorMessage = msg;
    }
    public String toString()
    {
        return  "ERROR"+'\n'
                +"receipt-id:"+receiptId+'\n'
                +"message: malformed frame received"+'\n'
                +"-----"+'\n'
                +sentMessage+'\n'
                +"-----"+'\n'
                +errorMessage+'\n'
                +'\n'+
                +'\u0000';
    }

}
