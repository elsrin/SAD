

import java.util.Observable;
import java.util.ArrayList;

/**
 *
 * @author lsadusr26
 */
public class Line extends Observable{
    public ArrayList<Character> line;
    public int index;
    public boolean ins;

    public Line() {
        this.ins = false;
        this.index=0;
        line = new ArrayList();
    }
    
    public int getIndex(){
        return index;        
    }
    
    public boolean getMode(){
        return ins;        
    }
    
    public int getSize(){
        return line.size();
    }
    
    public char getChar(int i){
        return line.get(i);
    }
    
    public void addChar(char c){
        line.add(index, c);
        index++; 
        setChanged();
        notifyObservers((Integer)((int)c));
    }    
    
    public void delChar(){
        if (index!=0){
            line.remove(index);
            index--;
            
        }
        setChanged();
        notifyObservers((Integer) Codis.BACKSPACE);
    }
    
    public void suprChar(){
        if (index < line.size()){
            line.remove(index+1);
        }
        setChanged();
    }
    
    public void insert(){
        ins=!ins;
        setChanged();
        notifyObservers((Integer) Codis.INSERT);
    }
    
    public void reWrite(char c){
        line.set(index, c);
        setChanged();
        notifyObservers((Integer)((int)c));
    }
    
    public void moveRight(){
        if (index!=getSize()){
            index++;
        }
        setChanged();
        notifyObservers((Integer)Codis.RIGHT);
    }
    
    public void moveLeft(){
        if (index!=0){
            index--;
        }
        setChanged();
    } 
    
    public void moveFirst(){
        index=0;
        setChanged();
     } 
    
    public void moveFinal(){
        index=getSize();
        setChanged();
    } 
    
    /*public void moveUp(){}
    public void moveDown(){}*/
    
    public void move(int i){
        index=i;
    }
    
    public String getLine(){
        return line.toString();
    }
}