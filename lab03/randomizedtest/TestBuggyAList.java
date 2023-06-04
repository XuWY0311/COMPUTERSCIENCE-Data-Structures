package randomizedtest;

import edu.princeton.cs.algs4.StdRandom;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Created by hug.
 */

public class TestBuggyAList {

    @Test
    public void testThreeAddThreeRemove(){
        AListNoResizing<Integer> list1 = new AListNoResizing();
        BuggyAList<Integer> list2 = new BuggyAList();
        list1.addLast(4);
        list1.addLast(5);
        list1.addLast(6);
        list2.addLast(4);
        list2.addLast(5);
        list2.addLast(6);
        assertEquals(list1.size(), list2.size());
        assertEquals(list1.removeLast(), list2.removeLast());
        assertEquals(list1.removeLast(), list2.removeLast());
        assertEquals(list1.removeLast(), list2.removeLast());
    }
    @Test
    public void randomizedTest(){
        AListNoResizing<Integer> Right = new AListNoResizing<>();
        BuggyAList<Integer> toBeChecked = new BuggyAList<>();
        int N = 5000;
        for (int i = 0; i < N; i += 1) {
            int operationNumber = StdRandom.uniform(0, 4);

            if (operationNumber == 0) {
                // addLast
                int randVal = StdRandom.uniform(0, 100);
                Right.addLast(randVal);
                toBeChecked.addLast(randVal);

            } else if (operationNumber == 1) {
                // size
                int size1 = Right.size();
                int size2 = toBeChecked.size();
                assertEquals(size1, size2);

            } else if (operationNumber == 2) {
                // getLast
                if (Right.size() >= 1) {
                    int getLastValue1 = Right.getLast();
                    int getLastValue2 = toBeChecked.getLast();
                    assertEquals(getLastValue1, getLastValue2);
                }

            } else if (operationNumber == 3) {
                // removelast
                if (Right.size() >= 1) {
                    int removedLastValue1 = Right.removeLast();
                    int removedLastValue2 = toBeChecked.removeLast();
                    assertEquals(removedLastValue1, removedLastValue2);
                }
            }
        }
    }
}
