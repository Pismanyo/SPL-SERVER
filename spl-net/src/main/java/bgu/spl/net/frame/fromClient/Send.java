package bgu.spl.net.frame.fromClient;

import bgu.spl.net.api.StompMessagingProtocolImpl;
import bgu.spl.net.frame.toClient.Error;
import bgu.spl.net.frame.toClient.Message;

public class Send implements Frame{
    private Error e = new Error("");
    private Message toSend;

    public Send(StompMessagingProtocolImpl stompMessagingProtocol) {
    }


    public boolean process(String msg) {

        String topicLessMsg = msg.substring(0, '\n');
        String line = topicLessMsg.substring(0, '\n');
        if (lineprocess(line, "destination")) {
            int a = line.indexOf(':');
            String topicMsg = line.substring(a + 1);
            if (topicMsg.length() != 0) {
                toSend = new Message(topicMsg);

            } else {
                String errorMsg = e.getMsg();
                errorMsg += "Not a valid version input" + '\n';
                e.setMsg(errorMsg);
                return false;
            }
        }
        return true;
    }

    public boolean lineprocess(String line, String wantedHeader) {
        return (line.indexOf(':') != -1 && (line.substring(line.indexOf(':')).equals(wantedHeader)));
    }

    public Message getToSend() {
        return toSend;
    }

    public Error getError(){
        return e;
    }
}
