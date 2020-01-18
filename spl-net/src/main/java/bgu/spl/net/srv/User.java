package bgu.spl.net.srv;

import java.util.Collection;
import java.util.HashMap;

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

    /**
     * Removes user books upon receiving disconnection frame.
     * @param conectionId Client connection id to handle.
     */
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

    public String getPassword() {
        return password;
    }

    public boolean isSubscribed(String topic)
    {
        return subscibe.containsValue(topic);
    }

    /**
     * Adds a subscriber to a desired topic.
     * @param id Subscriber id.
     * @param topic Topic to subscribe to.
     * @return True if subscriber was added and false otherwise.
     */
    public boolean addSubscribe(int id, String topic) {
        if(!subscibe.containsKey(id)&&!subscibe.containsValue(topic)) {
            subscibe.put( id,topic);
            topicToId.put(topic,id);
            return true;
        }
        return false;

    }

    public String getTopic(int id)
    {
        if(subscibe.containsKey(id))
        {
            return subscibe.get(id);
        }
        return null;

    }

    /**
     * Unsubscribers a client from a topic by id.
     * @param id The subscribers id
     * @return True if unsubscribed and false otherwise.
     */
    public boolean unscribe(int id)
    {
        if(subscibe.containsKey(id))
        {
            String ans=subscibe.get(id);
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
