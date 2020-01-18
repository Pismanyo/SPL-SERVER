package bgu.spl.net.srv;

import bgu.spl.net.frame.toClient.Message;

public interface Connections<T> {

    void send(T topic, Message message);

    boolean send(int connectionId, T msg);

    void send(String channel, T msg);

    void disconnect(int connectionId);
    void addConnectionHandler(ConnectionHandler a,int id);
    int getnext();
    boolean subscribe(String topic,int subscriberid,int connectionid);
    boolean unsubscribe(String topic,int SubsriptionId,int connectionId);
}
