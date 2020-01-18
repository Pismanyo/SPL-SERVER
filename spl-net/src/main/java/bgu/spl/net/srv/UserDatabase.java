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

    /**
     * Adds a user to the user database.
     * @param a A user to add to the database.
     * @return True if the user was added to the database and false otherwise.
     */
    public synchronized boolean addUser(User a)
    {
        if(!userdata.containsKey(a.getUsername())) {
            userdata.put(a.getUsername(), a);
            return true;
        }
        else{
            return false;
        }
    }

    /**
     * Searches for a user in the database.
     * @param username Users username.
     * @param password Users password.
     * @return A user if one matching the username and password exists and null otherwise.
     */
    public User findUser(String username,String password)
    {
        if(userdata.containsKey(username)) {
            if(userdata.get(username).getPassword().equals(password))
                 return userdata.get(username);
        }
         return null;

    }
}
