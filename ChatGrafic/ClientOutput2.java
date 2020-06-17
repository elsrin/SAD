package ChatGrafic;

import java.util.logging.Level;
import java.util.logging.Logger;

public class ClientOutput2 extends Thread {
    
    //public String nick;
    //public MySocket socket;
    private ChatGraf cg;
    
    public ClientOutput2(ChatGraf cg) {
        this.cg = cg;
    }

    private void addNick(final String nick) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    cg.getListModel().addElement(nick);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void removeNick(final String nick) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    cg.getListModel().removeElement(nick);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void addTextArea(final String line) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    cg.getTextArea().append(line);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
    
    // Output Thread
    @Override
    public void run() {
        String line;
        try {
            while ((line = cg.getSocket().readLine()) != null) {
                if (line.startsWith("/User ")) {
                    addNick(line.substring(6));
                } else {
                    if (line.startsWith("/Bye ")) {
                        removeNick(line.substring(5));
                    } else {
                        if (line.startsWith("/update ")) {
                            String[] nicks = line.substring(8).split("\\s");
                            removeNick(nicks[0]);
                            addNick(nicks[1]);
                        } else {
                            addTextArea(line + "\n");
                        }
                    }
                }
            }
            cg.getSocket().close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
