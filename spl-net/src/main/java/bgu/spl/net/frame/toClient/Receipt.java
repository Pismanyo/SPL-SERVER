package bgu.spl.net.frame.toClient;

import bgu.spl.net.frame.fromClient.Frame;

public class Receipt {
    private int receiptid;

    public Receipt(int receiptid){
        this.receiptid=receiptid;
    }
    public String toString()
    {
        return "RECEIPT"+"\n"
                +"receipt-id:"+receiptid+"\n\n"
                +"\u0000";
    }
}
