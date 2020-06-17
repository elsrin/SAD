
import java.io.Reader;
import java.io.BufferedReader;
import java.lang.Runtime;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class EditableBufferedReader extends BufferedReader{ 
    Line l;
    Console con;
    Reader red;
    
    public EditableBufferedReader(Reader red) {
        super(red);
        this.red=red;
        this.l = new Line();
        this.con = new Console(this.l);
        l.addObserver(con);
        
    }
    
    public void setRaw() throws InterruptedException, IOException{
        Runtime.getRuntime().exec("stty -icanon min 1 -echo raw < /dev/tty").waitFor();
    }
    
    public void unsetRaw() throws InterruptedException, IOException{
        Runtime.getRuntime().exec("stty -icanon min 1 echo -raw < /dev/tty").waitFor();
    }
    
    public int readChar() throws IOException{
        int c=-1;
        try {
            this.setRaw();
        } catch (InterruptedException ex) {
            Logger.getLogger(EditableBufferedReader.class.getName()).log(Level.SEVERE, null, ex);
        }
        while (true){
            if ( System.in.available() != 0 ) {
                c = System.in.read();
                break;
            }
        }
        try {
            this.unsetRaw();
        } catch (InterruptedException ex) {
            Logger.getLogger(EditableBufferedReader.class.getName()).log(Level.SEVERE, null, ex);
        }
        return c;
    } 
    
    public int read() throws IOException{
        int c=this.readChar();
        if ( c == Codis.ESCAPE ){
            c=this.readChar();
            if ( c == '[' ){
                c=this.readChar();
                switch (c){
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
        return c;
    }

    public String readLine() throws IOException {
        boolean readMore = true;
        while (readMore){
            System.out.print("New Char:");
            int c = this.read();
            switch (c){
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
                case Codis.CR:
                    readMore=false;
                    break;
                default:
                    if (l.getMode())
                        l.reWrite((char)c);
                    else
                        l.addChar((char)c);
                    break;
            }
        }

        return l.getLine();
        
    }

}


