
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

public class ChatServidor {

    public static final int PORT = 4444;

    public static void main(String[] args) throws Exception {
        final MyServerSocket ss = new MyServerSocket(PORT);
        final ConcurrentHashMap<MySocket, String> biblioteca
                = new ConcurrentHashMap<MySocket, String>();

        while (true) {
            final MySocket s = ss.accept();
            // Asigna un nom de convidat si no s'inserta cap.
            int random = 0;
            String color = "\033[30m";
            int numero=30;
            while (s.nick.isEmpty()) {
                random = new Random().nextInt(100);
                if (!biblioteca.containsValue("Guest" + random)) {
                    s.nick = "Guest"+random;
                }
            }
            if (!s.nick.equals("Guest"+random)){
                random = new Random().nextInt(5);
                numero = 30+1+random;
            }
            color = "\033["+numero+"m";
            biblioteca.put(s, color+s.nick+"\033[30m");
            System.out.println(s.nick + " connected...");

            // Thread que processara els missatges del client.
            new Servidor(s,biblioteca).start();
        }
    }
}
