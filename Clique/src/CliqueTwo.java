
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class CliqueTwo {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		CliqueTwo myClique = new CliqueTwo();
		
		ArrayList<ArrayList<Integer>> cliques = new ArrayList<ArrayList<Integer>>();
		cliques = CliqueTwo.reader();
		
		for(int i = 0; i < cliques.size(); i++) {
			System.out.println("Clique: " + cliques.get(i).toString());
		}
		
		

	}

	//read in the graphs and put them in an arraylist
	private static ArrayList<ArrayList<Integer>> reader() {
		ArrayList<ArrayList<ArrayList<Integer>>> graphs = new ArrayList<ArrayList<ArrayList<Integer>>>();
		
		File file = new File("graphsDense.txt");
		BufferedReader reader = null;
		try {
		    reader = new BufferedReader(new FileReader(file));
		    String line = null;
		    
		    int matrixSize = 0;
		    int numMatrix = 0;
		    while ((line = reader.readLine()) != null) {
		    		
			    	matrixSize = Integer.parseInt(line);
			    	if(matrixSize == 0) {
			    		break;
			    	}
			    	else {
			    		//read in the matrix
			    		graphs.add(readMatrix(matrixSize, reader));
			    		//numMatrix++;
			    	}
			    	//testing
			    	//if(numMatrix > 3) {
			    	//	break;
			    	//}
		    }
		}
		catch (FileNotFoundException e) {
		    e.printStackTrace();
		} 
		catch (IOException e) {
		    e.printStackTrace();
		} 
		finally {
		    try {
				if (reader != null) {
				    reader.close();
				}
		    } catch (IOException e) {	}
		}

		ArrayList<ArrayList<Integer>> cliques = maxCliques(graphs);
		
		return cliques;
		
	}

	private static ArrayList<ArrayList<Integer>> readMatrix(int matrixSize, BufferedReader reader) throws IOException {
		// TODO Auto-generated method stub
		String line = null;
		ArrayList<ArrayList<Integer>> currMatrix = new ArrayList<ArrayList<Integer>>();
		
		for(int i = 0; i < matrixSize; i++) {
			line = reader.readLine();
    		String[] row = line.split(" ");
			
    		ArrayList<Integer> rowArray = new ArrayList<Integer>();
	    	//read the row into the current matrix
	    	for(int j = 0; j < row.length; j++) {
	    		rowArray.add(Integer.parseInt(row[j]));
	    	}
	    		currMatrix.add(rowArray);
	    }

		
		return currMatrix;
	}
	
	public void seeGraph(ArrayList<ArrayList<Integer>> graph) {
		//print out the previous graph
		
		int graphSize = graph.get(0).size();
	    System.out.println("Current Graph");
	    for(int i = 0; i < graphSize; i++) {
	    	
	    	for(int j = 0; j < graphSize; j++) {
	    		System.out.print(graph.get(i).get(j) + " ");
	    	}
	    	System.out.println();
	    }	
	}
	
	public static ArrayList<ArrayList<Integer>> maxCliques(ArrayList<ArrayList<ArrayList<Integer>>> graphs) {
		ArrayList<ArrayList<Integer>> cliques = new ArrayList<ArrayList<Integer>>();
		ArrayList<Integer> cl = new ArrayList<Integer>();
		for(int i = 0; i < graphs.size(); i++) {
			cliques.add(maxClique(cl, 0, graphs.get(i)));
			
		}
		
		return cliques;
	}
	
	//need to edit
	public static ArrayList<Integer> maxClique(ArrayList<Integer> cl, int x, ArrayList<ArrayList<Integer>> graph) {
		System.out.println("maxClique run");
		long start_time = System.currentTimeMillis();
		
		ArrayList<Integer> temp = new ArrayList<Integer>();
		ArrayList<Integer> largestClique = new ArrayList<Integer>();
		largestClique = cl;
		
	for(int i = x; i< graph.size(); i++){
		boolean isCL = true;
		for(int j = 0; j < cl.size(); j++) {
			//System.out.println("cl.get(i): " + cl.get(i));
			//System.out.println("x inside for: " + x);
			if(graph.get(cl.get(j)).get(i) != 1) {
				isCL = false;
			}
		}

		if(isCL  ) {
			//System.out.println("x: " + x);
			ArrayList<Integer> curCl = new ArrayList<Integer>(cl);
			curCl.add(i);
			temp = maxClique(curCl,i+1,graph);
			
			if(temp.size() > largestClique.size()) {
				//System.out.println("assignment temp");
				largestClique = temp;
				
			}
		}
		
	}

		long end_time = System.currentTimeMillis();
		
		//msTime = end_time-start_time;
		
		return largestClique;

		
	}

}
