import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.StringTokenizer;


public  class CnfToGraph {
	static ArrayList<ArrayList<Integer>> data = new ArrayList<ArrayList<Integer>>() ;
	
	static ArrayList<Integer> k = new ArrayList<Integer>();
	static ArrayList<Integer> n = new ArrayList<Integer>();

	
	public static void main(String[] args) {
		try {
			CnfToGraph.reduce("../cnfs.txt");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		File file = new File("outfile.txt");
		BufferedReader reader = null;
		
		int graphSize = 0;
		try {
		    reader = new BufferedReader(new FileReader(file));
		    String text = null;
		    
		   // System.out.println("*Max Cliques in graphs in graphsDense.txt");
		  //  System.out.println();
		 //   System.out.println("(|V|,|E|) Cliques (size, ms used)");
			Clique myClique = new Clique();

		    
		    int graphRow = 0;
		    int numGraphs = 1;
		    int edgeCount = 0;
		    boolean skipfirst = true;
		    while ((text = reader.readLine()) != null) {
		    	
			    //detects if it's a new graph
			    if(Clique.isInteger(text) && (Integer.parseInt(text) != 1 || Integer.parseInt(text) != 0)) {


			    	graphRow = 0;
				    ArrayList<Integer> cl = new ArrayList<Integer>();
				    long start = System.currentTimeMillis();
				    myClique.seeGraph(graphSize);
				 //   System.out.println(graphSize);
					cl = myClique.maxClique(cl, 0, graphSize);
					//System.out.println(cl);
					long time = System.currentTimeMillis()-start;
					
					//a bit silly, need it because we make the graphs in kind of a goofy way
					if(skipfirst) {
						skipfirst = false;
					}
				else {
				//	System.out.println(cl);
					System.out.println(CnfToGraph.getCnfVal(cl, time, numGraphs));
						//System.out.println("G" + numGraphs + " (" + graphSize + ", " + ((graphSize / 2) + (edgeCount / 2)) + ") " + cl.toString() + " (" + cl.size() + ", " + msTime + "ms)");
						numGraphs++;
						
					}
					graphSize = Integer.parseInt(text);
					
			    	
		    	
			    	//graph = new int[graphSize][graphSize];
			    	//System.out.println("Graph size:" + graphSize);
					edgeCount = 0;
			    	
			    }
			    else {
			    	String[] arrayRow = text.split(" ");
			    	for(int i = 0; i < arrayRow.length; i++) {
			    		//graph2.add(Integer.parseInt(arrayRow[i]));
			    		Clique.graph[graphRow][i] = Integer.parseInt(arrayRow[i]);
			    		if(Integer.parseInt(arrayRow[i]) == 1) {
			    			edgeCount++;
			    		}
			    		//System.out.print(graph[graphRow][i] + " ");
			    	}
			    	//System.out.println();
			    	graphRow++;
			    }

		    }
		    
		    
		}
		catch (FileNotFoundException e) {
		    e.printStackTrace();
		} catch (IOException e) {
		    e.printStackTrace();
		} finally {
		    try {
			if (reader != null) {
			    reader.close();
			}
		    } catch (IOException e) {
		    }
		}

	}
	public static String getCnfVal(ArrayList<Integer> input, long time, int probNum ){
		String output = "";
		//ArrayList<String> out = new ArrayList<String>();
	    String [] out = new String[k.get(probNum-1)];
		//out.ensureCapacity(k.get(probNum-1));
		//System.out.println(k.get(probNum-1));
		if(input.size() == k.get(probNum-1)){
		
		
		output += "3-CNF No." + (probNum) + ":[n=" + n.get(probNum-1) + " k=" + k.get(probNum-1) + "]\n" + "Assignments:[ ";
		for(int i =0; i<input.size(); i++){
		int temp = data.get(probNum-1).get(input.get(i));
		
		
		if(temp <0 ){
			out[-temp-1]= "F";
		}
		else{
			out[temp-1]= "T";
		}
		}
		for(int i = 0; i<out.length; i++){
			if(out[i]!=null){
			//System.out.println(out[i]);
			output+= "A" + (i+1) + "=" + out[i] + " ";
			}
		}
		output += "](" + (time) + ")\n\n" ;
		}
		else{
			output += "3-CNF No." + (probNum) + ":[n=" + n.get(probNum-1) + " k=" + k.get(probNum-1) + "]" + "No " + k.get(probNum-1) + "-clique; no solution(" + time + ")\n";
		}
		
		
		return output;
		
	}
	public static String reduce(String input) throws IOException{
		Scanner reader = null;
		FileWriter f = null;
		try {
			 f = new FileWriter(new File("outfile.txt"));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			 reader = new Scanner(new FileInputStream(input));
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		int numVar;
		
		numVar = reader.nextInt();
		n.add(numVar);
		while( numVar != 0){
		StringTokenizer tok = new StringTokenizer(reader.nextLine());
		ArrayList<Integer> temp = new ArrayList<Integer>();
		while(tok.hasMoreTokens()){
			temp.add(Integer.parseInt(tok.nextToken()));
		 
		}
		data.add(temp);
	//	System.out.println();
			int size = temp.size(); 
			k.add(size/3);
			int [][] arr = new int [size] [size];
			try {
				f.write(size + "");
				f.write("\n");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//System.out.println(size);
			for(int i=0; i<size; i++){
				for(int j=0; j<size; j++){
		
				if((temp.get(i) != -temp.get(j) && i/3 != j/3) || i==j){
					try {
						f.write("1 ");
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					//System.out.print(1);
					//arr[i][j] = 1;
				}
				else{
					f.write("0 ");
					//arr[i][j]=0;
					//System.out.print(0);
				}
					
				}
				f.write("\n");
				//System.out.println("");
			}
			numVar = reader.nextInt();
			n.add(numVar);
		}
		
		f.close();
		reader.close();
		return input;
		
	}
}
