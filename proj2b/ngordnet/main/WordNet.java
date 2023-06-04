package ngordnet.main;

import edu.princeton.cs.algs4.In;
import org.junit.platform.commons.util.StringUtils;

import java.util.*;

public class WordNet {
    /** ✔Step 1: Declarations. */
    /** ✔1-1. Variable 1: nodeGraph. */
    private Graph totalGraph;
    /** ✔1-2. Variable 4: Magic Number. */
    private static final int SPLIT_NUMBER = 3;

    /** ✔Step 2: Construction. */
    public WordNet(String idSynsetFileName, String hyponymsFileName) {
        /** 2-1. Instantiate the graph and maps. */
        totalGraph = new Graph();
        /** 2-2. Load in the ID-Synset file. */
        In idSynsetFile = new In(idSynsetFileName);
        while (idSynsetFile.hasNextLine()) {
            String thisLine = idSynsetFile.readLine();
            String[] thisLineSplit = thisLine.split(",", SPLIT_NUMBER);
            int id = Integer.valueOf(thisLineSplit[0]);
            String synset = String.valueOf(thisLineSplit[1]);
            String definition = String.valueOf(thisLineSplit[2]);
            totalGraph.addNode(id, synset);
        }
        /** 2-3. Load in the Hyponyms file. */
        In hyponymsFile = new In(hyponymsFileName);
        while (hyponymsFile.hasNextLine()) {
            String thisLine = hyponymsFile.readLine();
            String[] thisLineSplit = thisLine.split(",");
            int hypernymID = 0;
            int hyponymID;
            for (String word : thisLineSplit) {
                if (word.equals(thisLineSplit[0])) {
                    hypernymID = Integer.valueOf(word);
                } else {
                    hyponymID = Integer.valueOf(word);
                    totalGraph.addEdge(hypernymID, hyponymID);
                }
            }
        }
    }
    /** ✔Step 3: Methods. */
    /** ✔3-1: getHyponyms(hypernym). */
    public HashSet<String> getHyponyms(String hypernym) {
        HashSet<String> hyponyms = totalGraph.dfs(hypernym);
        HashSet<String> resultsSet = new HashSet<>(hyponyms);

        HashSet<String> finalResultsSet = new HashSet<>();
        for (String hyponym : resultsSet) {
            if (StringUtils.containsWhitespace(hyponym)) {
                String[] hyponymSet = hyponym.split(" ");
                finalResultsSet.addAll(Arrays.asList(hyponymSet));
            } else {
                finalResultsSet.add(hyponym);
            }
        }
        return finalResultsSet;
    }
}
