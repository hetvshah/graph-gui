import java.util.ArrayList;
/**
 *
 * @author MattFriedman
 *
 *
 */

public class Graph {
	private boolean directed = Display.isDirected;
	private boolean weighted = Display.isWeighted;
	private ArrayList<Node> nodes = new ArrayList<Node>();

	public ArrayList<Node> getNodes() {
	    return new ArrayList<Node>(nodes);
	}
	
	public void setWeighted(boolean isWeighted) {
	    this.weighted = isWeighted;
	}
	
	public void setDirected(boolean isDirected) {
        this.directed = isDirected;
    }

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

	/**
	 * helper method to add edges in the implementation of Prim's algorithm
	 * @param node1 the starting {@code Node} object
	 * @param edgeWeight the weight of the edge
	 * @param node2 the ending {@code Node} object
	 * @return true if the specified edge does not already exist in the graph and has been successfully added
	 * and false otherwise
	 */
	public boolean addEdge(Node node1, int edgeWeight, Node node2) {
		boolean added = true;
		if (weighted) {
			if (directed) {
				added = added && node1.addEdge(edgeWeight, node2);
			} else {
				added = added && node1.addEdge(edgeWeight, node2);
				added = added && node2.addEdge(edgeWeight, node1);
			}
		} else {
			if (directed) {
				added = added && node1.addEdge(1, node2);
			} else {
				added = added && node1.addEdge(1, node2);
				added = added && node2.addEdge(1, node1);
			}
		}
		return added;
	}

	public boolean deleteEdge(String n1, String n2) {
		boolean removed = true;
		Node node1 = getNodeFromName(n1);
		Node node2 = getNodeFromName(n2);
		if (directed) {
			removed = removed && node1.removeEdge(n2);
		} else {
			removed = removed && node1.removeEdge(n2);
			removed = removed && node2.removeEdge(n1);
		}
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
	
	public boolean addNode(String n1, String color) {
        for (Node n: nodes) {
            if (n.getName().equals(n1)) {
                return false;
            }
        }
        Node n = new Node(n1, color);
        nodes.add(n);
        return true;
    }

	/**
	 * helper method to add Nodes in the implementation of Prim's algorithm
	 * @param newNode the {@code Node} object to be added to the graph
	 * @return true if the Node object passed in does not already exist in the graph and has been successfully added
	 * and false otherwise
	 */
	public boolean addNode(Node newNode) {
		for (Node n : nodes) {
			if (n.getName().equals(newNode.getName())) {
				return false;
			}
		}
		nodes.add(newNode);
		return true;
	}

	public boolean deleteNode(String n1) {
		for (Node n: nodes) {
			if (n.getName().equals(n1)) {
				deleteIncidentEdges(n1);
				nodes.remove(n);
				return true;
			}
		}

		return false;
	}

	public void deleteIncidentEdges(String n1) {
		ArrayList<Node> neighbors = getOutgoingNeighbors(n1);
		for (Node n : neighbors) {
			n.removeEdge(n1);
		}
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
	
	public ArrayList<Node> getOutgoingNeighbors(Node n1) {
	    ArrayList<Object[]> edges = null;
	    ArrayList<Node> neighbors = new ArrayList<Node>();

	    for (Node n: nodes) {
	        if (n.getName().equals(n1.getName())) {
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

	public String getNameFromIndex(int index) {
	    return nodes.get(index).getName();
	}

	/**
	 * accessor method for nodes
	 * @param index the index of the node to be returned
	 * @return the node in the specified index in the {@code nodes} ArrayList 
	 */
	public Node getNodeFromIndex(int index) {
		return nodes.get(index);
	}
	
	/**
	 * Iterators through the nodes to find the node with the name "name"
	 * @param name
	 * @return the index that this node is at
	 */
	
	public Integer getIndexFromName(String name) {
        for (int i = 0; i < nodes.size(); i++) {
            if (nodes.get(i).getName().equals(name)) {
                return i;
            }
        }
        
        return null;
    }
}
