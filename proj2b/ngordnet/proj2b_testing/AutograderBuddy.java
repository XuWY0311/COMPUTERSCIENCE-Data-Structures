package ngordnet.proj2b_testing;

import ngordnet.hugbrowsermagic.NgordnetQueryHandler;
import ngordnet.main.HyponymsHandler;
import ngordnet.main.WordNet;
import ngordnet.ngrams.NGramMap;


public class AutograderBuddy {
    /** Returns a HyponymHandler */
    public static NgordnetQueryHandler getHyponymHandler(
            String wordFile, String countFile,
            String synsetFile, String hyponymFile) {
        WordNet wn = new WordNet(synsetFile, hyponymFile);
        NGramMap ngm = new NGramMap(wordFile, countFile);
        HyponymsHandler thisHandler = new HyponymsHandler(wn, ngm);

        return thisHandler;
    }
}
