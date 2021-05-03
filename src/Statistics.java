import java.util.ArrayList;
import java.util.HashMap;

public class Statistics {
    
    Graph g;
    
    /*
     * Constructor that initalizes the graph
     */
    
    public Statistics (Graph g) {
        this.g = g;
    }
    
    /*
     * Finds the factorial of "num" 
     */
    
    public double findFactorial(int num) {
        double total = 1;
        
        for (int i = 1; i <= num; i++) {
            total = total * i;
        }
        
        return total;
    }
    
    /*
     * Returns the clustering coefficient of the specified node by finding the number of neighbors
     * who are friends with each other and the total number of ways 2 neighbors can be friends and
     * dividing the two.
     */
    
    public double getClusteringCoefficient(String node) {
        ArrayList<Node> outNeighbors = g.getOutgoingNeighbors(node);
        
        double friendsCount = 0;
        double clusteringCoeff = 0.0;
        int size = outNeighbors.size();
        
        // found by doing 2 times the number of neighbors choose 2
        double totalEdges = 2 * (findFactorial(size))/(findFactorial(size-2) * 2);
        
        for (int i = 0; i < size; i++) {
            Node start = outNeighbors.get(i);
            for (int j = 0; j < size; j++) {
                Node end = outNeighbors.get(j);
                if (start.hasEdge(end.getName())) {
                    friendsCount++;
                }
            }
        }
        
        clusteringCoeff = friendsCount / totalEdges;
        
        return clusteringCoeff;
        
    }

    public HashMap getPageRanks() {
        HashMap<Node, Double> pageRanks = new HashMap<Node, Double>();
        ArrayList<Node> nodes = g.getNodes();
        double size = (double) g.numOfVertices();
        for (Node node : nodes) {
            pageRanks.put(node, 1.0 / size);
        }
        int counter = 0;
        while (counter < 52) {
            for (Node node : nodes) {
                // TODO: implement
            }
            counter++;
        }
        return pageRanks;
    }
    
    /* 
     * Main method used to test Statistics method
     */
    
    public static void main (String [] args) {
        
        // testing clustering coefficient method
       
        Graph g = new Graph();
        
        g.addNode("Emily");
        g.addNode("Matt");
        g.addNode("Sara");
        g.addNode("Hetvi");
        g.addNode("Sleep");
        
        g.addEdge("Sleep", 1, "Emily");
        g.addEdge("Emily", 1, "Sleep");
        g.addEdge("Sleep", 1, "Hetvi");
        g.addEdge("Hetvi", 1, "Sleep");
        g.addEdge("Sleep", 1, "Matt");
        g.addEdge("Matt", 1, "Sleep");
        g.addEdge("Sleep", 1, "Sara");
        g.addEdge("Sara", 1, "Sleep");
        g.addEdge("Emily", 1, "Sara");
        g.addEdge("Sara", 1, "Emily");
        
        Statistics stat = new Statistics(g);
        
        System.out.println(stat.getClusteringCoefficient("Sleep"));
    }
}
