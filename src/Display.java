import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

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
    
    public static void main(String[] args) {   
        JFrame frame = new JFrame("Graph UI");
        frame.setSize(650, 550);
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
        createGraph.setBounds(20, 5, 20, 5);
        createGraph.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        createGraph.setVisible(true);
        createGraph.setForeground(Color.RED);
        panel.add(createGraph);
        
        JButton addNode = new JButton("Add Node");
        addNode.setBounds(20,40,180,25);
        panel.add(addNode);
        
        JPanel popUpPanel = new JPanel();
        
        addNodeDialogNotColored(addNode, popUpPanel);

        
        JButton addEdge = new JButton("Add Edge");
        addEdge.setBounds(20,70,180,25);
        panel.add(addEdge);
        
        JCheckBox weighted = new JCheckBox("Weighted");
        weighted.setBounds(20,100,180,25);
        panel.add(weighted);
        
        addEdgeDialogUnweighted(addEdge, popUpPanel);
        
        JCheckBox directed = new JCheckBox("Directed");
        directed.setBounds(20,130,180,25);
        panel.add(directed);

        JCheckBox colored = new JCheckBox("Colored");
        colored.setBounds(20,160,180,25);
        panel.add(colored);
        
        // ALGORITHMS
        
        JLabel algorithms = new JLabel("Algorithms");
        algorithms.setBounds(240, 5, 20, 5);
        algorithms.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        algorithms.setVisible(true);
        algorithms.setForeground(Color.RED);
        panel.add(algorithms);
        
        JButton GSCCs = new JButton("Find GSCCs (Kosaraju's alg)");
        GSCCs.setBounds(240,40,280,25);
        panel.add(GSCCs);
        
        JButton MSTs = new JButton("Find the MST (Prim's alg)");
        MSTs.setBounds(240,70,280,25);
        panel.add(MSTs);
        
        JButton kahn = new JButton("Topological Sort (Kahn's alg)");
        kahn.setBounds(240,100,280,25);
        panel.add(kahn);
        
        JButton shortestPath = new JButton("Find Shortest Path (BFS)");
        shortestPath.setBounds(240,130,280,25);
        panel.add(shortestPath);
        
        // STATISTICS
        
        JLabel statistics = new JLabel("Statistics");
        statistics.setBounds(240, 5, 20, 5);
        statistics.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        statistics.setVisible(true);
        statistics.setForeground(Color.RED);
        panel.add(statistics);
        
        JButton CC = new JButton("Find clustering coefficient for a node");
        CC.setBounds(550,40,280,25);
        panel.add(CC);
        
        JButton homophily = new JButton("Find homophily");
        homophily.setBounds(550,70,280,25);
        panel.add(homophily);
        
        JButton betweenness = new JButton("Find betweenness for an edge");
        betweenness.setBounds(550,100,280,25);
        panel.add(betweenness);
        
        JButton pageRank = new JButton("Run PageRank");
        pageRank.setBounds(550,130,280,25);
        panel.add(pageRank);
        
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
    
    private static void buttonEnable(JButton shortestPath, JButton kahn, 
            JButton GSCCs, JButton MSTs, JButton homophily) {
        if (isWeighted && isDirected) {
            shortestPath.setEnabled(false);
            kahn.setEnabled(true);
            GSCCs.setEnabled(true);
            MSTs.setEnabled(true);
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
                   System.out.println("name: " + name.getText());
                   System.out.println("color: " + color.getText());
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
                JTextField color = new JTextField(5);

                JPanel myPanel = new JPanel();
                myPanel.add(new JLabel("Name of node:"));
                myPanel.add(name); 

                int result = JOptionPane.showConfirmDialog(null, myPanel, 
                         "Please enter name and color of the node.", JOptionPane.OK_CANCEL_OPTION);
                if (result == JOptionPane.OK_OPTION) {
                   System.out.println("name: " + name.getText());
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
                   System.out.println("node 1: " + node1.getText());
                   System.out.println("node 2: " + node2.getText());
                   System.out.println("weight: " + weight.getText());
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
                JTextField edge1 = new JTextField(5);
                JTextField edge2 = new JTextField(5);
                myPanel.removeAll();

                myPanel.add(new JLabel("Edge 1: "));
                myPanel.add(edge1);
                
                myPanel.add(new JLabel("Edge 2: "));
                myPanel.add(edge2);


                int result = JOptionPane.showConfirmDialog(null, myPanel, 
                         "Please enter the nodes to add an edge between.", 
                         JOptionPane.OK_CANCEL_OPTION);
                if (result == JOptionPane.OK_OPTION) {
                   System.out.println("edge 1: " + edge1.getText());
                   System.out.println("edge 2: " + edge2.getText());
                }
            }
         };
        
        addEdge.addActionListener(listener);
    }
}