/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package practica1;

import java.lang.Integer;
import java.util.Observer;
import java.util.Observable;

public class Consola implements Observer {
  private Line line;

  public Consola(Line line){
    this.line = line;
  }

  @Override
  public void update(Observable o, Object arg) {
    int character = ((Integer)arg).intValue();
    switch( character ) {
      case Codis.DEL:
        System.out.print("\033[P");
        break;
      case Codis.BACKSPACE:
        System.out.print("\010\033[P");
        break;
      case Codis.LEFT:
        System.out.print("\033[D");
        break;
      case Codis.RIGHT:
        System.out.print("\033[C");
        break;
      case Codis.INSERT:
        // ToDO: output for insert mode
        break;
      case Codis.HOME:
        System.out.print("\033[G");
        break;
      case Codis.END:
        // can't find a single sequence to go to the end of the line
        // thus doing it like this. THIS IS UGLY!!!
        try {
          System.out.print("\033["+(line.getIndex()+1)+"G");
        } catch(Exception e){
          e.printStackTrace();
        }
        break;
      case Codis.BELL:
        System.out.print('\007');
        break;
      default:
        System.out.print("\033[@"+(char)character);
        break;
    }

  }
}
