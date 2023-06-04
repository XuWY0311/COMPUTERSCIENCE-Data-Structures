package ngordnet.main;

import ngordnet.hugbrowsermagic.NgordnetQuery;
import ngordnet.hugbrowsermagic.NgordnetQueryHandler;
import ngordnet.ngrams.NGramMap;

import java.util.*;

public class HyponymsHandler extends NgordnetQueryHandler {
    private WordNet wn;
    private NGramMap ngm;
    public HyponymsHandler(WordNet wordNet, NGramMap ngMap) {
        this.wn = wordNet;
        this.ngm = ngMap;
    }
    @Override
    public String handle(NgordnetQuery q) {
        int startYear = q.startYear();
        int endYear = q.endYear();
        List<String> words = q.words();
        Integer k = q.k();

        List<String> jointHyponymsList = new ArrayList<>();
        String outputString = new String();

        if (words != null) {
            HashSet<String> jointHyponyms = new HashSet<>();
            HashSet<String> currHyponyms = new HashSet<>();
            for (String word : words) {
                if (word.equals(words.get(0))) {
                    jointHyponyms = wn.getHyponyms(word);
                } else {
                    currHyponyms = wn.getHyponyms(word);
                    jointHyponyms.retainAll(currHyponyms);
                }
            }
            jointHyponymsList = new ArrayList<String>(jointHyponyms);
            Collections.sort(jointHyponymsList);
            outputString = jointHyponymsList.toString();
            if (k > 0) {
                List<Double> countsList = new ArrayList<>();
                for (String word : jointHyponymsList) {
                    if (ngm.countHistory(word, startYear, endYear).values() != null) {
                        Collection<Double> counts = ngm.countHistory(word, startYear, endYear).values();
                        double count = counts.stream().mapToDouble(Double::doubleValue).sum();
                        countsList.add(count);
                    } else {
                        countsList.add(0.0);
                    }
                }
                HashMap<Double, String> matchMap = new HashMap<>();
                for (int i = 0; i < jointHyponymsList.size(); i++) {
                    matchMap.put(countsList.get(i), jointHyponymsList.get(i));
                }
                List<Double> helperList = new ArrayList<>();
                helperList.addAll(countsList);
                Collections.sort(helperList, Collections.reverseOrder());
                List<String> anotherList = new ArrayList<>();
                int end = 0;
                int zeroNum = 0;
                for (double i : helperList) {
                    if (i == 0.0) {
                        zeroNum = zeroNum + 1;
                    }
                }
                if (k <= helperList.size() - zeroNum) {
                    end = k;
                } else {
                    end = helperList.size() - zeroNum;
                }
                for (int i = 0; i < end; i++) {
                    Double key = helperList.get(i);
                    String word = matchMap.get(key);
                    anotherList.add(word);
                }
                Collections.sort(anotherList);
                outputString = anotherList.toString();
            }
            return outputString;
        }
        return null;
    }
}
