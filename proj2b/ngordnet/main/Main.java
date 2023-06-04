package ngordnet.main;

import ngordnet.hugbrowsermagic.HugNgordnetServer;
import ngordnet.ngrams.NGramMap;

public class Main {
    public static void main(String[] args) {
        HugNgordnetServer hns = new HugNgordnetServer();
        String synsetFile = "./data/wordnet/synsets16.txt";
        String hyponymFile = "./data/wordnet/hyponyms16.txt";

        WordNet wn = new WordNet(synsetFile, hyponymFile);
//        NGramMap ngm = new NGramMap();
//
//        hns.startUp();
//        hns.register("history", new HistoryHandler(ngm));
//        hns.register("historytext", new HistoryTextHandler(ngm));
//        hns.register("hyponyms", new HyponymHandler(wn));
    }
}
