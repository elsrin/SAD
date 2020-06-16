
import java.io.IOException;
import java.net.ServerSocket;

/* A server socket waits for requests to come in over the network. It performs 
 * some operation based on that request, and then possibly returns a result 
 * to the requester.*/

public class MyServerSocket {
    
    public ServerSocket socket;
    
    public MyServerSocket() throws IOException {
        socket = new ServerSocket();
    }
    public MyServerSocket(int port) throws IOException {
        socket = new ServerSocket(port);
    }
    
    public MySocket accept() throws Exception {
        return new MySocket(socket.accept());
    }
}