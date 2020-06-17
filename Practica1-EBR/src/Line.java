
import java.util.Observable;
import java.util.ArrayList;

public class Line extends Observable{
    
    public StringBuffer line;
    public int index;
    public boolean ins;

    public Line() {
        this.ins = false;
        this.index=0;
        line = new StringBuffer();
    }
    
    public int getIndex(){
        return index;        
    }
    
    public boolean getMode(){
        return ins;        
    }
    
    public int getSize(){
        return line.length();
    }
    
    public char getChar(int i){
        return line.charAt(i);
        //return line.get(i);
    }
    
    public void addChar(char c){
        line.insert(index, c);
        index++; 
        setChanged();
        notifyObservers((Integer)((int)c));
    }    
    
    public void delChar(){
        if (index!=0){
            line.deleteCharAt(index-1);
            index--;
        }
        setChanged();
        notifyObservers((Integer) Codis.BACK);
    }
    
    public void suprChar(){
        if (index < line.length()){
            line.deleteCharAt(index);
        }
        setChanged();
        notifyObservers((Integer)Codis.DEL);
    }
    
    public void insert(){
        ins=!ins;
        setChanged();
        notifyObservers((Integer) Codis.INS);
    }
    
    public void reWrite(char c){
        line.setCharAt(index, c);
        setChanged();
        notifyObservers((Integer)((int)c));
    }
    
    public void moveRight(){
        if (index!=getSize()){
            index++;
        }
        setChanged();
        notifyObservers((Integer)Codis.DRETA);
    }
    
    public void moveLeft(){
        if (index!=0){
            index--;
        }
        setChanged();
        notifyObservers((Integer)Codis.ESQUERRE);
    } 
    
    public void moveFirst(){
        index=0;
        setChanged();
        notifyObservers((Integer)Codis.PPI);
     } 
    
    public void moveFinal(){
        index=getSize();
        setChanged();
        notifyObservers((Integer)Codis.FI);
    } 
    
    public void move(int i){
        index=i;
    }
    
    public String getLine(){
        return line.toString();
    }
}