import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;

public class Graph {

  static class Node{
    public final String name;
    public final HashSet<Edge> inEdges;
    public final HashSet<Edge> outEdges;
    public Node(String name) {
      this.name = name;
      inEdges = new HashSet<Edge>();
      outEdges = new HashSet<Edge>();
    }
    public Node addEdge(Node node){
      Edge e = new Edge(this, node);
      outEdges.add(e);
      node.inEdges.add(e);
      return this;
    }
    @Override
    public String toString() {
      return name;
    }
  }

  static class Edge{
    public final Node from;
    public final Node to;
    public Edge(Node from, Node to) {
      this.from = from;
      this.to = to;
    }
    @Override
    public boolean equals(Object obj) {
      Edge e = (Edge)obj;
      return e.from == from && e.to == to;
    }
  }

  public static void main(String[] args) {
    Node seven = new Node("7");
    Node five = new Node("5");
    Node three = new Node("3");
    Node eleven = new Node("11");
    Node eight = new Node("8");
    Node two = new Node("2");
    Node nine = new Node("9");
    Node ten = new Node("10");
    seven.addEdge(eleven).addEdge(eight);
    five.addEdge(eleven);
    three.addEdge(eight).addEdge(ten);
    eleven.addEdge(two).addEdge(nine).addEdge(ten);
    eight.addEdge(nine).addEdge(ten);

    Node[] allNodes = {seven, five, three, eleven, eight, two, nine, ten};
    //L <- Empty list that will contain the sorted elements
    ArrayList<Node> L = new ArrayList<Node>();

    //S <- Set of all nodes with no incoming edges
    HashSet<Node> S = new HashSet<Node>(); 
    for(Node n : allNodes){
      if(n.inEdges.size() == 0){
        S.add(n);
      }
    }

    //while S is non-empty do
    while(!S.isEmpty()){
      //remove a node n from S
      Node n = S.iterator().next();
      S.remove(n);

      //insert n into L
      L.add(n);

      //for each node m with an edge e from n to m do
      for(Iterator<Edge> it = n.outEdges.iterator();it.hasNext();){
        //remove edge e from the graph
        Edge e = it.next();
        Node m = e.to;
        it.remove();//Remove edge from n
        m.inEdges.remove(e);//Remove edge from m

        //if m has no other incoming edges then insert m into S
        if(m.inEdges.isEmpty()){
          S.add(m);
        }
      }
    }
    //Check to see if all edges are removed
    boolean cycle = false;
    for(Node n : allNodes){
      if(!n.inEdges.isEmpty()){
        cycle = true;
        break;
      }
    }
    if(cycle){
      System.out.println("Cycle present, topological sort not possible");
    }else{
      System.out.println("Topological Sort: "+Arrays.toString(L.toArray()));
    }
  }
}