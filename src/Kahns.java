import java.util.*;

/**
 * This class will run Kahn's algorithm on a DAG to find a topological sort of a given graph
 * @author SaraXin
 *
 */

public class Kahns {

    /**
     * Creates a topological sort of nodes given a graph
     * @param g the given graph
     */
    public static ArrayList<Node> topologicalSort(Graph g) {
        LinkedList<Node> queue = new LinkedList<Node>();
        ArrayList<Node> output = new ArrayList<Node>();

        ArrayList<Node> nodes = g.getNodes();
        HashMap<Node, Integer> indegree = new HashMap<Node, Integer>();

        for (int i = 0; i < nodes.size(); i++) {
            indegree.put(nodes.get(i), 0);
        }
        
        for (int i = 0; i < nodes.size(); i++) {
            ArrayList<Node> adj = g.getOutgoingNeighbors(nodes.get(i).getName());
            for (int j = 0; j < adj.size(); j++) {
                if (indegree.containsKey(adj.get(j))) {
                    int val = indegree.get(adj.get(j));
                    indegree.put(adj.get(j), val + 1);
                } else {
                    indegree.put(adj.get(j), 1);
                }
            }
        }

        for (int i = 0; i < nodes.size(); i++) {
            //System.out.println(nodes.get(i).getName());
            if (indegree.get(nodes.get(i)) != null) {
                if (indegree.get(nodes.get(i)) == 0) {
                    queue.add(nodes.get(i));
                }
            }
            
        }

        while (!queue.isEmpty()) {
            Node v = queue.poll();
            output.add(v);
            ArrayList<Node> adj = g.getOutgoingNeighbors(v.getName());

            for (int i = 0; i < adj.size(); i++) {
                int val = indegree.get(adj.get(i));
                indegree.put(adj.get(i), val - 1);
                if (indegree.get(adj.get(i)) == 0) {
                    queue.add(adj.get(i));
                }
            }
        }

        return output;
    }
    
    /**
     * This main method is used to run a test example of a DAG's topo sort
     * @param args
     */
    public static void main(String[] args) {
        Graph g = new Graph();
        g.setDirected(true);
        g.addNode("a");
        g.addNode("b");
        g.addNode("c");
        g.addNode("d");
        g.addNode("e");
        g.addNode("f");
        g.addNode("g");
        g.addNode("h");

//        g.addEdge("a", 1, "b");
//        g.addEdge("b", 1, "c");
        g.addEdge("h", 1, "c");
        g.addEdge("a", 1, "h");
        g.addEdge("d", 1, "e");
        g.addEdge("c", 1, "d");
        g.addEdge("b", 1, "g");
        g.addEdge("d", 1, "b");
        
        //the output should be a, f, h, c, d, e, b, g
        
        ArrayList<Node> output = Kahns.topologicalSort(g);
        for (int i = 0; i < output.size(); i++) {
            System.out.println(output.get(i).getName());
        }
        
    }

}
