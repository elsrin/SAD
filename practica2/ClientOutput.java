
import java.util.logging.Level;
import java.util.logging.Logger;

public class ClientOutput extends Thread {
    
    public String nick;
    public MySocket socket;
    
    public ClientOutput(MySocket socket) {
        this.socket = socket;
    }
    
    // Output Thread
    @Override
    public void run() {
        String line;
        try {
            //hi ha línia del servidor
            while ((line = socket.readLine()) != null) {
                System.out.println(line);//escriure línia per pantalla.
            }
            socket.close(); //rep final de dades i acaba.
        } catch (Exception ex) {
            Logger.getLogger(ClientOutput.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
