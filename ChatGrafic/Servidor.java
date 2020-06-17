package ChatGrafic;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

public class Servidor extends Thread {
    
    private ConcurrentHashMap<MySocket, String> diccionari;
    private MySocket socket;
    public static final int PORT = 444;
    
    public Servidor(MySocket socket, ConcurrentHashMap<MySocket,String> dicc){
        this.socket = socket;
        this.diccionari = dicc;
    }

    public String getClientNick(MySocket socket) throws Exception {
        return diccionari.get(socket);
    }

    public void setClientNick(MySocket socket, String nick) throws Exception {
        diccionari.replace(socket, nick);
    }

    public void sendBroadcast(String message) throws Exception {
        Iterator<MySocket> sockets = diccionari.keySet().iterator();
        while (sockets.hasNext()) {
            MySocket client = sockets.next();
            client.println(message);
        }
    }

    public void run() {
        String line;
        try {
            String initialNick = getClientNick(socket);
            // Notifiquem la unió del client a tothom
            sendBroadcast("/User " + initialNick);
            // notifiquem al nou client dels clients que hi ha.
            Iterator<String> nicks = diccionari.values().iterator();
            while (nicks.hasNext()) {
                String nick = nicks.next();
                if (!initialNick.equals(nick)) { // don't notify ourselves
                    socket.println("/User " + nick);
                }
            }
            // process commands and messages
            while ((line = socket.readLine()) != null) {
                if (line.startsWith("/nick ")) {
                    String nick = line.substring(6);
                    if (!diccionari.contains(nick)) {
                        String previousNick = getClientNick(socket);
                        setClientNick(socket, nick);
                        sendBroadcast("/update " + previousNick + " " + nick);
                    }
                } else {
                    String clientSendingMessage = getClientNick(socket);
                    sendBroadcast(clientSendingMessage + ": " + line);
                }
            }
            String nick = getClientNick(socket);
            System.out.println(nick + " disconnected...");
            diccionari.remove(socket);
            socket.close();
            // notifiquem a tots de la desconnexio del client.
            sendBroadcast("/Bye " + nick);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
