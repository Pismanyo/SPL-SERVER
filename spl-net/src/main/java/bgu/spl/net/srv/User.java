package bgu.spl.net.srv;

import java.util.LinkedList;
import java.util.concurrent.ConcurrentHashMap;

public class User {
    private String username;
    private String password;
    private ConcurrentHashMap<Integer,String> subscibe;
    private ConcurrentHashMap<String,Integer> topicToId;

    public User(String username, String password)
    {
        this.password=password;
        this.username=username;
        subscibe=new ConcurrentHashMap<>();
        topicToId=new ConcurrentHashMap<>();
    }
    public User()
    {

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
}
