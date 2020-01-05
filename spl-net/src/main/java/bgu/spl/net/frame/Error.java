package bgu.spl.net.frame;

public class Error implements Frame {
    private String msg;

    public Error(String msg){
        this.msg=msg;
    }
}
