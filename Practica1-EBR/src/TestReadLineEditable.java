/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.InputStreamReader;

/**
 *
 * @author gcabanyes
 */
class TestReadLineEditable {
    public static void main(String[] args) {
        EditableBufferedReader in = new EditableBufferedReader(new InputStreamReader(System.in));
        String str = null;
        try {
          str = in.readLine();
        } catch(Exception e){
          e.printStackTrace();
        }
        System.out.println("\nline is:"  + str);
    }
}

