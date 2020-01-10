package bgu.spl.net.srv;

import java.io.IOException;

public interface Connections<T> {

    boolean send(int connectionId, T msg);

    void send(String channel, T msg);

    void disconnect(int connectionId);
    void addConnectionHandler(ConnectionHandler a,int id);
    int getnext();
    void subscribe(String topic,int connectionid);
    boolean unsubscribe(String topic,int connectionid);

}
