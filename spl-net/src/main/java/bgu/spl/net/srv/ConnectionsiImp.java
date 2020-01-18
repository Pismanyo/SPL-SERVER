package bgu.spl.net.srv;

import bgu.spl.net.api.PairForMe;
import bgu.spl.net.frame.toClient.Message;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListMap;
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
    @Override
    public void addConnectionHandler(ConnectionHandler a,int id)
    {
        idToConectionHandler.put(id,a);
    }

    @Override
    public int getnext() {
        return idCounter.incrementAndGet();
    }

    /**
     * Subscribes a client to a topic.
     * @param topic A topic to subscribe to.
     * @param subscriptionid A client subscription id.
     * @param connectionid Clients connection id.
     * @return True if the client was subscribed and false otherwise.
     */
    @Override
    public boolean subscribe( String topic,int subscriptionid, int connectionid) {
        subscribersIdToConnectionId.putIfAbsent(topic,new ConcurrentSkipListMap<>((a,b)->{return b-a;}));//syncrized problem
        if(subscribersIdToConnectionId.get(topic).containsKey(subscriptionid))
            return false;
        subscribersIdToConnectionId.get(topic).put(subscriptionid,new PairForMe(subscriptionid,connectionid));
        return true;
    }

    /**
     * Unsubscribes a client from a topic.
     * @param topic A topic to unsubscribe from.
     * @param SubsriptionId Clients subscriber id to that topic.
     * @param connectionId Clients connection id.
     * @return True if the client was unsubscribed and false otherwise.
     */
    @Override
    public boolean unsubscribe(String topic,int SubsriptionId,int connectionId) {

        subscribersIdToConnectionId.get(topic).remove(SubsriptionId);//should check what was removed
        return true;
    }

    /**
     * Sends a message to clients subscribed to a certain topic
     * @param topic A topic clients are subscribed to.
     * @param message A message to send to all the clients subscribed to the topic.
     */
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

    /**
     * Sends a message back to the client.
     * @param connectionId A clients id to send to.
     * @param msg The message to send back to the client.
     * @return True if message was passed and false otherwise.
     */
    @Override
    public synchronized boolean send(int connectionId, String msg) {
        if(idToConectionHandler.containsKey(connectionId))
        {
            idToConectionHandler.get(connectionId).send(msg);
            return true;
        }
        return false;
    }

    /**
     * Sends a message to clients subscribed to a certain topic
     * @param topic A topic clients are subscribed to.
     * @param msg A message to send to all the clients subscribed to the topic.
     */
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

    /**
     * Disconnects a client from the server.
     * @param connectionId A client id to remove from the server.
     */
    @Override
    public void disconnect(int connectionId) {
        idToConectionHandler.remove(connectionId);
    }

}
