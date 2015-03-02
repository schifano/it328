/*
 * Kevin Castor & Travis Sauder
 * Programming Assignment 1
 * IT328 Dr. Li Spring 2014
 */


import java.awt.List;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Clique {
//stores the graph
static int[][] graph = new int[100][100];
static long msTime = 0;


public static void main(String[] args) {

	File file = new File("../graphsDense.txt");
	BufferedReader reader = null;
	int graphSize = 0;
	try {
	    reader = new BufferedReader(new FileReader(file));
	    String text = null;
	    
	    System.out.println("*Max Cliques in graphs in graphsDense.txt");
	    System.out.println();
	    System.out.println("(|V|,|E|) Cliques (size, ms used)");
		Clique myClique = new Clique();

		//counters for reading in the matrix
	    int graphRow = 0;
	    int numGraphs = 1;
	    int edgeCount = 0;
	    boolean skipfirst = true;
	    while ((text = reader.readLine()) != null) {
	    	
		    //detects if it's a new graph
		    if(isInteger(text)) {

		    	graphRow = 0;
			    ArrayList<Integer> cl = new ArrayList<Integer>();
			    
			    //myClique.seeGraph(graphSize);
				cl = myClique.maxClique(cl, 0, graphSize);
				//a bit silly, need it because we make the graphs in kind of a goofy way
				if(skipfirst) {
					skipfirst = false;
				}
				else {
					System.out.println("G" + numGraphs + " (" + graphSize + ", " + ((graphSize / 2) + (edgeCount / 2)) + ") " + cl.toString() + " (" + cl.size() + ", " + msTime + "ms)");
					numGraphs++;
				}
				graphSize = Integer.parseInt(text);
				
				edgeCount = 0;
		    	
		    }
		    else {
		    	String[] arrayRow = text.split(" ");
		    	for(int i = 0; i < arrayRow.length; i++) {

		    		graph[graphRow][i] = Integer.parseInt(arrayRow[i]);
		    		if(Integer.parseInt(arrayRow[i]) == 1) {
		    			edgeCount++;
		    		}

		    	}

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

public void seeGraph(int graphSize) {
	//print out the previous graph
    System.out.println("Current Graph");
    for(int i = 0; i < graphSize; i++) {
    	
    	for(int j = 0; j < graphSize; j++) {
    		System.out.print(graph[i][j] + " ");
    	}
    	System.out.println();
    }	
}

public static boolean isInteger(String s) {
    return isInteger(s,10);
}

public static boolean isInteger(String s, int radix) {
    if(s.isEmpty()) return false;
    for(int i = 0; i < s.length(); i++) {
        if(i == 0 && s.charAt(i) == '-') {
            if(s.length() == 1) return false;
            else continue;
        }
        if(Character.digit(s.charAt(i),radix) < 0) return false;
    }
    return true;
}


public ArrayList<Integer> maxClique(ArrayList<Integer> cl, int x, int graphSize) {
	//System.out.println("maxClique run");
	long start_time = System.currentTimeMillis();
	
	ArrayList<Integer> temp = new ArrayList<Integer>();
	ArrayList<Integer> largestClique = new ArrayList<Integer>();
	largestClique = cl;
	
for(int i = x; i<graphSize; i++){
	boolean isCL = true;
	for(int j = 0; j < cl.size(); j++) {
		//System.out.println("cl.get(i): " + cl.get(i));
		//System.out.println("x inside for: " + x);
		if(graph[cl.get(j)][i] != 1) {
			isCL = false;
		}
	}

	if(isCL  ) {
		//System.out.println("x: " + x);
		ArrayList<Integer> curCl = new ArrayList<Integer>(cl);
		curCl.add(i);
		temp = maxClique(curCl,i+1,graphSize);
		
		if(temp.size() > largestClique.size()) {
			//System.out.println("assignment temp");
			largestClique = temp;
			
		}
	}
	
}

	long end_time = System.currentTimeMillis();
	
	msTime = end_time-start_time;
	
	return largestClique;

	
}

//separate, non-working attempt at maxClique
public ArrayList<Integer> maxClique2(ArrayList<Integer> cl, int x, int graphSize){
	ArrayList<Integer> temp = new ArrayList<Integer>();
	ArrayList<Integer> maxClique = new ArrayList<Integer>();
	maxClique = cl;
	boolean isCL = true;
	if(x<graphSize-1){
	temp = maxClique2(cl,x+1,graphSize);
	}
	if(temp.size()> maxClique.size()){
		maxClique = temp;
	}
	for(int j = 0; j < cl.size(); j++) {
		//System.out.println("cl.get(i): " + cl.get(i));
		//System.out.println("x inside for: " + x);
		if(graph[cl.get(j)][x] != 1 ||  cl.get(j) ==x) {
			isCL = false;
		}
	}
	if(isCL){
	cl.add(x);
	if(x<graphSize-1){
	temp = maxClique2(cl,x+1,graphSize);
	}
	if(cl.size()>maxClique.size()){
		maxClique = cl;
	}
	if(temp.size()>maxClique.size()){
		maxClique = temp;
	}
	}
	
	
	return maxClique;
	
}

}
