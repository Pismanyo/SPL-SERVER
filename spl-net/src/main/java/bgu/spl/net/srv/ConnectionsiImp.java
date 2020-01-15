package bgu.spl.net.srv;

import bgu.spl.net.api.PairForMe;
import bgu.spl.net.frame.toClient.Message;

import javax.swing.text.html.HTMLDocument;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.atomic.AtomicInteger;

public class ConnectionsiImp implements Connections<String> {
    private ConcurrentHashMap<String, ConcurrentSkipListMap<Integer, PairForMe>> subscribersIdToConnectionId;
    private ConcurrentHashMap<Integer, ConnectionHandler> idToConectionHandler;
    private AtomicInteger idCounter;
    private AtomicInteger messageid;
    public static class connectionHolder {
        private static ConnectionsiImp instance = new ConnectionsiImp();
    }
    private ConnectionsiImp() {
        subscribersIdToConnectionId = new ConcurrentHashMap<>();
        idToConectionHandler = new ConcurrentHashMap<>();
        idCounter = new AtomicInteger(0);
        messageid = new AtomicInteger(0);
    }
    public static ConnectionsiImp getInstance() {
        return ConnectionsiImp.connectionHolder.instance;
    }
//    public ConnectionsiImp()
//    {
//        idToConectionHandler=new ConcurrentHashMap<>();
//        idCounter=0;
//    }
    @Override
    public void addConnectionHandler(ConnectionHandler a,int id)
    {
        idToConectionHandler.put(id,a);
    }


    @Override
    public int getnext() {
        return idCounter.incrementAndGet();
    }

    @Override
    public boolean subscribe( String topic,int subscriptionid, int connectionid) {
        subscribersIdToConnectionId.putIfAbsent(topic,new ConcurrentSkipListMap<>((a,b)->{return b-a;}));//syncrized problem
        if(subscribersIdToConnectionId.get(topic).containsKey(subscriptionid))
            return false;
        subscribersIdToConnectionId.get(topic).put(subscriptionid,new PairForMe(subscriptionid,connectionid));
        return true;
    }


    @Override
    public boolean unsubscribe(String topic,int SubsriptionId,int connectionId) {

        subscribersIdToConnectionId.get(topic).remove(SubsriptionId);//should check what was removed
        return true;
    }

    @Override
    public synchronized void send(String topic, Message message) {
        NavigableSet<Integer> toSend =this.subscribersIdToConnectionId.get(topic).descendingKeySet();
        Iterator<Integer> a= toSend.descendingIterator();

        while (a.hasNext())
        {
            message.setMessageid(this.messageid.incrementAndGet());
            int temp=a.next();
            message.setSubsriptionid(temp);
            int connectid=this.subscribersIdToConnectionId.get(topic).get(temp).getSecond();
            if(!this.send(connectid,message.toString()))
                subscribersIdToConnectionId.get(topic).remove(temp);
        }



    }


    @Override
    public synchronized boolean send(int connectionId, String msg) {
        if(idToConectionHandler.containsKey(connectionId))
        {
            idToConectionHandler.get(connectionId).send(msg);
            return true;
        }
        return false;
    }

    @Override
    public void send(String topic, String msg) {
        NavigableSet<Integer> toSend =this.subscribersIdToConnectionId.get(topic).descendingKeySet();
        Iterator<Integer> a= toSend.descendingIterator();
        while (a.hasNext())
        {
            int temp=a.next();
            if(!this.send(this.subscribersIdToConnectionId.get(topic).get(temp).getSecond(),msg))
                subscribersIdToConnectionId.get(topic).remove(temp);
        }
    }

    @Override
    public void disconnect(int connectionId) {

        idToConectionHandler.remove(connectionId);
    }
    public int Messageidcount()
    {
        return messageid.incrementAndGet();
    }

}
