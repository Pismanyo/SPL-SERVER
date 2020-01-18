package bgu.spl.net.frame.fromClient;

/**
 * Processes the message received from the client depending on the frame type.
 */
public interface Frame {
    boolean process( String msg);


}
