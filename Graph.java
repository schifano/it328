import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
 
/**
 *
 */
 
/**
 * @author Ben
 *
 */

public class Graph {


static int [][]adj;
        static int n;
        static int maxCliqueSize;
        static int []clique;
        static String file = "graphsDense.txt";
 
        /**
         * @param args
         */
        public static void main(String[] args) {
                readGraph();
               
        }
       
        static void readGraph() {
                char curr;
                String num = null;
                try {
                        FileReader fr = new FileReader(file);
                        BufferedReader br = new BufferedReader(fr);
                        while((num = br.readLine()) != null){
                                n = Integer.parseInt(num);
                                if(n == 0) break;
                                adj = new int[n][n];
                                //load values from file into adjacency list
                                for(int i = 0; i < n; i++) {
                                        for(int j = 0; j < n; j++) {
                                                adj[i][j] = getNext(br);
                                                br.read();
                                        }
                                        br.read();
                                        br.read();
                                }
                                //printing for graph visual
                                /*
                                System.out.println("n = " + n);
                                for(int i = 0; i < n; i++) {
                                        for(int j = 0; j < n; j++) {
                                                System.out.print(adj[i][j] + " ");
                                        }
                                        System.out.println();
                                }
                                System.out.println();
                                */
                        }
                        br.close();
                }
                catch(FileNotFoundException e) {
                        System.out.println("Unable to open file graphsDense.txt");
                }
                catch(IOException ex) {
                        System.out.println("Error reading file");
                }
        }
       
        static int getNext(BufferedReader br) throws IOException {
                char c = (char) br.read();
                int x = Character.getNumericValue(c);
                return x;
        }
    public static void main(String[] args) {
        System.out.println("lol");
    }

}
