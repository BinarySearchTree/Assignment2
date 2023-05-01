package kys;
// Represents an edge in the graph.
class Edge {
	public Vertex source;
	public Vertex dest; // Second vertex in Edge
	public double cost; // Edge cost

	public Edge(Vertex s, Vertex d, double c) {
		source = s;
		dest = d;
		cost = c;
	}

	public Vertex getSource() {
		return source;
	}

	public Vertex getDestination() {
		return dest;
	}

	public double getCost() {
		return cost;
	}

	@Override
	public String toString() {
		return source.toString() + " " + dest.toString() + ", cost=" + cost + " ";
	}

}
