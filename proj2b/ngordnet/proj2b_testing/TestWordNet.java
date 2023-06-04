package ngordnet.proj2b_testing;

import ngordnet.main.Graph;
import ngordnet.main.WordNet;
import org.junit.Test;

import java.util.HashSet;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class TestWordNet {
    @Test
    public void testWNCase1() {
        String SMALL_SYNSET_FILE = "data/wordnet/synsets11.txt";
        String SMALL_HYPONYM_FILE = "data/wordnet/hyponyms11.txt";
        WordNet wn = new WordNet(SMALL_SYNSET_FILE, SMALL_HYPONYM_FILE);
        HashSet<String> actHyponyms = wn.getHyponyms("action");
//        assertEquals(actHyponyms.get(0),"action");
//        assertEquals(actHyponyms.get(1),"change");
//        assertEquals(actHyponyms.get(2),"demotion");
    }
    @Test
    public void testWNCase2() {
        String SMALL_SYNSET_FILE = "data/wordnet/synsets11.txt";
        String SMALL_HYPONYM_FILE = "data/wordnet/hyponyms11.txt";
        WordNet wn = new WordNet(SMALL_SYNSET_FILE, SMALL_HYPONYM_FILE);
        HashSet<String> desHyponyms = wn.getHyponyms("descent");
//        assertEquals(desHyponyms.get(0),"descent");
//        assertEquals(desHyponyms.get(1),"jump");
//        assertEquals(desHyponyms.get(2),"parachuting");
    }
}
