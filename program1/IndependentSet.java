/**
 * Course: IT 328 Theory of Computation
 * Description: Independent Set class that finds a max independent set in every graph.
 * This code cannot be copied or reproduced without permission from the authors.
 * @author Rachel Schifano and Ben Gulans
 */
import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

public class IndependentSet {
	// Initialize 2D array to store graph
	public static int[][] graph;
	public static int dimension, numGraphs, numEdges, row;
	public static long ms;

	/**
	 * Default Constructor
	 */
	public IndependentSet() {
		graph = new int[60][60];
		dimension = 0;
		numGraphs = 0;
		numEdges = 0;
		row = 0;
		ms = 0;
	}

	/**
	 * Method that finds the max clique within a graph.
	 * @param aClique An arraylist of cliques
	 * @param aDimension The current graph dimension
	 * @return maxClique An arraylist with the max clique
	 */
	public ArrayList<Integer> findMaxIndependentSet(ArrayList<Integer> aClique, int row, int aDimension) {
		long startTime = System.currentTimeMillis();

		ArrayList<Integer> temp = new ArrayList<Integer>();
		ArrayList<Integer> maxClique = new ArrayList<Integer>();
		maxClique = aClique;

		for (int i = row; i < dimension; i++) {
			boolean isAClique = true;
			for (int j = 0; j < aClique.size(); j++) {
				if (graph[aClique.get(j)][i] != 1) {
					isAClique = false;
				}
			}

			if (isAClique) {
				ArrayList<Integer> currentClique = new ArrayList<Integer>(aClique);
				currentClique.add(i);
				temp = findMaxIndependentSet(currentClique, i+1, dimension);

				if (temp.size() > maxClique.size()) {
					maxClique = temp;
				}
			}
		} // end outer
		long endTime = System.currentTimeMillis();
		ms = endTime - startTime;
		return maxClique;
	}

	/**
	 * Method to count the number of edges in the graph.
	 * @return numEdges The number of edges in the graph.
	 */
	private static int countEdges() {
		numEdges = 0;
		for (int i = 0; i < dimension; i++) {
			for (int j = i+1; j < dimension; j++) {
				if (graph[i][j] == 1) {
					numEdges++;
				}
			}
		}
		return numEdges / 2 + (dimension / 2);
	}

	/**
	 * Helper method that retrieves the next vertex in a graph and converts
	 * the char into an int.
	 * @param br The current buffer reader
	 * @return x The vertex as an int
	 */
	private static int getNext(BufferedReader br) throws IOException {
		char c = (char) br.read();
		int x = Character.getNumericValue(c);
		return x;
	}

	/**
	 * Method that prints out the max clique solution.
	 */
	private static void printGraphSolution(ArrayList<Integer> aClique) {
		int size = aClique.size();

		System.out.println("G" + numGraphs + " (" + dimension + ", " + numEdges + ") " +
			aClique.toString() + "(size=" + aClique.size() + ", " + ms + " ms)");
	}

	/**
	 * Method that prints out the contents of the 2D array and graph.
	 */
	private static void printGraph() {
		System.out.println("dimension = " + dimension);
		for (int i = 0; i < dimension; i++) {
			for (int j = 0; j < dimension; j++) {
				System.out.print(graph[i][j] + " ");
			}
			System.out.println();
		}
		System.out.println();
	}

	// Main
	public static void main(String[] args) {
		IndependentSet indSet = new IndependentSet();

		System.out.println("* Max Independent Set in graphs in graphs.txt\n");
		System.out.println("(|V|,|E|) Independent Sets (size, ms used)");

		// Read in the graph
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader("graphs.txt"));
			String line = null;
			while((line = br.readLine()) != null) {
				dimension = Integer.parseInt(line);

				if (dimension != 0) {
					numGraphs++;

					// Load values from file into 2D array
					for(int i = 0; i < dimension; i++) {
						for(int j = 0; j < dimension; j++) {
							graph[i][j] = getNext(br);

							countEdges(); // Count number of edges
							// Keep diagonal vertices
							if (i != j) {
								if (graph[i][j] == 1) {
									graph[i][j] = 0;
								} else {
									graph[i][j] = 1;
								}
							} 

							br.read(); // skip space
						} //end inner for
						br.read();
						br.read();
					} // end outer for

					// Evaluate Max Independent Sets
					ArrayList<Integer> cliqueList = new ArrayList<Integer>();
					cliqueList = indSet.findMaxIndependentSet(cliqueList, row, dimension);
					printGraphSolution(cliqueList);
					// printGraph(); // TEST PRINT
				}
			} // endwhile
			br.close();
		} // endtry
		catch (FileNotFoundException e) {
			System.out.println("Could not find file.");
		} 
		catch (IOException e) {
			System.out.println("Could not read file.");
		}
	} // endmain
}