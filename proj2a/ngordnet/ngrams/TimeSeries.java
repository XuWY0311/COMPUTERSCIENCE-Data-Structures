package ngordnet.ngrams;

import java.util.*;

/** An object for mapping a year number (e.g. 1996) to numerical data. Provides
 *  utility methods useful for data analysis.
 *  @author Josh Hug
 */
public class TimeSeries extends TreeMap<Integer, Double> {
    /**
     * ✔Constructs a new empty TimeSeries.
     */
    public TimeSeries() {
        super();
    }

    /**
     * ✔Creates a copy of TS, but only between STARTYEAR and ENDYEAR,
     * inclusive of both end points.
     */
    public TimeSeries(TimeSeries ts, int startYear, int endYear) {
        super();
        int year = startYear;
        while (year <= endYear) {
            if (ts.containsKey(year)) {
                this.put(year, ts.get(year));
            }
            year = year + 1;
        }
    }

    /**
     * ✔Returns all years for this TimeSeries (in any order).
     */
    public List<Integer> years() {
        Set<Integer> yearSet = this.keySet();
        List<Integer> yearsList = new ArrayList<>(this.size());
        for (int year : yearSet) {
            yearsList.add(year);
        }
        return yearsList;
    }

    /**
     * ✔Returns all data for this TimeSeries (in any order).
     * Must be in the same order as years().
     */
    public List<Double> data() {
        List<Double> dataList = new ArrayList<>(this.size());
        for (int year : this.years()) {
            dataList.add(this.get(year));
        }
        return dataList;
    }

    /**
     * ✔Returns the yearwise sum of this TimeSeries with the given TS. In other words, for
     * each year, sum the data from this TimeSeries with the data from TS. Should return a
     * new TimeSeries (does not modify this TimeSeries).
     */
    public TimeSeries plus(TimeSeries ts) {
        TimeSeries copyOfLeftTree = new TimeSeries(this, this.firstKey(), this.lastKey());
        for (int year : ts.keySet()) {
            /** For the same key. */
            if (copyOfLeftTree.containsKey(year)) {
                double newData = copyOfLeftTree.get(year) + ts.get(year);
                copyOfLeftTree.put(year, newData);
                /** For different keys. */
            } else {
                copyOfLeftTree.put(year, ts.get(year));
            }
        }
        return copyOfLeftTree;
    }

    /**
     * ✔Returns the quotient of the value for each year this TimeSeries divided by the
     * value for the same year in TS.
     * If TS is missing a year that exists in this TimeSeries,
     * throw an IllegalArgumentException.
     * If TS has a year that is not in this TimeSeries, ignore it.
     * Should return a new TimeSeries (does not modify this TimeSeries).
     */
    public TimeSeries dividedBy(TimeSeries ts) {
        TimeSeries copyOfLeftTree = new TimeSeries();
        if (!this.isEmpty()) {
            copyOfLeftTree = new TimeSeries(this, this.firstKey(), this.lastKey());
            for (int year : this.keySet()) {
                if (!ts.containsKey(year)) {
                    throw new IllegalArgumentException("numerator has a missing year");
                }
                double newData = copyOfLeftTree.get(year) / ts.get(year);
                copyOfLeftTree.put(year, newData);
            }
        }
        return copyOfLeftTree;
    }
}
