package ChatGrafic;

import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.Thread;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import com.sun.glass.events.KeyEvent;

public class ChatGraf extends JFrame implements ActionListener{

    public static final int PORT = 4444;
    private JTextField inputField;
    public JList<String> list;
    public DefaultListModel<String> listModel;
    public JTextArea textArea;
    private MySocket sc;
    private JButton button;

    public DefaultListModel<String> getListModel() {
        return listModel;
    }

    public MySocket getSocket() {
        return sc;
    }

    public JTextArea getTextArea() {
        return textArea;
    }

    public ChatGraf(MySocket socket) {
        sc = socket;

        // Schedule creation and show of GUI
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    GUI();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        // Output thread
        new ClientOutput2(this).start();
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        // Gets the text from inputField (clearing it afterwards), adds the 
        // message to the text area and writes it to the socket to send it
        // to the server
        String message = inputField.getText();
        
        if (event.getSource().equals(button)) {
            setInputField("");
            try {
                sc.println(message);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            setInputField("");
            try {
                sc.println(message);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        inputField.addKeyListener(new KeyListener() {
            //se ejecuta cuando se presiona una tecla
            @Override
            public void keyTyped(java.awt.event.KeyEvent e) {
            }

            //se ejecuta cuando se suelta una tecla
            @Override
            public void keyPressed(java.awt.event.KeyEvent e) {
                if (e.getSource() == inputField) {
                    if (e.VK_ESCAPE == e.getKeyCode()) {
                        int respuesta = JOptionPane.showConfirmDialog(rootPane,
                                "Esta seguro que desea salir?", "Confirmación",
                                JOptionPane.YES_NO_OPTION);
                        if (respuesta == JOptionPane.YES_NO_OPTION) {
                            System.exit(0);
                        }
                    }
                }
            }

            /* funcionará solo cuando se presionan caracteres, si se
             * presionan teclas como F1, F2, inicio etc no ejecutará ningun evento */
            @Override
            public void keyReleased(java.awt.event.KeyEvent e) {
            }

        });
    }
    
  

    private void GUI() throws Exception {
        //Set the look and feel.
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        //Make sure we have nice window decorations.
        JFrame.setDefaultLookAndFeelDecorated(true);
        // Create and set up the window.
        JFrame frame = new JFrame("Chat SAD");
        frame.setLayout(new BorderLayout(5, 5));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // Create an output JPanel and add a JTextArea(20, 30) inside a JScrollPane
        JPanel out = new JPanel();
        out.setLayout(new BoxLayout(out, BoxLayout.LINE_AXIS));
        out.setBorder(BorderFactory.createEmptyBorder(5, 5, 0, 5));
        //chat area
        textArea = new JTextArea(20, 30);
        textArea.setEditable(false);
        textArea.setEnabled(false);
        out.add(new JScrollPane(textArea));
        //list of the users
        listModel = new DefaultListModel<>();
        list = new JList<>(listModel);
        list.setEnabled(false);
        JScrollPane listScrollPane = new JScrollPane(list);
        out.add(listScrollPane, BorderLayout.CENTER);
        //user writing space
        // Create an input JPanel and add a JTextField(25) and a JButton
        JPanel inp = new JPanel();
        inp.setLayout(new BoxLayout(inp, BoxLayout.LINE_AXIS));
        inputField = new JTextField();
        button = new JButton("Enviar");
        button.addActionListener(this); //button is listening
        inp.add(inputField);
        inp.add(button);
        // Listen to events from the inputField button.
        inputField.addActionListener(this);
        // add panels to main frame
        frame.add(out, BorderLayout.CENTER);
        frame.add(inp, BorderLayout.PAGE_END);
        //Display the window centered.
        frame.setSize(500, 400);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private void setInputField(final String message) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    inputField.setText(message);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public static void main(String[] args) throws Exception {
        new ChatGraf(new MySocket("localhost", 4444));
    }
}
