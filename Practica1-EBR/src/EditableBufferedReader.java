
import java.io.Reader;
import java.io.BufferedReader;
import java.lang.Runtime;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class EditableBufferedReader extends BufferedReader{ 
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
        Runtime.getRuntime().exec("stty -echo raw").waitFor();
    }
    
    public void unsetRaw() throws InterruptedException, IOException{
        Runtime.getRuntime().exec("stty echo -raw").waitFor();
    }
    
    public int read() throws IOException{
        int c=super.read();
        if ( c == Codis.ESCAPE ){
            c=super.read();
            if ( c == '[' ){
                c=super.read();
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
        
        try {
            this.setRaw();
        } catch (InterruptedException ex) {
            Logger.getLogger(EditableBufferedReader.class.getName()).log(Level.SEVERE, null, ex);
        }
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
                default:
                    if (l.getMode())
                        l.reWrite((char)c);
                    else
                        l.addChar((char)c);
                    break;
            }
        }
        try {
            this.unsetRaw();
        } catch (InterruptedException ex) {
            Logger.getLogger(EditableBufferedReader.class.getName()).log(Level.SEVERE, null, ex);
        }
        return l.getLine();
        
    }

}


