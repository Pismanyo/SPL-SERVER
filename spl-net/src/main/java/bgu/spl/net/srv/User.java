package bgu.spl.net.srv;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.concurrent.ConcurrentHashMap;

public class User {
    private String username;
    private String password;
    private HashMap<Integer,String> subscibe;
    private HashMap<String,Integer> topicToId;
    private boolean active;

    public User(String username, String password)
    {
        this.password=password;
        this.username=username;
        subscibe=new HashMap<>();
        topicToId=new HashMap<>();
        active=true;
    }
    public User()
    {

    }

    public void disconnect(int conectionId)
    {
        Collection<String>a=subscibe.values();
        Object[] arr = a.toArray();
        String[] toRomove = new String[arr.length];
        int index=0;
        for(Object o: arr) {
            toRomove[index]=(String)o;
            ++index;
        }
        for(int i=0;i<toRomove.length;i++)
        {
            ConnectionsiImp.getInstance().unsubscribe(toRomove[i],topicToId.get(toRomove[i]),conectionId);
        }
        subscibe.clear();
        topicToId.clear();
    }
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    public boolean isSubscribed(String topic)
    {
        return subscibe.containsValue(topic);
    }

    public boolean addSubscribe(int id, String topic) {
        if(!subscibe.containsKey(id)&&!subscibe.containsValue(topic)) {
            subscibe.put( id,topic);
            topicToId.put(topic,id);
            return true;
        }
        return false;

    }
    public Integer getId(String topic)
    {
        if(topicToId.containsKey(topic))
        {
            return topicToId.get(topic);
        }
        return null;
    }
    public String getTopic(int id)
    {
        if(subscibe.containsKey(id))
        {
            return subscibe.get(id);
        }
        return null;

    }
    public boolean unscribe(int id)
    {

        if(subscibe.containsKey(id))
        {
            String ans=subscibe.get(id);
           // ConnectionsiImp.getInstance().ub
            topicToId.remove(ans,id);
            return subscibe.remove(id,ans);
        }

        return false;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
