package bgu.spl.net.frame.toClient;

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
