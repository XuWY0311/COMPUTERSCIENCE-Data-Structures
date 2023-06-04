package IntList;

import static org.junit.Assert.*;
import org.junit.Test;

public class SquarePrimesTest {

    /**
     * Here is a test for isPrime method. Try running it.
     * It passes, but the starter code implementation of isPrime
     * is broken. Write your own JUnit Test to try to uncover the bug!
     */
    @Test
    public void testSquarePrimesSimple() {
        IntList lst = IntList.of(14, 15, 16, 17, 18);
        boolean changed = IntListExercises.squarePrimes(lst);
        assertEquals("14 -> 15 -> 16 -> 289 -> 18", lst.toString());
        assertTrue(changed);
    }

    @Test
    public void testSquarePrimesComplex_NoPrime() {
        IntList lst = IntList.of(4, 12, 16, 18, 20);
        boolean changed = IntListExercises.squarePrimes(lst);
        assertEquals("4 -> 12 -> 16 -> 18 -> 20", lst.toString());
        assertFalse(changed);
    }

    @Test
    public void testSquarePrimesComplex_MoreThanOnePrime() {
        IntList lst = IntList.of(3, 3, 16, 18, 20);
        boolean changed = IntListExercises.squarePrimes(lst);
        assertEquals("9 -> 9 -> 16 -> 18 -> 20", lst.toString());
        assertTrue(changed);
    }
}
