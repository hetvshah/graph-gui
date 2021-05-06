import java.util.HashMap;
import java.util.ArrayList;

public class Prim {
    
    Graph graph;
    HashMap<Node, Integer> key;
    HashMap<Node, Node> parent;

    /**
     * constructor
     * @param g the input Graph, which should be connected 
     * (an IllegalArgumentException will be thrown by {@code mst()} otherwise)
     */
    public Prim(Graph g) {
        this.graph = g;
    }
    
    /**
     * 
     * @return the Minimum Spanning Tree produced by running Prim's algorithm
     */
    public HashMap<Node, Node> mst() {
        if (graph.numOfVertices() == 0) {
            return new HashMap<Node, Node>();
        }
        key = new HashMap<Node, Integer>();
        parent = new HashMap<Node, Node>();
        Graph mst = new Graph();
        ArrayList<Node> nodes = graph.getNodes();
        for (Node node : nodes) {
            key.put(node, Integer.MAX_VALUE);
            parent.put(node, null);
        }
        Node start = graph.getNodeFromIndex(0);
        key.put(start, 0);
        while (nodes.size() > 0) {
            // brute force search for node with lowest key since {@code Node} objects aren't comparable
            // otherwise a priority queue would be implemented instead, for a lower run time
            Node vertex = nodes.get(0);
            for (Node node : nodes) {
                if (key.get(node) < key.get(vertex)) {
                    vertex = node;
                }
            }
            nodes.remove(vertex);
            // parent map construction
            for (Node neighbor : graph.getOutgoingNeighbors(vertex)) {
                int weight = vertex.getWeight(neighbor);
                if (weight < key.get(neighbor)) {
                    key.put(neighbor, weight);
                    parent.put(neighbor, vertex);
                }
            }
        }
        return parent;
        // for (Node node : parent.keySet()) {
        //     mst.addNode(node);
        //     Node parentNode = parent.get(node);
        //     if (parentNode != null) {
        //         mst.addNode(parentNode);
        //         mst.addEdge(parentNode, parentNode.getWeight(node), node);
        //     }
        // }
        // if (mst.numOfVertices() != this.graph.numOfVertices()) {
        //     throw new IllegalArgumentException();
        // }
        // return mst;
    }

    public static void main(String[] args) {
        Graph graph = new Graph();
        graph.setDirected(true);
        graph.addNode("A");
        graph.addNode("B");
        graph.addNode("C");
        graph.addNode("D");
        graph.addNode("E");
        graph.addEdge("A", 1, "B");
        graph.addEdge("B", 2, "C");
        graph.addEdge("C", 3, "D");
        graph.addEdge("D", 4, "E");
        graph.addEdge("E", 50, "A");
        graph.addEdge("A", 60, "D");
        graph.addEdge("D", 70, "B");
        graph.addEdge("B", 80, "E");
        graph.addEdge("E", 90, "C");
        Prim prim = new Prim(graph);
        Graph mst = prim.mst();
        for (Node node : mst.getNodes()) {
            System.out.println(node.getName() + ":");
            for (Object[] array : node.getEdges()) {
                for (Object entry : array) {
                    System.out.print(entry);
                    System.out.print(" ");
                }
                System.out.println();
            }
            System.out.println();
        }
    }
}
        
