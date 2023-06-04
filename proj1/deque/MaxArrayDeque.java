package deque;
import java.util.Comparator;

public class MaxArrayDeque<T> extends ArrayDeque<T> {

    /** Class_Part 1: Declarations. */

    /** Declared Variable 1: Item. */
    private T[] maxArrayDeque;

    /** Declared Variable 2: Size. */
    private int size;

    /** Declared Variable 3: nextFirst. */
    private int nextFirst;

    /** Declared Variable 4: nextLast. */
    private int nextLast;

    /** Declared Magic Numbers. */
    private static final int MAX_ARRAY_LENGTH = 8;

    private Comparator<T> defaultComparator;


    /** Class_Part 2: Assignment.
     * Creates an empty max array deque. */
    public MaxArrayDeque(Comparator<T> c) {
        maxArrayDeque = (T[]) new Object[MAX_ARRAY_LENGTH];
        size = 0;
        nextFirst = 0;
        nextLast = 1;
        defaultComparator = c;
    }

    /** Class_Part 3: (Extra) Methods. */


    /** Extra Method 1: max()
     * Returns the maximum element in the deque as governed by the previously given Comparator.
     * If the MaxArrayDeque is empty, simply return null. */
    public T max() {
        if (this == null) {
            return null;
        }
        T maxItem = get(0);
        for (T i : this) {
            int cmp = defaultComparator.compare(i, maxItem);
            if (cmp > 0) {
                maxItem = i;
            }
        }
        return maxItem;
    }

    /** Extra Method 2: max(Comparator<T> c)
     * Returns the maximum element in the deque as governed by the parameter Comparator c.
     * If the MaxArrayDeque is empty, simply return null. */
    public T max(Comparator<T> c) {
        if (this == null) {
            return null;
        }
        T maxItem = get(0);
        for (T i : this) {
            int cmp = c.compare(i, maxItem);
            if (cmp > 0) {
                maxItem = i;
            }
        }
        return maxItem;
    }
}
