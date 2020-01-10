package bgu.spl.net.srv;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.atomic.AtomicInteger;

public class ConnectionsiImp  implements Connections<String> {
    private ConcurrentHashMap<String, LinkedBlockingDeque<Integer>> chanel;
    //private ConcurrentHashMap<String, ConcurrentHashMap<Integer,Integer>> subcribed;
    private ConcurrentHashMap<Integer, ConnectionHandler> conectinhandeler;
    private AtomicInteger idCounter;
    private AtomicInteger messageid;
    public static class connectionHolder {
        private static ConnectionsiImp instance = new ConnectionsiImp();
    }
    private ConnectionsiImp(){

        conectinhandeler=new ConcurrentHashMap<>();
        idCounter=new AtomicInteger(0);
        messageid=new AtomicInteger(0);
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
        return idCounter.incrementAndGet();
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
    public int Messageidcount()
    {
        return messageid.incrementAndGet();
    }

}
