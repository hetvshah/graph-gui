import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author HetviShah
 *
 */

public class Kosaraju {

    Graph inputGraph;

    public Kosaraju(Graph g) {
        inputGraph = g;
    }

    // recursively visits all nodes within the connected component of "start"
    public void DFSVisit(Graph g, String start, List<String> discovered, List<String> order) {
        ArrayList<Node> outNeighbors = g.getOutgoingNeighbors(start);

        discovered.add(start);
        for (Node neighbor : outNeighbors) {
            if(!discovered.contains(neighbor.getName())) {
                DFSVisit(g, neighbor.getName(), discovered, order);
            }
        }

        order.add(start);
    }

    // iterates through all the vertices and calls DFSVisit on those who haven't been discovered yet
    public List<String> DFS() {
        List<String> discovered = new ArrayList<>();
        List<String> order = new ArrayList<String>();

        for (int i = 0; i < inputGraph.numOfVertices(); i++) {
            if(!discovered.contains(inputGraph.getNameFromIndex(i))) {
                DFSVisit(inputGraph, inputGraph.getNameFromIndex(i), discovered, order);
            }
        }

        return order;

    }

    // computes the transpose of the input graph
    public Graph getTranspose() {
        Graph gTranspose = new Graph();

        for (int i = 0; i < inputGraph.numOfVertices(); i++) {
            List<Node> outNeighbors = inputGraph.getOutgoingNeighbors(inputGraph.getNameFromIndex(i));

            for (Node neighbor : outNeighbors) {
                Node node = inputGraph.getNodeFromName(inputGraph.getNameFromIndex(i));

                gTranspose.addNode(neighbor.getName());
                gTranspose.addNode(inputGraph.getNameFromIndex(i));
                gTranspose.addEdge(neighbor.getName(),
                        node.getWeight(neighbor.getName()),
                        inputGraph.getNameFromIndex(i));
            }
        }

        return gTranspose;
    }

    // Kosaraju's algorithm: returns a list of each SCC
    public List<List<String>> kosarajuAlg(){
        List<List<String>> result = new ArrayList<>();

        List<String> orderedList = DFS();
        Collections.reverse(orderedList);

        Graph gTranspose = getTranspose();

        List<String> discovered = new ArrayList<String>();

        for (int i = 0; i < orderedList.size(); i++) {
            List<String> order = new ArrayList<String>();

            if(!discovered.contains(orderedList.get(i))) {
                DFSVisit(gTranspose, orderedList.get(i), discovered, order);
                result.add(order);
            }
        }

        return result;
    }

    // for testing purposes
    public static void main (String[] args) {
        Graph g = new Graph();
       
//        g.addNode("Emily");
//        g.addNode("Sara");
//        g.addNode("Hetvi");
//        g.addNode("Matt");
//        
//        g.addEdge("Emily", 1, "Sara");
//        g.addEdge("Sara", 1, "Hetvi");
//        g.addEdge("Hetvi", 1, "Matt");
//        g.addEdge("Matt", 1, "Hetvi");

        g.addNode("a");
        g.addNode("b");
        g.addNode("c");
        g.addNode("d");
        g.addNode("e");
        g.addNode("f");
        g.addNode("g");
        g.addNode("h");

        g.addEdge("a", 1, "b");
        g.addEdge("b", 1, "e");
        g.addEdge("b", 1, "f");
        g.addEdge("b", 1, "c");
        g.addEdge("c", 1, "g");
        g.addEdge("c", 1, "d");
        g.addEdge("d", 1, "c");
        g.addEdge("d", 1, "h");
        g.addEdge("h", 1, "h");
        g.addEdge("g", 1, "h");
        g.addEdge("g", 1, "f");
        g.addEdge("f", 1, "g");
        g.addEdge("e", 1, "a");
        g.addEdge("e", 1, "f");

        Kosaraju kosaraju = new Kosaraju(g);
        List<List<String>> result = kosaraju.kosarajuAlg();
        System.out.println(result);
    }
}
