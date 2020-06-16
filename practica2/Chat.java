
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.Thread;

public class Chat {
    
    public static final int PORT=4444;
    
    public static void main(String[] args) throws Exception {
        final MySocket sc = new MySocket("localhost", PORT);
        
        // Input thread
        new ClientInput(sc).start();
        // Output thread
        new ClientOutput(sc).start();
    }
}