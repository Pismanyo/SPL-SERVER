package bgu.spl.net.api;


public class PairForMe {
    private int first;
    private int second;
    public PairForMe(int first,int second)
    {
        this.first=first;
        this.second=second;
    }
    public int getFirst()
    {
        return first;
    }
    public int getSecond()
    {
        return second;
    }
    boolean isEqual(Object a)
    {
        if(a==null)
            return false;
        if(!(a instanceof PairForMe))
            return false;
        if(((PairForMe) a).first==this.first&&((PairForMe) a).second==this.second)
            return true;
        return false;
    }
}
