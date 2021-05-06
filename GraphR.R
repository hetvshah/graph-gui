install.packages("tidyverse")
install.packages("network")
install.packages("igraph")
install.packages("tidygraph")
install.packages("ggraph")
install.packages("rJava")

library(tidyverse)
library(network)
library(igraph)
library(tidygraph)
library(ggraph)
library(rJava)


NodesCSV = read.csv("~/Downloads/NodesCSV.csv")
EdgesCSV = read.csv("~/Downloads/EdgesCSV.csv")
nodes = NodesCSV

edges = tibble(from = EdgesCSV$From, 
               to = EdgesCSV$To,
               weight = EdgesCSV$Weight)

routes_igraph = graph_from_data_frame(d = edges, vertices = nodes, directed = TRUE)

routes_tidy = as_tbl_graph(routes_igraph)

ggraph(routes_tidy, layout = "graphopt") + 
  geom_node_point(colour = NodesCSV$Color, size = 4) +
  geom_edge_link(aes(width = weight), alpha = 0.8, arrow = arrow(length = unit(5, 'mm'))) + 
  scale_edge_width(range = c(0.1, 0.9)) +
  geom_node_label(aes(label = name), repel = FALSE, nudge_x = 5) +
  labs(edge_width = "Edge Weight") +
  theme_graph()

