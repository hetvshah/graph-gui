import java.util.HashMap;

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
        Node start = graph.getNodeFromIndex(0);
        
        //TODO: implement

        if (mst.numOfVertices() != this.graph.numOfVertices()) {
            throw new IllegalArgumentException();
        }
        return mst;
    }

}
