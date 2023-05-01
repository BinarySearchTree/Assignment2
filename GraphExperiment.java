package kys;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.Set;

public class GraphExperiment {
	private static final Random random = new Random();

	public static Map<String, Vertex> generateVertices(int v) { // generates a map of vertices, with keys "Node0"
		// through "Node(v-1)"
		Map<String, Vertex> vertices = new HashMap<>();
		for (int i = 0; i < v; i++) {
			vertices.put("Node" + i, new Vertex("Node" + i));
		}
		return vertices;
	}

	public static Set<Edge> generateEdges(Map<String, Vertex> vertices,
			int e) {/*
					 * generates a set of edges that connects these vertices randomly. The maximum
					 * number of possible edges is given by the formula v*(v-1)
					 */
		Set<Edge> edges = new HashSet<>();
		int maxPossibleEdges = vertices.size() * (vertices.size() - 1);
		int numEdges = Math.min(e, maxPossibleEdges);
		while (edges.size() < numEdges) {
			int sourceIndex = random.nextInt(vertices.size());
			int destinationIndex = random.nextInt(vertices.size());
			if (sourceIndex == destinationIndex) {
				continue;
			}
			Vertex source = getVertex(vertices, sourceIndex);
			Vertex destination = getVertex(vertices, destinationIndex);
			Edge edge = new Edge(source, destination, random.nextInt(10) + 1);
			if (!edges.contains(edge)) {
				edges.add(edge);
			}
		}
		return edges;
	}

	public static Vertex getVertex(Map<String, Vertex> vertices, int index) {
		String key = "Node" + index;
		return vertices.get(key);
	}

	public static void main(String[] args) {
		// Define different values of V
		int[] V_VALUES = { 10, 20, 30, 40, 50 };
		// Define different values of E
		int[] E_VALUES = { 20, 35, 50, 65, 80 };
		String dataSummary = "numVertices,numEdges,vOperations,eOperations,pqOperations\n";// in order of number of
																							// vertices, edges, vertex
																							// operations, edge
																							// operations,

		for (int v : V_VALUES) {
			for (int e : E_VALUES) {

				// Generate random dataset
				Map<String, Vertex> vertices = GraphExperiment.generateVertices(v);
				Set<Edge> edges = GraphExperiment.generateEdges(vertices, e);

				/*
				 * generate random dataset
				 */

				// save dataset to file

				String filename = "Graph v" + vertices.size() + " e" + edges.size() + ".txt";
				dataSummary += vertices.size() + "," + edges.size() + ",";

				File file = new File(filename);
				try {

					FileWriter writer = new FileWriter(file);
					for (Edge edge : edges) {
						writer.write(edge.getSource().getName() + " " + edge.getDestination().getName() + " "
								+ (int) edge.getCost() + "\n");

					}
					writer.close();
				} catch (IOException ex) {
					ex.printStackTrace();
					continue;
				}

				// load data into graph and run Dijkstra's algorithm
				Graph graph = new Graph();
				for (Vertex vertex : vertices.values()) {
					graph.addVertex(vertex);
				}
				for (Edge edge : edges) {
					graph.addEdge(edge.source.getName(), edge.dest.getName(), edge.cost);
				}
				graph.resetDistances();
				graph.dijkstra("Node0");
				dataSummary += graph.getSummary() + "\n";

			}

		}
		System.out.println(dataSummary); // in order of number of vertices, edges, vertex operations, edge operations,
											// number of pq operations
		// save summary of operations to file

		String filename = "Summary.txt";

		File file = new File(filename);
		try {

			FileWriter writer = new FileWriter(file);
			writer.write(dataSummary);

			writer.close();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
}
