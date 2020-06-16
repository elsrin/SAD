/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package practica1;

import java.awt.AWTEvent;
import java.awt.AWTEventMulticaster;
import java.awt.event.KeyListener;
import java.awt.peer.LightweightPeer;
import java.util.ArrayList;
import java.util.Observable;

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
        line = new ArrayList<Character>();
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
    
    public void addChar(char c){
        line.add(index,c);
        index++; 
    }    
    
    public void delChar(){
        if (index!=0){
            line.remove(index-1);
            index--;
            notifyObservers(Codis.BACKSPACE);
        }
    }
    
    public void suprChar(){
        if (index <= line.size()){
            line.remove(index);
            notifyObservers(Codis.DEL);
        }
    }
    
    public void insert(){
        ins = ins != true;
        notifyObservers(Codis.INSERT);
    }
    
    public void reWrite(char c){
        line.set(index, c);
        index++;
    }
    
    public void moveRight(){
        if (index!=getSize()){
            index++;
        }
    }
    
    public void moveLeft(){
        if (index!=1){
            index--;
        }
    } 
    
    public void moveFirst(){
        index=0;
        notifyObservers(Codis.HOME);
    } 
    
    public void moveFinal(){
        index=getSize();
        notifyObservers(Codis.END);
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
