import java.util.ArrayList;
import java.util.HashMap;

public class Statistics {
    
    Graph g;
    
    /**
     * Constructor that initializes the graph
     * @param g
     */
    
    public Statistics (Graph g) {
        this.g = g;
    }

    public double homophily(String c1, String c2) {
        ArrayList<Node> nodes = g.getNodes();
        int color1 = 0;
        int color2 = 0;
        for (int i = 0; i < nodes.size(); i++) {
            if (nodes.get(i).getColor().equals(c1)) {
                color1++;
            } else if (nodes.get(i).getColor().equals(c2)) {
                color2++;
            }
        }

        double p = (double)color1 / (color1 + color2);
        double q = (double)color2 / (color1 + color2);
        return 2 * p * q;
    }
    
    /**
     * Finds the factorial of "num" 
     * @param num
     * @return the factorial
     */
    
    public double findFactorial(int num) {
        double total = 1;
        
        for (int i = 1; i <= num; i++) {
            total = total * i;
        }
        
        return total;
    }
    
    /**
     * Returns the clustering coefficient of the specified node by finding the number of neighbors
     * who are friends with each other and the total number of ways 2 neighbors can be friends and
     * dividing the two.
     * @param node
     * @return the clustering coefficient
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

    /**
     * Returns the page rank of each node in the graph
     * @return a hashmap mapping nodes to their page ranks
     */
    public HashMap<Node, Double> getPageRanks() {
        HashMap<Node, Double> pageRanks = new HashMap<Node, Double>();
        ArrayList<Node> nodes = g.getNodes();
        double size = (double) g.numOfVertices();
        for (Node node : nodes) {
            pageRanks.put(node, 1.0 / size);
        }
        int counter = 0;
        HashMap<Node, Double> temp = new HashMap<Node, Double>();
        while (counter < 52) {
            for (Node node : nodes) {
                temp.put(node, 0.0);
            }
            for (Node node : nodes) {
                for (Node neighbor : g.getOutgoingNeighbors(node)) {
                    // temp.put(neighbor, temp.get(neighbor) + )
                }
            }
            counter++;
        }
        return pageRanks;
    }
    
    /**
     * Main method used to test Statistics method
     * @param args
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
