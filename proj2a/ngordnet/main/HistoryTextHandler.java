package ngordnet.main;

import ngordnet.hugbrowsermagic.NgordnetQuery;
import ngordnet.hugbrowsermagic.NgordnetQueryHandler;
import ngordnet.ngrams.NGramMap;

import java.util.List;

public class HistoryTextHandler extends NgordnetQueryHandler {
    private NGramMap ngm;
    public HistoryTextHandler(NGramMap someNGM) {
        ngm = someNGM;
    }

    @Override
    public String handle(NgordnetQuery q) {
        List<String> words = q.words();
        int startYear = q.startYear();
        int endYear = q.endYear();
        int wordsSize = words.size();

        String response = new String();
        for (int i = 0; i <= wordsSize - 1; i++) {
            response += words.get(i) + ": " + (ngm.weightHistory(words.get(i), startYear, endYear).toString()) + "\n";
        }
        return response;
    }
}
