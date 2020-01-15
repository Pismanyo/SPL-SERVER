package bgu.spl.net.api;


import java.util.HashMap;

public class Messageformat {
    public String[] messageformat(String msg, String[] headers, boolean hasbody)
    {
        String[] splitMsg = msg.split("\n");
        String []ans=new String[headers.length+1];

        for(int a=0; a<headers.length;a++)
        {
            String[] headline=splitMsg[a+1].split(":");
            if(!(headline[0]+":").equals(headers[a]))
                return null;
            else{
            ans[a] = headline[1];
            }
        }
        ans[headers.length]="";
        if(hasbody) {
            for(int i=(headers.length+2);i<splitMsg.length;i++)
            {
                ans[headers.length]=ans[headers.length]+splitMsg[i]+"\n";
            }
        }

        return ans;


        }



}
