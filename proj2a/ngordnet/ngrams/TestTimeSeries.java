package ngordnet.ngrams;

import org.junit.Test;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/** Unit Tests for the TimeSeries class.
 *  @author Josh Hug
 */
public class TestTimeSeries {
    @Test
    public void testYears() {
        TimeSeries catPopulation = new TimeSeries();
        catPopulation.put(1991, 0.0);
        catPopulation.put(1992, 100.0);
        catPopulation.put(1994, 200.0);
        List<Integer> expectedYears = new ArrayList<>
                (Arrays.asList(1991, 1992, 1994));
        assertEquals(expectedYears, catPopulation.years());
    }

    @Test
    public void testData() {
        TimeSeries catPopulation = new TimeSeries();
        catPopulation.put(1991, 0.0);
        catPopulation.put(1992, 100.0);
        catPopulation.put(1994, 200.0);
        List<Double> expectedData = new ArrayList<>
                (Arrays.asList(0.0, 100.0, 200.0));
        assertEquals(expectedData, catPopulation.data());
    }

    @Test
    public void testCopy() {
        TimeSeries catPopulation = new TimeSeries();
        catPopulation.put(1991, 0.0);
        catPopulation.put(1992, 100.0);
        catPopulation.put(1994, 200.0);
        TimeSeries catCopy = new TimeSeries(catPopulation, catPopulation.firstKey(), catPopulation.lastKey());
        List<Integer> expectedYears = new ArrayList<>
                (Arrays.asList(1991, 1992, 1994));
        assertEquals(expectedYears, catCopy.years());
    }

    @Test
    public void testFromSpec() {
        TimeSeries catPopulation = new TimeSeries();
        catPopulation.put(1991, 0.0);
        catPopulation.put(1992, 100.0);
        catPopulation.put(1994, 200.0);

        TimeSeries dogPopulation = new TimeSeries();
        dogPopulation.put(1994, 400.0);
        dogPopulation.put(1995, 500.0);

        TimeSeries totalPopulation = catPopulation.plus(dogPopulation);
        // expected: 1991: 0,
        //           1992: 100
        //           1994: 600
        //           1995: 500

        List<Integer> expectedYears = new ArrayList<>
                (Arrays.asList(1991, 1992, 1994, 1995));

        assertEquals(expectedYears, totalPopulation.years());

        List<Double> expectedTotal = new ArrayList<>
                (Arrays.asList(0.0, 100.0, 600.0, 500.0));

        for (int i = 0; i < expectedTotal.size(); i += 1) {
            assertEquals(expectedTotal.get(i), totalPopulation.data().get(i), 1E-10);
        }
    }

    @Test
    public void testDividedBy() {
        TimeSeries catPopulation = new TimeSeries();
        catPopulation.put(1992, 100.0);
        catPopulation.put(1994, 200.0);

        TimeSeries dogPopulationFirst = new TimeSeries();
        dogPopulationFirst.put(1991, 0.0);
        dogPopulationFirst.put(1992, 100.0);
        dogPopulationFirst.put(1994, 200.0);

        TimeSeries dividedPopulation = catPopulation.dividedBy(dogPopulationFirst);
        //           1992: 1.0
        //           1994: 1.0

        List<Double> expectedNumber = new ArrayList<>
                (Arrays.asList(1.0, 1.0));
        for (int i = 0; i < expectedNumber.size(); i += 1) {
            assertEquals(expectedNumber.get(i), dividedPopulation.data().get(i), 1E-10);
        }
    }
} 