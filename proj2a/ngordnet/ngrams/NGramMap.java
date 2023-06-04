package ngordnet.ngrams;

import edu.princeton.cs.algs4.In;

import java.util.*;

/** An object that provides utility methods for making queries on the
 *  Google NGrams dataset (or a subset thereof).
 *
 *  An NGramMap stores pertinent data from a "words file" and a "counts
 *  file". It is not a map in the strict sense, but it does provide additional
 *  functionality.
 *
 *  @author Josh Hug
 */
public class NGramMap {

    /** Part 1: Declarations. */

    /** 1. Creates a HashMap and a TimeSeries as loaders for wordsFile. */
    private HashMap<String, TimeSeries> wordsHashMap = new HashMap<>();
    /** 2. Creates a TimeSeries as a loader for countsFile. */
    private TimeSeries tsCountsFile = new TimeSeries();
    /** 3. Creates a HashMap as an overall loader. */
    private HashMap<HashMap<String, TimeSeries>, TimeSeries> nGramMap = new HashMap<>();
    private static final int MAGIC_NUMBER = 4;


    /** Part 2: Initiations. */

    /** Constructs / Initiates an NGramMap from WORDSFILENAME and COUNTSFILENAME. */
    public NGramMap(String wordsFilename, String countsFilename) {
        /** 1. Reads the wordsFile. */
        /** 1-1. Creates two data structures to better handle. */
        TimeSeries tsTemp = new TimeSeries();
        ArrayList<String> wordsArray = new ArrayList();
        /** 1-2. Outputs the data from the wordsFile. */
        In wordsFile = new In(wordsFilename);
        while (!wordsFile.isEmpty()) {
            String words = wordsFile.readString();
            int years = wordsFile.readInt();
            Double counts = wordsFile.readDouble();
            Double irrelavants = wordsFile.readDouble();
            /** 1-3. Inputs the data to the HashMap and TimeSeries. */
            /** 1-3-1. If this word is a previous word. */
            if (wordsArray.contains(words)) {
                tsTemp.put(years, counts);
            } else {
                /** 1-3-2. If this word is the first word. */
                if (wordsArray.size() == 0) {
                    wordsArray.add(words);
                    tsTemp.put(years, counts);
                } else {
                    /** 1-3-3. If this word is a new word. */
                    String prevWord = wordsArray.get(wordsArray.size() - 1);
                    wordsHashMap.put(prevWord, tsTemp);
                    tsTemp = new TimeSeries();
                    wordsArray.add(words);
                    tsTemp.put(years, counts);
                }
            }
        }
        String prevWord = wordsArray.get(wordsArray.size() - 1);
        wordsHashMap.put(prevWord, tsTemp);
        /** 2. Reads the countsFile. */
        /** 2-1. Outputs the data from the countsFile. */
        In countsFile = new In(countsFilename);
        while (countsFile.hasNextLine()) {
            String thisLine = countsFile.readLine();
            String[] thisLineSplit = thisLine.split(",", MAGIC_NUMBER);
            int years = Integer.valueOf(thisLineSplit[0]);
            Double counts = Double.valueOf(thisLineSplit[1]);
            Double firstIrrelavants = Double.valueOf(thisLineSplit[2]);
            Double secondIrrelavants = Double.valueOf(thisLineSplit[MAGIC_NUMBER - 1]);
            /** 2-2. Inputs the data to the TimeSeries. */
            tsCountsFile.put(years, counts);
        }
        /** 3. Combines the data structures above to make it an overall NGramMap. */
        nGramMap.put(wordsHashMap, tsCountsFile);
    }

    /** Part 3: Methods. */

    /** Method 1. */
    /** Provides the history of WORD. The returned TimeSeries should be a copy,
     *  not a link to this NGramMap's TimeSeries. In other words, changes made
     *  to the object returned by this function should not also affect the
     *  NGramMap. This is also known as a "defensive copy". */
    public TimeSeries countHistory(String word) {
        TimeSeries copiedHistory = new TimeSeries();
        TimeSeries correspondingTS = wordsHashMap.get(word);
        int firstYear = correspondingTS.firstKey();
        int lastYear = correspondingTS.lastKey();
        int year = firstYear;
        while (year <= lastYear) {
            if (correspondingTS.containsKey(year)) {
                copiedHistory.put(year, correspondingTS.get(year));
            }
            year = year + 1;
        }
        return copiedHistory;
    }

    /** Method 2. */
    /** Provides the history of WORD between STARTYEAR and ENDYEAR, inclusive of both ends. The
     *  returned TimeSeries should be a copy, not a link to this NGramMap's TimeSeries. In other words,
     *  changes made to the object returned by this function should not also affect the
     *  NGramMap. This is also known as a "defensive copy". */
    public TimeSeries countHistory(String word, int startYear, int endYear) {
        TimeSeries copiedHistory = new TimeSeries();
        TimeSeries correspondingTS = wordsHashMap.get(word);
        int firstYearThisWord = correspondingTS.firstKey();
        int lastYearThisWord = correspondingTS.lastKey();
        int firstYear;
        int lastYear;
        if (startYear <= firstYearThisWord) {
            firstYear = firstYearThisWord;
        } else {
            firstYear = startYear;
        }
        if (endYear >= lastYearThisWord) {
            lastYear = lastYearThisWord;
        } else {
            lastYear = endYear;
        }
        int year = firstYear;
        while (year <= lastYear) {
            if (correspondingTS.containsKey(year)) {
                copiedHistory.put(year, correspondingTS.get(year));
            }
            year = year + 1;
        }
        return copiedHistory;
    }

    /** Method 3. */
    /** Returns a defensive copy of the total number of words recorded per year in all volumes. */
    public TimeSeries totalCountHistory() {
        TimeSeries copiedHistory = new TimeSeries();
        int firstYear = tsCountsFile.firstKey();
        int lastYear = tsCountsFile.lastKey();
        int year = firstYear;
        while (year <= lastYear) {
            if (tsCountsFile.containsKey(year)) {
                copiedHistory.put(year, tsCountsFile.get(year));
            }
            year = year + 1;
        }
        return copiedHistory;
    }

    /** Method 4. */
    /** Provides a TimeSeries containing the relative frequency per year of WORD compared to
     *  all words recorded in that year. */
    public TimeSeries weightHistory(String word) {
        TimeSeries weightHistory = countHistory(word).dividedBy(tsCountsFile);
        return weightHistory;
    }

    /** Method 5. */
    /** Provides a TimeSeries containing the relative frequency per year of WORD between STARTYEAR
     *  and ENDYEAR, inclusive of both ends. */
    public TimeSeries weightHistory(String word, int startYear, int endYear) {
        TimeSeries weightHistory = countHistory(word, startYear, endYear).dividedBy(tsCountsFile);
        return weightHistory;
    }

    /** Method 6. */
    /** Returns the summed relative frequency per year of all words in WORDS. */
    public TimeSeries summedWeightHistory(Collection<String> words) {
        TimeSeries totalPopulation = new TimeSeries();
        for (String word : words) {
            if (totalPopulation.size() == 0) {
                totalPopulation = countHistory(word);
            } else {
                totalPopulation = totalPopulation.plus(countHistory(word));
            }
        }
        TimeSeries summedWeightHistory = totalPopulation.dividedBy(tsCountsFile);
        return summedWeightHistory;
    }

    /** Method 7. */
    /** Provides the summed relative frequency per year of all words in WORDS
     *  between STARTYEAR and ENDYEAR, inclusive of both ends. If a word does not exist in
     *  this time frame, ignore it rather than throwing an exception. */
    public TimeSeries summedWeightHistory(Collection<String> words, int startYear, int endYear) {
        TimeSeries totalPopulation = new TimeSeries();
        for (String word : words) {
            if (totalPopulation.size() == 0) {
                totalPopulation = countHistory(word, startYear, endYear);
            } else {
                totalPopulation = totalPopulation.plus(countHistory(word, startYear, endYear));
            }
        }
        TimeSeries summedWeightHistory = totalPopulation.dividedBy(tsCountsFile);
        return summedWeightHistory;
    }
}
