package bgu.spl.net.srv;

import bgu.spl.net.api.MessageEncoderDecoder;
import bgu.spl.net.api.MessagingProtocol;
import bgu.spl.net.api.StompMessagingProtocol;
import bgu.spl.net.api.StompMessagingProtocolImpl;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.util.function.Supplier;

public abstract class BaseServer<T> implements Server<T> {

    private final int port;
    private final Supplier<MessageEncoderDecoder<T>> encdecFactory;
    private final Supplier<StompMessagingProtocol> stompFactory;
    //private StompMessagingProtocolImpl stomp;
    private Connections talker;
    private ServerSocket sock;

    public BaseServer(
            int port, Supplier<StompMessagingProtocol>stompFactory,
            Supplier<MessageEncoderDecoder<T>> encdecFactory) {
        this.stompFactory=stompFactory;
        this.port = port;
        this.encdecFactory = encdecFactory;
		this.sock = null;
		talker=ConnectionsiImp.getInstance();
    }

    @Override
    public void serve() {

        try (ServerSocket serverSock = new ServerSocket(port)) {
			System.out.println("Server started");

            this.sock = serverSock; //just to be able to close

            while (!Thread.currentThread().isInterrupted()) {


                Socket clientSock = serverSock.accept();
                int id=talker.getnext();
                StompMessagingProtocol stomp=stompFactory.get();
               stomp.start( id,talker);

                BlockingConnectionHandler<T> handler = new BlockingConnectionHandler<T>(
                        clientSock,
                        encdecFactory.get(),stomp);
                talker.addConnectionHandler(handler,id);


                execute(handler);
            }
        } catch (IOException ex) {
        }

        System.out.println("server closed!!!");
    }

    @Override
    public void close() throws IOException {
		if (sock != null)
			sock.close();
    }

    protected abstract void execute(BlockingConnectionHandler<T>  handler);

}
