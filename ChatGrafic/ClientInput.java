package ChatGrafic;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ClientInput extends Thread{
    
    //public String nick;
    public MySocket socket;
    
    public ClientInput(MySocket socket) {
        this.socket = socket;
    }
    
    // Input Thread
    @Override
    public void run() {
        try {
            String line;
            BufferedReader teclado = new BufferedReader(new InputStreamReader(System.in));;
            //llegim per teclat.
            while((line = teclado.readLine()) != null) {
                socket.println(line); //escriure línia per socket.
            }
            socket.shutdownOutput(); //tanca l'escriptura del socket.
        } catch (Exception ex) {
            Logger.getLogger(ClientInput.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}