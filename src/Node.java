import java.util.ArrayList;

public class Node {
	
	private String name;
	private String color;
	
	// Array format: starting edge, edge weight, ending edge
	private ArrayList<Object[]> edges = new ArrayList<>();
	
	public Node(String id) {
		this.name = id;
	}

	public Node(String id, String color) {
		this.name = id;
		this.color = color;
	}

	public String getColor() {
		return this.color;
	}

	public String getName() {
		return name;
	}
	
	public int getDegree() {
		return edges.size();
	}
	
	public int getWeight(String end) {
	    int weight = 1;
	    
	    for (Object[] e : edges) {
	        if (e[2].toString().equals(end)) {
	            weight = (int) e[1];
	        }
	    }

	    return weight;
	}
	
	public int getWeight(Node end) {
	    int weight = 1;
	    
	    for (Object[] e : edges) {
	        if (e[2].toString().equals(end.getName())) {
	            weight = (int) e[1];
	        }
	    }

	    return weight;
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

	    edges.add(edgeDetails);

	    return true;
	}

	/**
	 * 
	 * @param edgeWeight the weight of the edge to be added
	 * @param neighbor the ending {@code Node} object of the edge to be added
	 * @return true if there did not already exist an edge to the specified neighbor and the edge was successfully added
	 * and false otherwise
	 */
	public boolean addEdge(int edgeWeight, Node neighbor) {
		for (Object[] e : edges) {
	        if (e[2].equals(neighbor)) {
	            return false;
	        }
	    }
		Object[] edgeDetails = new Object[3];
	    edgeDetails[0] = name;
	    edgeDetails[1] = edgeWeight;
	    edgeDetails[2] = neighbor.getName();
		edges.add(edgeDetails);
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
	
	public boolean hasEdge(String neighbor) {
        for (Object[] e : edges) {
            if (e[2].toString().equals(neighbor)) {
                return true;
            }
        }
        
        return false;
    }
}
