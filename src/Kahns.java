import java.util.*;

/**
 *
 * @author SaraXin
 *
 */

public class Kahns {

    /**
     * Creates a topological sort of nodes given a graph
     * @param g the given graph
     */
    public static ArrayList<Node> topologicalSort(Graph g) {
        PriorityQueue<Node> queue = new PriorityQueue<Node>();
        ArrayList<Node> output = new ArrayList<Node>();

        ArrayList<Node> nodes = g.getNodes();
        HashMap<Node, Integer> indegree = new HashMap<Node, Integer>();

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
            if (indegree.get(nodes.get(i)) == 0) {
                queue.add(nodes.get(i));
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

}
