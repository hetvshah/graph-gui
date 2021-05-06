import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField; 
public class Display {
    
    static boolean isWeighted = false;
    static boolean isDirected = false;
    static boolean isColored = false;
    
    static Graph graph = new Graph();
    
    public static void main(String[] args) {   
        JFrame frame = new JFrame("Graph GUI :)");
        frame.setSize(400, 550);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        JPanel panel = new JPanel();    
        frame.add(panel);
        placeComponents(panel);

        frame.setVisible(true);
    }

    private static void placeComponents(JPanel panel) {
        panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
        
        JLabel createGraph = new JLabel("Create Graph");
        createGraph.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        createGraph.setForeground(Color.RED);
        panel.add(createGraph);
        
        JButton addNode = new JButton("Add Node");
        panel.add(addNode);
        
        JPanel popUpPanel = new JPanel();
        
        addNodeDialogNotColored(addNode, popUpPanel);
        
        JButton addEdge = new JButton("Add Edge");
        panel.add(addEdge);
        
        JCheckBox weighted = new JCheckBox("Weighted");
        panel.add(weighted);
        
        addEdgeDialogUnweighted(addEdge, popUpPanel);
        
        JCheckBox directed = new JCheckBox("Directed");
        panel.add(directed);

        JCheckBox colored = new JCheckBox("Colored");
        panel.add(colored);
        
        // ALGORITHMS
        
        JLabel algorithms = new JLabel("Algorithms");
        algorithms.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        algorithms.setForeground(Color.RED);
        panel.add(algorithms);
        
        JButton GSCCs = new JButton("Find GSCCs (Kosaraju's alg)");
        panel.add(GSCCs);
        
        kosarajuButton(GSCCs);
        
        JButton MSTs = new JButton("Find the MST (Prim's alg)");
        panel.add(MSTs);
        
        primButton(MSTs);
        
        JButton kahn = new JButton("Topological Sort (Kahn's alg)");
        panel.add(kahn);
        
        kahnButton(kahn);
        
        JButton shortestPath = new JButton("Find Shortest Path (BFS)");
        panel.add(shortestPath);
        
        shortestPathButton(shortestPath);
        
        // STATISTICS
        
        JLabel statistics = new JLabel("Statistics");
        statistics.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        statistics.setForeground(Color.RED);
        panel.add(statistics);
        
        JButton CC = new JButton("Find clustering coefficient for a node");
        panel.add(CC);
        
        ccButton(CC);
        
        JButton homophily = new JButton("Find homophily");
        panel.add(homophily);
        
        homophilyButton(homophily);

        JButton pageRank = new JButton("Run PageRank");
        panel.add(pageRank);
        
        pageRankButton(pageRank);
        
        // export csv button and label
        JLabel exportCSVLabel = new JLabel("Export CSV of Graph");
        exportCSVLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        exportCSVLabel.setForeground(Color.RED);
        panel.add(exportCSVLabel);
        
        JButton exportCSV = new JButton("Export CSV");
        panel.add(exportCSV);
        
        exportCSVButton(exportCSV);
        
        buttonEnable(shortestPath, kahn, GSCCs, MSTs, homophily);
        
        // ITEM LISTENERS
        
        weighted.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                if (weighted.isSelected()) {
                    isWeighted = true;
                    addEdgeDialogWeighted(addEdge, popUpPanel);
                } else {
                    isWeighted = false;
                    addEdgeDialogUnweighted(addEdge, popUpPanel);
                }
                
                graph.setWeighted(isWeighted);
                buttonEnable(shortestPath, kahn, GSCCs, MSTs, homophily);
             }
          });
        
        directed.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                if (directed.isSelected()) {
                    isDirected = true;
                } else {
                    isDirected = false;
                }
                
                graph.setDirected(isDirected);
                buttonEnable(shortestPath, kahn, GSCCs, MSTs, homophily);
             }
          });
        
        colored.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                if (colored.isSelected()) {
                    isColored = true;
                    addNodeDialogColored(addNode,popUpPanel);
                } else {
                    isColored = false;
                    addNodeDialogNotColored(addNode,popUpPanel);
                }
                
                buttonEnable(shortestPath, kahn, GSCCs, MSTs, homophily);
             }
          });
    }
    
    private static void exportCSVButton(JButton csvFile) {
        for( ActionListener al : csvFile.getActionListeners() ) {
            csvFile.removeActionListener( al );
        }

        ActionListener listener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GraphCSVConverter converter = new GraphCSVConverter(graph);
                
                converter.convert();

                JOptionPane.showConfirmDialog(null, "NodesCSV.csv and EdgesCSV.csv have been exported "
                        + "to the project folder. You can run GraphR.R to see the graph you made!", 
                        "Exported CSV", JOptionPane.DEFAULT_OPTION); 
            }
        };

        csvFile.addActionListener(listener);
    }
    
    private static void primButton(JButton prim) {
        for( ActionListener al : prim.getActionListeners() ) {
            prim.removeActionListener( al );
        }

        ActionListener listener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String result = "The following edges construct the MST: \n\n";
                Prim primObj = new Prim(graph);
                
                HashMap<Node, Node> output = primObj.mst();
                
              for (Node node : output.keySet()) {
                  if (output.get(node) != null) {
                      result = result + output.get(node).getName() + " -- " + node.getName() + "\n";
                  }
          }

                JOptionPane.showConfirmDialog(null, result, 
                        "Prim's Alg", JOptionPane.DEFAULT_OPTION); 
            }
        };

        prim.addActionListener(listener);
    }
    
    private static void pageRankButton(JButton pageRank) {
        for( ActionListener al : pageRank.getActionListeners() ) {
            pageRank.removeActionListener( al );
        }

        ActionListener listener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String output = "";
                Statistics stat = new Statistics(graph);
                HashMap<Node, Double> pageRanks = stat.getPageRanks();
                for (Node node : pageRanks.keySet()) {
                    output = output + node.getName() + ": " + pageRanks.get(node) + "\n";
                }

                JOptionPane.showConfirmDialog(null, output, 
                        "Page Rank", JOptionPane.DEFAULT_OPTION); 
            }
        };

        pageRank.addActionListener(listener);
    }
    
    private static void kahnButton(JButton kahn) {
        for( ActionListener al : kahn.getActionListeners() ) {
            kahn.removeActionListener( al );
        }

        ActionListener listener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ArrayList<Node> output = Kahns.topologicalSort(graph);
                
                ArrayList<String> names = new ArrayList<String>();
                
                for(Node node : output) {
                    names.add(node.getName());
                }

                JOptionPane.showConfirmDialog(null, "The topo sort for this graph is " + names, 
                        "Kahn's Alg", JOptionPane.DEFAULT_OPTION); 
            }
        };

        kahn.addActionListener(listener);
    }
    
    private static void kosarajuButton(JButton GSCCs) {
        for( ActionListener al : GSCCs.getActionListeners() ) {
            GSCCs.removeActionListener( al );
        }

        ActionListener listener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Kosaraju kosarajuAlg = new Kosaraju(graph);

                java.util.List<java.util.List<String>> output = kosarajuAlg.kosarajuAlg();

                JOptionPane.showConfirmDialog(null, "The GSCCs are " + output, 
                        "Kosaraju's Alg", JOptionPane.DEFAULT_OPTION); 
            }
        };

        GSCCs.addActionListener(listener);
    }

    private static void shortestPathButton(JButton shortestPath) {
        for( ActionListener al : shortestPath.getActionListeners() ) {
            shortestPath.removeActionListener( al );
        }

        ActionListener listener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                BFS bfs = new BFS(graph);
                JTextField node1 = new JTextField(5);
                JTextField node2 = new JTextField(5);
                
                JPanel myPanel = new JPanel();

                myPanel.add(new JLabel("Start node:"));
                myPanel.add(node1); 
                myPanel.add(new JLabel("End node:"));
                myPanel.add(node2); 

                int result = JOptionPane.showConfirmDialog(null, myPanel, 
                         "Enter the two nodes.", JOptionPane.OK_CANCEL_OPTION);
                if (result == JOptionPane.OK_OPTION) {
                    System.out.println("node1: " + node1.getText());
                    System.out.println("node2: " + node2.getText());

                    ArrayList<String> output = bfs.bfsAlgorithm(node1.getText(), node2.getText());
                    
                    if (output.isEmpty()) {
                        JOptionPane.showConfirmDialog(null, "There is no path from " + 
                                node1.getText() + " to " + node2.getText() + ".", "Shortest Path", 
                                JOptionPane.DEFAULT_OPTION); 
                    } else {
                        JOptionPane.showConfirmDialog(null, "The shortest path from " + 
                                node1.getText() + " to " + node2.getText() + " is " + output, 
                                "Shortest Path", JOptionPane.DEFAULT_OPTION); 
                    }
                }
            }
         };
        
         shortestPath.addActionListener(listener);
    }
    
    private static void homophilyButton(JButton homophily) {
        for( ActionListener al : homophily.getActionListeners() ) {
            homophily.removeActionListener( al );
        }

        ActionListener listener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Statistics stats = new Statistics(graph);

                JTextField color1 = new JTextField(5);
                JTextField color2 = new JTextField(5);
                
                JPanel myPanel = new JPanel();

                myPanel.add(new JLabel("Color 1:"));
                myPanel.add(color1); 
                myPanel.add(new JLabel("Color 2:"));
                myPanel.add(color2); 

                int result = JOptionPane.showConfirmDialog(null, myPanel, 
                         "Please enter the two colors of the graph.", JOptionPane.OK_CANCEL_OPTION);
                if (result == JOptionPane.OK_OPTION) {
                    System.out.println("color1: " + color1.getText());
                    System.out.println("color2: " + color2.getText());
                   double homophilyStat = stats.homophily(color1.getText(), color2.getText());
                   
                   JOptionPane.showConfirmDialog(null, "The homophily is " + homophilyStat, 
                           "Homophily", JOptionPane.DEFAULT_OPTION); 
                }
            }
         };
        
         homophily.addActionListener(listener);
    }
    
    private static void ccButton(JButton CC) {
        for( ActionListener al : CC.getActionListeners() ) {
            CC.removeActionListener( al );
        }

        ActionListener listener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Statistics stats = new Statistics(graph);

                JTextField node = new JTextField(5);
                
                JPanel myPanel = new JPanel();

                myPanel.add(new JLabel("Name of node:"));
                myPanel.add(node); 

                int result = JOptionPane.showConfirmDialog(null, myPanel, 
                         "Please enter node name.", JOptionPane.OK_CANCEL_OPTION);
                if (result == JOptionPane.OK_OPTION) {
                   System.out.println("name: " + node.getText());
                   double clusterCoeff = stats.getClusteringCoefficient(node.getText());
                   
                   JOptionPane.showConfirmDialog(null, "The clustering coefficient of " + node.getText() + 
                           " is " + clusterCoeff, "Clustering Coefficient", 
                           JOptionPane.DEFAULT_OPTION); 
                }
            }
         };
        
         CC.addActionListener(listener);
    }
    
    private static void buttonEnable(JButton shortestPath, JButton kahn, 
            JButton GSCCs, JButton MSTs, JButton homophily) {
        if (isWeighted && isDirected) {
            shortestPath.setEnabled(false);
            kahn.setEnabled(true);
            GSCCs.setEnabled(true);
            MSTs.setEnabled(false);
        } else if (isWeighted && !isDirected) {
            shortestPath.setEnabled(false);
            kahn.setEnabled(false);
            GSCCs.setEnabled(false);
            MSTs.setEnabled(true);
        } else if (!isWeighted && isDirected) {
            MSTs.setEnabled(false);
            shortestPath.setEnabled(true);
            kahn.setEnabled(true);
            GSCCs.setEnabled(true);
        } else if (!isWeighted && !isDirected) {
            MSTs.setEnabled(false);
            kahn.setEnabled(false);
            GSCCs.setEnabled(false); 
            shortestPath.setEnabled(true);
        }
        
        if (!isColored) {
            homophily.setEnabled(false);
        } else {
            homophily.setEnabled(true);
        }
    }

    private static void addNodeDialogColored(JButton addNode, JPanel myPanel) {
        for( ActionListener al : addNode.getActionListeners() ) {
            addNode.removeActionListener( al );
        }

        ActionListener listener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JTextField name = new JTextField(5);
                JTextField color = new JTextField(5);

                JPanel myPanel = new JPanel();
                myPanel.add(new JLabel("Name of node:"));
                myPanel.add(name); 
                
                myPanel.add(new JLabel("Color of node (\"none\" if no color): "));
                myPanel.add(color);

                int result = JOptionPane.showConfirmDialog(null, myPanel, 
                         "Please enter name and color of the node.", JOptionPane.OK_CANCEL_OPTION);
                if (result == JOptionPane.OK_OPTION) {
                    graph.addNode(name.getText(), color.getText());
                    
                   System.out.println("Added node " + "\"" + name.getText() + "\"" + " with color " + 
                   color.getText());
                }
            }
         };
        
         addNode.addActionListener(listener);
    }

    private static void addNodeDialogNotColored(JButton addNode, JPanel myPanel) {
        for( ActionListener al : addNode.getActionListeners() ) {
            addNode.removeActionListener( al );
        }

        ActionListener listener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JTextField name = new JTextField(5);

                JPanel myPanel = new JPanel();
                myPanel.add(new JLabel("Name of node:"));
                myPanel.add(name); 

                int result = JOptionPane.showConfirmDialog(null, myPanel, 
                         "Please enter name of the node.", JOptionPane.OK_CANCEL_OPTION);
                if (result == JOptionPane.OK_OPTION) {
                   graph.addNode(name.getText());
                    
                   System.out.println("Added node \"" + name.getText() + "\"");
                }
            }
         };
        
         addNode.addActionListener(listener);
    }

    private static void addEdgeDialogWeighted(JButton addEdge, JPanel myPanel) {
        for( ActionListener al : addEdge.getActionListeners() ) {
            addEdge.removeActionListener( al );
        }


        ActionListener listener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JTextField node1 = new JTextField(5);
                JTextField node2 = new JTextField(5);
                JTextField weight = new JTextField(5);

                myPanel.removeAll();
                
                myPanel.add(new JLabel("Node 1: "));
                myPanel.add(node1);
                
                myPanel.add(new JLabel("Node 2: "));
                myPanel.add(node2);
                
                myPanel.add(new JLabel("Weight: "));
                myPanel.add(weight);

                int result = JOptionPane.showConfirmDialog(null, myPanel, 
                         "Please enter the nodes to add an edge between (and their weight).", 
                         JOptionPane.OK_CANCEL_OPTION);
                if (result == JOptionPane.OK_OPTION) {
                   graph.addEdge(node1.getText(), Integer.parseInt(weight.getText()), node2.getText());
                   
                   System.out.println("Added edge from \"" + node1.getText() + "\" to \"" + node2.getText() + 
                           "\" with edge weight " + weight.getText());
                }
            }
         };
        
         addEdge.addActionListener(listener);
    }
    
    private static void addEdgeDialogUnweighted(JButton addEdge, JPanel myPanel) {
        for( ActionListener al : addEdge.getActionListeners() ) {
            addEdge.removeActionListener( al );
        }
        
        ActionListener listener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JTextField node1 = new JTextField(5);
                JTextField node2 = new JTextField(5);
                myPanel.removeAll();

                myPanel.add(new JLabel("Node 1: "));
                myPanel.add(node1);
                
                myPanel.add(new JLabel("Node 2: "));
                myPanel.add(node2);


                int result = JOptionPane.showConfirmDialog(null, myPanel, 
                         "Please enter the nodes to add an edge between.", 
                         JOptionPane.OK_CANCEL_OPTION);
                if (result == JOptionPane.OK_OPTION) {
                   graph.addEdge(node1.getText(), 1, node2.getText());
                   System.out.println("Added edge from \"" + node1.getText() + "\" to \"" + node2.getText() + "\"");
                }
            }
         };
        
        addEdge.addActionListener(listener);
    }
    
    public Graph getGraph() {
        return graph;
    }
    
    public boolean getIsWeighted() {
        return isWeighted;
    }
}