import java.util.ArrayList;

public class Graph {
	private boolean directed;
	private boolean weighted;
	private ArrayList<Node> nodes = new ArrayList<Node>();
	
	public boolean addEdge(String n1, int edgeWeight, String n2) {
		boolean added = true;
		Node node1 = getNodeFromName(n1);
		Node node2 = getNodeFromName(n2);
		
		if (weighted) {
			if (directed) {
				added = added && node1.addEdge(edgeWeight, n2);
			} else {
				added = added && node1.addEdge(edgeWeight, n2);
				added = added && node2.addEdge(edgeWeight, n1);
			}
		} else {
			if (directed) {
				added = added && node1.addEdge(1, n2);
			} else {
				added = added && node1.addEdge(1, n2);
				added = added && node2.addEdge(1, n1);
			}
		}
		return added;
	}
	
	public boolean deleteEdge(String n1, String n2) {
		boolean removed = true;
		Node node1 = getNodeFromName(n1);
		Node node2 = getNodeFromName(n2);
		removed = removed && node1.removeEdge(n2);
		removed = removed && node2.removeEdge(n1);
		return removed;
	}
	
	
	public boolean addNode(String n1) {
		for (Node n: nodes) {
			if (n.getName().equals(n1)) {
				return false;
			}
		}
		Node n = new Node(n1);
		nodes.add(n);
		return true;
	}
	
	public boolean deleteNode(String n1) {
		for (Node n: nodes) {
			if (n.getName().equals(n1)) {
				nodes.remove(n);
				return true;
			}
		}
		return false;
	}
	
	public int getOutDegree(String n1) {
		for (Node n: nodes) {
			if (n.getName().equals(n1)) {
				return n.getDegree();
			}
		}
		return 0;
	}
	
	public int numOfVertices() {
		return nodes.size();
	}
	
	public ArrayList<Node> getOutgoingNeighbors(String n1) {
		ArrayList<Object[]> edges = null;
		ArrayList<Node> neighbors = new ArrayList<Node>();
		
		for (Node n: nodes) {
			if (n.getName().equals(n1)) {
				edges = n.getEdges();
			}
		}
		
		for (Object[] e : edges) {
			neighbors.add(getNodeFromName(e[2].toString()));
		}
		return neighbors;
	}
	
	public Node getNodeFromName(String n1) {
		for (Node n: nodes) {
			if (n.getName().equals(n1)) {
				return n;
			}
		}
		return null;
	}
	
}
