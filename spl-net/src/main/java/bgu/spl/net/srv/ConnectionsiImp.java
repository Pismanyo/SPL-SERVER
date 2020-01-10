package bgu.spl.net.srv;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingDeque;

public class ConnectionsiImp  implements Connections<String> {
    private ConcurrentHashMap<String, LinkedBlockingDeque<Integer>> chanel;
    //private ConcurrentHashMap<String, ConcurrentHashMap<Integer,Integer>> subcribed;
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
    public void subscribe( String topic, int connectionid) {
        if(!chanel.containsKey(topic))
        {
            chanel.put(topic,new LinkedBlockingDeque<>());//syncrized problem
        }
        chanel.get(topic).add(connectionid);

    }


    @Override
    public boolean unsubscribe(String topic,int connectionId) {
        if(!chanel.contains(topic))
            return false;
        if(!chanel.get(topic).contains(connectionId))
            return false;
        chanel.get(topic).remove(connectionId);//should check what was removed
        return true;
    }


    @Override
    public synchronized boolean send(int connectionId, String msg) {
        if(conectinhandeler.containsKey(connectionId))
        {
            conectinhandeler.get(connectionId).send(msg);
            return true;
        }
        return false;
    }

    @Override
    public void send(String topic, String msg) {
        if(chanel.containsKey(topic))
        {
            LinkedBlockingDeque<Integer> a=chanel.get(topic);
            for(int temp:a)
            {
                send(temp,msg);
            }

        }


    }

    @Override
    public void disconnect(int connectionId) {
        conectinhandeler.remove(connectionId);
    }

}
