
import java.io.BufferedReader;
import java.io.InputStreamReader;

class TestReadLineEditable {
    public static void main(String[] args) {
        BufferedReader in = new EditableBufferedReader(new InputStreamReader(System.in));
        String str = null;
        try {
          str = in.readLine();
        } catch(Exception e){
          e.printStackTrace();
        }
        System.out.println("\nline is:"  + str);
    }
}

