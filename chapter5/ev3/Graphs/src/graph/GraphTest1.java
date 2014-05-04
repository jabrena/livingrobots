package graph;

public class GraphTest1 {

	public static void main(String[] args) {
        Graph G = new Graph();
        G.addEdge("A", "B");
        G.addEdge("A", "C");
        G.addEdge("C", "D");
        G.addEdge("D", "E");
        G.addEdge("D", "G");
        G.addEdge("E", "G");
        //G.addVertex("H");

        // print out graph
        //System.out.println(G);


        // print out graph again by iterating over vertices and edges
        for (String v : G.vertices()) {
        	System.out.print(v + ": ");
            for (String w : G.adjacentTo(v)) {
            	System.out.print(w + " ");
            }
            System.out.println();
        }

    }
}
