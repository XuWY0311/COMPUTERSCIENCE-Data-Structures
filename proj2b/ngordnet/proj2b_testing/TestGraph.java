package ngordnet.proj2b_testing;
import ngordnet.main.Graph;
import org.junit.Test;

import java.util.HashSet;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class TestGraph extends Graph{

    @Test
    public void testAddNode() {
        Graph thisGraph = new Graph();
        thisGraph.addNode(2002, "Year");
        thisGraph.addNode(03, "Month");
        thisGraph.addNode(11, "Date_1");
        thisGraph.addNode(11, "Date_2");
    }
    @Test
    public void testAddEdge() {
        Graph thisGraph = new Graph();
        thisGraph.addNode(2002, "Year");
        thisGraph.addNode(03, "Month");
        thisGraph.addEdge(2002, 03);
    }
    @Test
    public void testGetNode() {
        Graph thisGraph = new Graph();
        thisGraph.addNode(2002, "Year");
        thisGraph.addNode(03, "Month");
        Node node = thisGraph.getNode(2002);
        assertEquals(node.id(), 2002);
        assertEquals(node.synset(), "Year");
    }
    @Test
    public void testDFS() {
        Graph thisGraph = new Graph();
        thisGraph.addNode(2002, "Year");
        thisGraph.addNode(03, "Month");
        thisGraph.addNode(11, "Date_1");
        thisGraph.addEdge(2002, 03);
        thisGraph.addEdge(03, 11);
        thisGraph.addEdge(2002, 11);
        HashSet<String> dfsPath = thisGraph.dfs("Year");
//        assertEquals(dfsPath.get(0),"Year");
//        assertEquals(dfsPath.get(1),"Month");
//        assertEquals(dfsPath.get(2),"Date_1");
    }
    @Test
    public void testGetNodeList() {
        Graph thisGraph = new Graph();
        thisGraph.addNode(2002, "Year XU");
        thisGraph.addNode(03, "Month XU");
        thisGraph.addNode(11, "Date_1 XU");
        thisGraph.addEdge(2002, 03);
        thisGraph.addEdge(03, 11);
        thisGraph.addEdge(2002, 11);
        HashSet<Graph.Node> results = thisGraph.getNodeList("XU");
        for (Graph.Node result : results) {
            System.out.println(result.id());
        }
    }
}
