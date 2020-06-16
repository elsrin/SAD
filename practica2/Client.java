package practica2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Client {
    
    public String nick;
    public MySocket socket;
    
    public Client(MySocket socket) {
        this.socket = socket;
    }
    
    // Input Thread
    public void ClientInput() throws Exception{
        String line;
        BufferedReader teclado = new BufferedReader(new InputStreamReader(System.in));;
        while((line = teclado.readLine()) != null) {
            socket.println(line); //escriure línia per socket.
        }
        socket.shutdownOutput(); //tanca l'escriptura del socket.
    }
    
    // Output Thread
    public void ClientOutput() throws Exception {
        String line;
        //hi ha línia del servidor
        while ((line = socket.readLine()) != null) {
            System.out.println(line);//escriure línia per pantalla.
        }
        socket.close(); //rep final de dades i acaba.
    }
}
