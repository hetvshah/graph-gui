import java.io.BufferedWriter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;


/**
 * This class allows us to convert a given graph into a CSV file so that we can redraw
 * the graph in our R application
 * @author SaraXin
 *
 */
public class GraphCSVConverter {
    
    private Graph g;

    /**
     * Constructs the converter
     * @param g the given graph that we want to convert to a CSV file
     */
    public GraphCSVConverter(Graph g) {
        
        this.g = g;
        
    }
    
    
    /**
     * Main method used for testing purposes, creates two files, one for nodes, one for edges
     * @param args
     */
    public static void main(String[] args) {
        Graph g = new Graph();
        
        g.setDirected(true);
        g.setWeighted(true);
       
        g.addNode("Emily", "red");
        g.addNode("Sara", "red");
        g.addNode("Hetvi", "blue");
        g.addNode("Matt", "blue");
        
        g.addEdge("Emily", 13, "Sara");
        g.addEdge("Sara", 23, "Hetvi");
        g.addEdge("Hetvi", 14, "Matt");
        g.addEdge("Matt", 2, "Hetvi");
        
        new GraphCSVConverter(g).convert();
        
        
        
    }

    public void convert() {
        
        try {
            File nodeFile = new File("NodesCSV.csv");
            FileWriter nodeWriter = new FileWriter(nodeFile, false);
            BufferedWriter bw = new BufferedWriter(nodeWriter);
            bw.write("Name,Color" );
            bw.newLine();
            ArrayList<Node> nodes = g.getNodes();
            for (Node node : nodes) { 
                if (node.getColor() == null) {
                    bw.write(node.getName() + ",");
                } else {
                    bw.write(node.getName() + "," + node.getColor());
                }
                
                bw.newLine();
            }
            
                    
            bw.flush();
            bw.close();
            
        } catch (FileNotFoundException e) {
            throw new IllegalArgumentException("NO FILE");
        } catch (IOException e) {
        
        }
        
        
        
        try {
            
            File edgeFile = new File("EdgesCSV.csv");
            FileWriter edgeWriter = new FileWriter(edgeFile, false);
            BufferedWriter bw = new BufferedWriter(edgeWriter);
            bw.write("From,To,Weight" );
            bw.newLine();
            ArrayList<Node> nodes = g.getNodes();
            for (Node node : nodes) { 
                
                ArrayList<Object[]> edges = node.getEdges();
                
                for (Object[] edge : edges) {
                    System.out.println(edge[0].toString());
                    System.out.println(edge[2].toString());
                    System.out.println(edge[1].toString());
                    bw.write(edge[0].toString() + "," + edge[2].toString() 
                            + "," + edge[1].toString());
                    bw.newLine();
                }
                
                
            }
            bw.flush();
            bw.close();
        } catch (FileNotFoundException e) {
            throw new IllegalArgumentException("NO FILE");
        } catch (IOException e) {
        
        }
    }
}

