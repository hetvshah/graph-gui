import java.util.ArrayList;
import java.util.List;

public class BFS {
    
    private boolean disconnected = false;
    Graph g;
    ArrayList<String> shortestPath = new ArrayList<>();
    
    /**
     * Constructor to initialize the input graph
     * @param input
     */
    
    public BFS (Graph input) {
        g = input;
    }

    /**
     * Recursive function that traces up the parent pointers from the specified ending vertex
     * to the specified starting vertex. Updates shortestPath with this path.
     * @param parentArr
     * @param index
     */
    
    public void getPath (String[] parentArr, Integer index) {
        if (index == null) {
            return;
        }

        String parent = parentArr[index];
        getPath(parentArr, g.getIndexFromName(parent));

        shortestPath.add(g.getNameFromIndex(index));

    }

    /**
     *
     * Implements the BFS algorithm and uses an array to keep track of the parents of each node 
     * 
     * @param start
     * @param end
     * @return the shortest path between two specified nodes
     */

    public ArrayList<String> bfsAlgorithm(String start, String end) {
        
        boolean stop = false;
        disconnected = false;


        List<String> discovered = new ArrayList<>();
        List<String> queue = new ArrayList<>();
        
        String[] parent = new String[g.numOfVertices()];

        discovered.add(start);
        queue.add(start);
        queue.add(null);

        String node = start;

        while (queue.size() != 0 && !stop) {

            node = queue.get(0);
            queue.remove(0);
            
            List<Node> outNeighbors = g.getOutgoingNeighbors(node);
            
            for (Node neighbor : outNeighbors) {
                if (!discovered.contains(neighbor.getName())) {
                    discovered.add(neighbor.getName());
                    queue.add(neighbor.getName());
                    parent[g.getIndexFromName(neighbor.getName())] = node;
                    
                }
            }

            if (!disconnected && queue.get(0) == null) {
                queue.remove(0);
                if (!queue.isEmpty()) {
                    queue.add(null);
                }
            }
        }
        
        
        getPath(parent, g.getIndexFromName(end));
        
        if (!shortestPath.get(0).equals(start)) {
            System.out.println("there exists no shortest path");
            return new ArrayList<String>();
        }
        
        return shortestPath;
    }
    
    /**
     * Main method to test the program
     * @param args
     */
    
    public static void main (String[] args) {
        Graph g = new Graph();
        
        g.addNode("1");
        g.addNode("2");
        g.addNode("3");
        g.addNode("4");
        g.addNode("5");
        g.addNode("6");
        
        g.addEdge("1", 1, "2");
        g.addEdge("2", 1, "1");
        g.addEdge("1", 1, "6");
        g.addEdge("6", 1, "1");
        g.addEdge("2", 1, "6");
        g.addEdge("6", 1, "2");
        g.addEdge("3", 1, "2");
        g.addEdge("2", 1, "3");
        g.addEdge("5", 1, "6");
        g.addEdge("6", 1, "5");
        g.addEdge("3", 1, "6");
        g.addEdge("6", 1, "3");
        g.addEdge("3", 1, "5");
        g.addEdge("5", 1, "3");
        g.addEdge("3", 1, "4");
        g.addEdge("4", 1, "3");
        g.addEdge("5", 1, "4");
        g.addEdge("4", 1, "5");
        
        BFS bfs = new BFS(g);
        System.out.println(bfs.bfsAlgorithm("1", "5"));
    }
}
