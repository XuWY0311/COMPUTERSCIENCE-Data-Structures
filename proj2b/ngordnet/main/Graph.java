package ngordnet.main;

import org.junit.platform.commons.util.StringUtils;

import java.util.*;
import java.util.ArrayList;


public class Graph {

    /** ✔Step 1: Declarations. */
    /** ✔1-1. Variable 1: nodeGraph. */
    private HashMap<Node, ArrayList<Node>> nodeGraph;
    /** ✔1-2. Variable 2: idNodeMap1. */
    private HashMap<Integer, Graph.Node> idNodeMap1;
    /** ✔1-3. Variable 3: wordIDListMap2. */
    private HashMap<String, List<Integer>> wordIDListMap2;
    /** ✔1-4. Variable 4: node. */
    public class Node {
        private int id;
        private String synset;
        public Node(int number, String string) {
            this.id = number;
            this.synset = string;
        }
        public int id() {
            return this.id;
        }
        public String synset() {
            return this.synset;
        }
    }
    /** ✔1-5. Variable 5: adjList. */
    private HashSet<Node> adjList;
    /** ✔1-6. Variable 6: dfs_stack. */
    private Stack<String> stack;
    /** ✔1-7. Variable 7: dfs_visitPath. */
    private HashSet<String> visitPath;
    /** ✔1-8. Variable 8: dfs_markedList. */
    private HashSet<Node> markedList;

    /** ✔Step 2: Construction. */
    public Graph() {
        nodeGraph = new HashMap<Node, ArrayList<Node>>();
        idNodeMap1 = new HashMap<>();
        wordIDListMap2 = new HashMap<>();
    }

    /** ✔Step 3: Methods. */
    /** ✔3-1: addNode(node). */
    public void addNode(int id, String synset) {
        Node newNode = new Node(id, synset);
        ArrayList<Node> itsAL = new ArrayList<>();
        nodeGraph.put(newNode, itsAL);
        idNodeMap1.put(id, newNode);
        if (StringUtils.containsWhitespace(synset)) {
            String[] wordsList = synset.split(" ");
            for (String word : wordsList) {
                if (!wordIDListMap2.containsKey(word)) {
                    List<Integer> idList = new ArrayList<>();
                    idList.add(id);
                    wordIDListMap2.put(word, idList);
                } else {
                    List<Integer> idList = wordIDListMap2.get(word);
                    idList.add(id);
                    wordIDListMap2.put(word, idList);
                }
            }
        } else {
            if (!wordIDListMap2.containsKey(synset)) {
                List<Integer> idList = new ArrayList<>();
                idList.add(id);
                wordIDListMap2.put(synset, idList);
            } else {
                List<Integer> idList = wordIDListMap2.get(synset);
                idList.add(id);
                wordIDListMap2.put(synset, idList);
            }
        }
    }
    /** ✔3-2: addEdge(idA, idB) [nodeA -> nodeB]. */
    public void addEdge(int idA, int idB) {
        Node nodeA = getNode(idA);
        Node nodeB = getNode(idB);
        ArrayList<Node> itsAL = nodeGraph.get(nodeA);
        itsAL.add(nodeB);
        nodeGraph.put(nodeA, itsAL);
    }
    /** ✔3-3: adj(node). */
    public ArrayList<Node> adj(Node node) {
        return nodeGraph.get(node);
    }
    /** ✔3-4: getNode(id). */
    public Node getNode(int id) {
        return idNodeMap1.get(id);
    }
    /** ✔3-5: getNodeList(synset). */
    public HashSet<Node> getNodeList(String synset) {
        HashSet<Node> nodes = new HashSet<>();
        List<Integer> itsIDs = wordIDListMap2.get(synset);
        if (itsIDs != null) {
            for (int id : itsIDs) {
                nodes.add(idNodeMap1.get(id));
            }
        }
        return nodes;
    }
    /** ✔3-6: dfs(synset). */
    public void dfsHelper(Node node) {
        Node currentNode = node;
        for (Node neighbor : adj(currentNode)) {
            if (!markedList.contains(neighbor)) {
                stack.push(neighbor.synset);
                visitPath.add(neighbor.synset);
                markedList.add(neighbor);
                dfsHelper(neighbor);
                stack.pop();
            }
        }
    }
    public HashSet<String> dfs(String synset) {
        stack = new Stack<>();
        visitPath = new HashSet<>();
        markedList = new HashSet<>();
        HashSet<Node> startNode = getNodeList(synset);
        for (Node node : startNode) {
            Node currentNode = node;
            stack.push(currentNode.synset);
            visitPath.add(currentNode.synset);
            markedList.add(currentNode);
            dfsHelper(currentNode);
        }
        return visitPath;
    }
}
