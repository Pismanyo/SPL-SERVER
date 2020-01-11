package bgu.spl.net.frame.toClient;

public class Connected {
    private Double version;

    public Connected(Double version){this. version=version;}

    public Double getVersion() { return version; }

    public void setVersion(Double version) { this.version = version; }

    public String toString(){ return "CONNECTED"+'\n'+"version:"+version+'\n'+'\u0000'; }
}
