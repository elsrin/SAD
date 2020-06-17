
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.Reader;
import java.io.BufferedReader;
import java.lang.Runtime;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class EditableBufferedReader extends BufferedReader implements KeyListener{
    
    Line l;
    Console c;
    Reader red;
    
    public EditableBufferedReader(Reader red) {
        super(red);
        this.red=red;
        this.l = new Line();
        this.c = new Console(this.l);
    }
    
    public void setRaw() throws InterruptedException, IOException{
        Runtime.getRuntime().exec(
                "stty -icanon min 1 -echo raw < /dev/tty").waitFor();
    }
    
    public void unsetRaw() throws InterruptedException, IOException{
        Runtime.getRuntime().exec(
                "stty -icanon min 1 echo -raw < /dev/tty").waitFor();
    }
    
    public int read() throws IOException{
        try {
            this.setRaw();
        } catch (InterruptedException ex) {
            Logger.getLogger(EditableBufferedReader.class.getName())
                    .log(Level.SEVERE, null, ex);
        }
        int c=-1;
        if ((c = System.in.read()) == Codis.ESCAPE){
            if ((c=System.in.read()) == '[' ){
                switch (c = System.in.read()){
                    case 'C':
                        c=Codis.RIGHT;
                    break;
                    case 'D':
                        c=Codis.LEFT;
                    break;
                    case 'H':
                        c=Codis.HOME;
                    break;
                    case '3':
                        c=Codis.DELETE;
                    break;
                    case '4':
                        c=Codis.END;
                    break;
                }
            }
        }
        try {
            this.unsetRaw();
        } catch (InterruptedException ex) {
            Logger.getLogger(EditableBufferedReader.class.getName()).log(Level.SEVERE, null, ex);
        }
        return c;
    }

    public String readLine() throws IOException {
        l.addObserver(c);
        boolean readMore = true;
        while (readMore){
            int car;
            switch (car = read()){
                case Codis.BACKSPACE:
                    l.delChar();
                    break;
                case Codis.DELETE:
                    l.suprChar();
                    break;
                case Codis.HOME:
                    l.moveFirst();
                    break;
                case Codis.END:
                    l.moveFinal();
                    break;
                case Codis.LEFT:
                    l.moveLeft();
                    break;
                case Codis.RIGHT:
                    l.moveRight();
                    break;
                case Codis.EOI:
                    readMore=false;
                    break;
                case 10:
                    readMore=false;
                    break;
                case 13:
                    readMore=false;
                    break;
                default:
                    if (l.getMode())
                        l.reWrite((char)car);
                    else
                        l.addChar((char)car);
                    break;
            }
        }
        return l.getLine();
    }

    @Override
    public void keyTyped(KeyEvent e) {
        if (e.getSource().equals(e.VK_RIGHT)){
            l.moveRight();
        }
        if (e.getSource().equals(e.VK_LEFT)){
            l.moveLeft();
        }
        if (e.getSource().equals(e.VK_HOME)){
            l.moveFirst();
        }
        if (e.getSource().equals(e.VK_END)){
            l.moveFinal();
        }
        if (e.getSource().equals(e.VK_INSERT)){
            l.insert();
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

}