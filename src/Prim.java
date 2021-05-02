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
    public Graph mst() {
        if (graph.numOfVertices() == 0) {
            return graph;
        }
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
            for (Node neighbor : graph.getOutgoingNeighbors(vertex)) {
                // if ()
            }

        //TODO: implement

        if (mst.numOfVertices() != this.graph.numOfVertices()) {
            throw new IllegalArgumentException();
        }
        return mst;
    }

}
