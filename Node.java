import java.util.ArrayList;

public class Node {
	
	private String name;
	
	// Array format: starting edge, edge weight, ending edge
	private ArrayList<Object[]> edges;
	
	public Node(String id) {
		this.name = name;
	}
	public String getName() {
		return name;
	}
	
	public int getDegree() {
		return edges.size();
	}
	
	public ArrayList<Object[]> getEdges() {
		return edges;
	}
	
	public boolean addEdge(int edgeWeight, String neighbor) {
		for (Object[] e : edges) {
			if (e[2].toString().equals(neighbor)) {
				return false;
			}
		}
		
		Object[] edgeDetails = new Object[3];
		edgeDetails[0] = name;
		edgeDetails[1] = edgeWeight;
		edgeDetails[2] = neighbor;
		return true;
	}
	
	public boolean removeEdge(String neighbor) {
		for (Object[] e : edges) {
			if (e[2].toString().equals(neighbor)) {
				edges.remove(e);
				return true;
			}
		}
		return false;
	}
}
