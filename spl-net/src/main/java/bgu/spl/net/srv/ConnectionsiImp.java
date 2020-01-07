package bgu.spl.net.srv;

public class ConnectionsiImp  implements Connections<String> {

    @Override
    public boolean send(int connectionId, String msg) {
        return false;
    }

    @Override
    public void send(String channel, String msg) {

    }

    @Override
    public void disconnect(int connectionId) {

    }
}
