package bgu.spl.net.srv;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingDeque;

public class ConnectionsiImp  implements Connections<String> {
    private ConcurrentHashMap<String, LinkedBlockingDeque<Integer>> chanel;
    private ConcurrentHashMap<Integer, String> subcribed;
    private ConcurrentHashMap<Integer, ConnectionHandler> conectinhandeler;
    private int idCounter;
    public static class connectionHolder {
        private static ConnectionsiImp instance = new ConnectionsiImp();
    }
    private ConnectionsiImp(){

        conectinhandeler=new ConcurrentHashMap<>();
        idCounter=0;
    }
    public static ConnectionsiImp getInstance() {
        return ConnectionsiImp.connectionHolder.instance;
    }
//    public ConnectionsiImp()
//    {
//        conectinhandeler=new ConcurrentHashMap<>();
//        idCounter=0;
//    }
    @Override
    public void addConnectionHandler(ConnectionHandler a,int id)
    {
        conectinhandeler.put(id,a);
    }


    @Override
    public int getnext() {
        idCounter++;
        return idCounter;
    }

    @Override
    public void subscribe(int connectionId, String topic) {
        if (!chanel.contains(topic))
        {
            chanel.put(topic, new LinkedBlockingDeque<>());
        }
        chanel.get(topic).add(connectionId);
        subcribed.put(connectionId,topic);

    }

    @Override
    public boolean unsubscribe(int connectionId) {
        if(!subcribed.contains(connectionId))
            return false;
        chanel.get(subcribed.get(connectionId)).remove(connectionId);
        subcribed.remove(connectionId);
        return true;
    }


    @Override
    public boolean send(int connectionId, String msg) {
        conectinhandeler.get(connectionId).send(msg);

        return true;
    }

    @Override
    public void send(String channel, String msg) {

    }

    @Override
    public void disconnect(int connectionId) {
        unsubscribe(connectionId);
        conectinhandeler.remove(connectionId);
    }

}
