package bgu.spl.net.srv;

import java.util.concurrent.ConcurrentHashMap;

public class UserDatabase {
    private ConcurrentHashMap<String,User> userdata;
    public static class userHolder {
        private static UserDatabase instance = new UserDatabase();
    }
    private UserDatabase(){

        userdata = new ConcurrentHashMap<>();
    }
    public static UserDatabase getInstance() {
        return userHolder.instance;
    }
    public synchronized boolean addUser(User a)
    {
        if(!userdata.containsKey(a.getUsername())) {
            userdata.put(a.getUsername(), a);
            return true;
        }
        else return false;

    }

    public User findUser(String username,String password)
    {
        if(userdata.containsKey(username)) {
            if(userdata.get(username).getPassword()==password)
                 return userdata.get(username);
        }
         return null;

    }


}
