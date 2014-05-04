package graph;

public class GraphTest2 {

	public static void main(String[] args) {
        Graph2 G = new Graph2();
        G.addVertex("A");
        G.addVertex("B");
        G.addEdge("A", "B");

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
