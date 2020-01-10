package bgu.spl.net.api;


public class Messageformat {
    public String[] messageformat(String msg,String[] headers)
    {
        String copyodmessage = msg.substring('\n');

        String[] ans=new String[headers.length+1];
        for(int a=0; a<headers.length;a++)
        {
            while(copyodmessage.indexOf('\n')==0)
                copyodmessage.substring(1);
            String headercheack=copyodmessage.substring(0,copyodmessage.indexOf(':'));
            if(headercheack==headers[a])
            {
                ans[a]=copyodmessage.substring(':'+1,'\n');
            }
            else{
                return null;
            }
            copyodmessage=copyodmessage.substring('\n'+1);
        }
        while(copyodmessage.indexOf('\n')==0)
            copyodmessage.substring(1);
        ans[ans.length-1]=copyodmessage;

        return ans;


        }



}
