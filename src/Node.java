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
	/**
	 * gets the color
	 * @return string color name
	 */
	public String getColor() {
		return this.color;
	}
	
	/**
	 * gets the name of the node
	 * @return string node name
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * 
	 * @return gets int degree
	 */
	public int getDegree() {
		return edges.size();
	}
	
	/**
	 * gets the weight of an edge
	 * @param ending node name
	 * @return integer weight of the edge between start and end node
	 */
	public int getWeight(String end) {
	    int weight = 1;
	    
	    for (Object[] e : edges) {
	        if (e[2].toString().equals(end)) {
	            weight = (int) e[1];
	        }
	    }

	    return weight;
	}
	
	/**
	 * gets the weight of an edge
	 * @param ending node
	 * @return integer weight of the edge between start and end node
	 */
	public int getWeight(Node end) {
	    int weight = 1;
	    
	    for (Object[] e : edges) {
	        if (e[2].toString().equals(end.getName())) {
	            weight = (int) e[1];
	        }
	    }

	    return weight;
	}

	/**
	 * gets edges from node
	 * @return edges arraylist of edge objects
	 */
	public ArrayList<Object[]> getEdges() {
	    return edges;
	}
	
	/**
	 * adds edges to graph
	 * @param edgeWeight - int edge weight
	 * @param neighbor - string of neighbor node
	 * @return - boolean of whether edge was added or not
	 */
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
	
	/**
	 * removes an edge given end node name
	 * @param neighbor - string name of neighbor
	 * @return boolean of whether edge was removed
	 */
	public boolean removeEdge(String neighbor) {
		for (Object[] e : edges) {
			if (e[2].toString().equals(neighbor)) {
				edges.remove(e);
				return true;
			}
		}
		return false;
	}
	
	/**
	 * checks whether edge exists
	 * @param neighbor - string name of end node
	 * @return boolean of whether edge exists
	 */
	public boolean hasEdge(String neighbor) {
        for (Object[] e : edges) {
            if (e[2].toString().equals(neighbor)) {
                return true;
            }
        }
        
        return false;
    }
}
