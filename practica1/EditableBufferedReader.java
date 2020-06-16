package practica1;
import static com.sun.java.accessibility.util.AWTEventMonitor.addKeyListener;
import java.io.BufferedReader;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.Scanner;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;


public class EditableBufferedReader extends BufferedReader implements KeyListener{
    
    public Line l;
    
    public EditableBufferedReader(Reader in) {
        super(in);
        l = new Line();
    }
    
    /*setRaw: passa la consola de mode cooked a mode raw.
    unsetRaw: passa la consola de mode raw a mode cooked.
    read: llegeix el següent caràcter o la següent tecla de cursor.
    readLine: llegeix la línia amb possibilitat d’editar-la.*/
    
    void setRaw() throws Exception{
        Runtime.getRuntime().exec("stty -echo raw").waitFor();
    }
    
    void unsetRaw() throws Exception{
        Runtime.getRuntime().exec("stty echo -raw").waitFor();
    }

    @Override
    public int read() throws IOException {
        int c =  super.read();             
        if( c == Codis.ESCAPE ){
            c=super.read();
                if ( c == '[' ) {
                    switch ( c = super.read() ) {
                            case 'D':
                                c = Codis.HOME;
                                break;
                            case 'C':
                                c = Codis.INSERT;
                                break;
                            case 'H':
                                c = Codis.DELETE;
                                break;
                            case '1':
                                c = Codis.HOME;
                                // We assume following reads will be 126
                                read();
                                break;
                            case '3':
                                c = Codis.DELETE;
                                // We assume following reads will be 126
                                read();
                                break;
                            case '4':
                                c = Codis.END;
                                // We assume following reads will be 126
                                read();
                                break;
                        }
                }
        }
        return c; //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public String readLine(){
        
        try {
            setRaw();
            int c = System.in.read();
            while (c != 10 && c != 13) {
                l.addChar((char) c);
                c = System.in.read();
            }
            l.getLine();
            Consola cons = new Consola(l);
            l.addObserver(cons);
            
            c = read();
            while (c != (char)10 && c != (char)13) {
                
                //c = e.getKeyCode();
                switch (c) {
                    case 2:
                        l.moveFirst();
                        break;
                    case Codis.HOME:
                        l.moveFirst();
                        break;
                    case Codis.END:
                        l.moveFinal();
                        break;
                    case 3:
                        l.moveFinal();
                        break;
                    case 127:
                        l.delChar();
                        break;
                    case 8:
                        l.suprChar();
                        break;
                    case Codis.DELETE:
                        l.suprChar();
                        break;
                    case 26:
                        l.insert();
                        break;
                    case Codis.INSERT:
                        l.insert();
                        break;
                    //Flechas del teclat (direccions)
                    case Codis.RIGHT:
                        l.moveRight();
                        break;
                    case Codis.LEFT:
                        l.moveLeft();
                        break;
                    
                    /*case 94:
                        c=read();
                        if(c==72){
                            l.moveLeft();
                            break;
                        }/* else if (c==73) {
                            l.moveDown();
                            break;
                        } else if (c==74){
                            l.moveUp();
                            break;
                        } if (c==75) {
                            l.moveRight();
                            break;
                        }*/
                    
                    /*case 37://224 75
                        l.moveLeft();
                        break;
                    case 38://224 77
                        l.moveRight();
                        break;
                    /*
                    case 39://224 72
                        l.moveUp();
                        break;
                    case 40://224 80 
                        l.moveDown();
                        break;
                    */ 
                    default:
                        if (l.getMode()!=false)
                            l.reWrite((char)c);
                        else
                            l.addChar((char)c);
                        break;
                }
                c = read();
            }
            unsetRaw();
        } catch (IOException ex) {
            Logger.getLogger(EditableBufferedReader.class.getName()).log(Level.SEVERE, null, ex);
        }
        return l.getLine();
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        
        int keyCode = e.getKeyCode();
        switch (keyCode) {
            case KeyEvent.VK_RIGHT:
                l.moveRight();
                break;
            case KeyEvent.VK_LEFT:
                l.moveLeft();
                break;
            default:
                if (l.getMode() != false) {
                    l.reWrite((char) keyCode);
                } else {
                    l.addChar((char) keyCode);
                }
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }
    
    /*public class ArrowKeys extends JPanel implements KeyListener {
        
        @Override
        public void keyTyped(KeyEvent e) {

        }

        @Override
        public void keyPressed(KeyEvent e) {
            //setFocusable(true);
            int keyCode = e.getKeyCode();
            switch (keyCode) {
                case KeyEvent.VK_RIGHT:
                    l.moveRight();
                    break;
                case KeyEvent.VK_LEFT:
                    l.moveLeft();
                    break;
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {

        }
    }*/
}
