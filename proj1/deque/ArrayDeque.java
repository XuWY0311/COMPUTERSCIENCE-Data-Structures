package deque;
import java.util.Iterator;

public class ArrayDeque<T> implements Deque<T>, Iterable<T> {

    /** Class_Part 1: Declarations. */

    /** Declared Variable 1: Item. */
    private T[] arrayDeque;

    /** Declared Variable 2: Size. */
    private int size;

    /** Declared Variable 3: nextFirst. */
    private int nextFirst;

    /** Declared Variable 4: nextLast. */
    private int nextLast;

    /** Declared Magic Numbers. */
    private static final int ARRAY_LENGTH = 8;
    private static final int SIZE_BOUNDARY = 16;
    private static final int SIZE_MULTIPLIER = 2;



    /** Class_Part 2: Assignment.
     * Creates an empty array deque. */
    public ArrayDeque() {
        arrayDeque = (T[]) new Object[ARRAY_LENGTH];
        size = 0;
        nextFirst = 0;
        nextLast = 1;
    }

    /** Class_Part 3: Methods. */

    /** Method 1: addFirst(T item)✔
     * Adds an item of type T to the front of the deque.
     * You can assume that item is never null.*/
    public void addFirst(T item) {
        int oldLength = arrayDeque.length;
        if (size == arrayDeque.length) {
            resize(size, "add");
        }
        arrayDeque[nextFirst] = item;
        if (nextFirst > 0) {
            nextFirst = nextFirst - 1;
        } else {
            if (size == oldLength) {
                nextFirst = arrayDeque.length - 1;
            } else {
                nextFirst = oldLength - 1;
            }
        }
        size = size + 1;
    }


    /** Method 2: addLast(T item)✔
     * Adds an item of type T to the back of the deque. You can assume that item is never null.*/
    public void addLast(T item) {
        int oldLength = arrayDeque.length;
        if (size == arrayDeque.length) {
            resize(size, "add");
        }
        arrayDeque[nextLast] = item;
        if (nextLast < oldLength - 1) {
            nextLast = nextLast + 1;
        } else {
            if (size == oldLength) {
                nextLast = nextLast + 1;
            } else {
                nextLast = 0;
            }
        }
        size = size + 1;
    }

    /** Method 3: isEmpty()✔. */

    /** Method 4: size()✔
     * Returns the number of items in the deque.*/
    public int size() {
        return size;
    }

    /** Method 5: resize(int size, String indicator)✔
     * Returns the number of items in the deque.*/
    private void resize(int originalSize, String indicator) {
        int capacity;
        if (indicator.equals("add")) {
            if (originalSize < SIZE_BOUNDARY) {
                capacity = SIZE_BOUNDARY;
            } else {
                capacity = originalSize * SIZE_MULTIPLIER;
            }
            T[] rotatedArray = rotate(arrayDeque, getFirstIndex());
            T[] resizedArray = (T[]) new Object[capacity];
            System.arraycopy(rotatedArray, 0, resizedArray, 0, originalSize);
            arrayDeque = resizedArray;
            nextFirst = capacity - 1;
            nextLast = originalSize;
        }
        if (indicator.equals("remove")) {
            T[] rotatedArray = rotate(arrayDeque, getFirstIndex());
            capacity = originalSize;
            T[] resizedArray = (T[]) new Object[capacity];
            System.arraycopy(rotatedArray, 0, resizedArray, 0, originalSize);
            arrayDeque = resizedArray;
            nextFirst = originalSize - 1;
            nextLast = 0;
        }
    }

    /** Method 6: getFirstIndex()✔
     * This function is the first helper function of resize(int size, String indicator).
     * Returns the position of first item in the deque.*/
    private int getFirstIndex() {
        if (nextFirst == arrayDeque.length - 1) {
            return 0;
        } else {
            return nextFirst + 1;
        }
    }

    /** Method 7: getLastIndex()✔
     * Returns the position of last item in the deque.*/
    private int getLastIndex() {
        if (nextLast == 0) {
            return arrayDeque.length - 1;
        } else {
            return nextLast - 1;
        }
    }

    /** Method 8: rotate(T[] oldArray, int head)✔
     * This function is the second helper function of resize(int size, String indicator).
     * Before resize the old array, this function returns a rotated version of old array, let us say "rotated array".
     * Then we get an array that:
     * rotatedArray[0] is the head of the deque;
     * rotatedArray[size - 1] is the tail of the deque;
     * Finally, we resize the rotated array to a new array.
     */
    private T[] rotate(T[] oldArray, int head) {
        int rightShift = head;
        T[] rotatedArray = (T[]) new Object[oldArray.length];
        for (int i = 0; i < oldArray.length; i++) {
            int newIndex;
            if (i - rightShift >= 0) {
                newIndex = (i - rightShift) % oldArray.length;
            } else {
                newIndex = arrayDeque.length - rightShift + i;
            }
            rotatedArray[newIndex] = oldArray[i];
        }
        return rotatedArray;
    }

    /** Method 9: printDeque()✔
     * Prints the items in the deque from first to last, separated by a space.
     * Once all the items have been printed, print out a new line.*/
    public void printDeque() {
        for (int i = 0; i <= size; i = i + 1) {
            System.out.print(get(i));
            System.out.print(" ");
        }
        System.out.println(" ");
    }

    /** Method 10: removeFirst()✔
     * Removes and returns the item at the front of the deque. If no such item exists, returns null.*/
    public T removeFirst() {
        if (size == 0) {
            return null;
        } else {
            resize(size, "remove");
            T originalFirstValue = arrayDeque[0];
            arrayDeque[0] = null;
            nextFirst = 0;
            size = size - 1;
            return originalFirstValue;
        }
    }

    /** Method 11: removeLast()✔
     * Removes and returns the item at the back of the deque. If no such item exists, returns null.*/
    public T removeLast() {
        if (size == 0) {
            return null;
        } else {
            resize(size, "remove");
            T originalLastValue = arrayDeque[size - 1];
            arrayDeque[size - 1] = null;
            nextLast = size - 1;
            size = size - 1;
            return originalLastValue;
        }
    }

    /** Method 12: get(int index)✔
     * Gets the item at the given index, where 0 is the front, 1 is the next item, and so forth.
     * If no such item exists, returns null. Must not alter the deque!*/
    public T get(int i) {
        /** Case 1. */
        if (nextFirst != arrayDeque.length - 1) {
            if (i + nextFirst + 1 <= arrayDeque.length - 1) {
                return arrayDeque[i + nextFirst + 1];
            } else {
                return arrayDeque[i + nextFirst + 1 - arrayDeque.length];
            }
        } else {
            /** Case 2. */
            return arrayDeque[i];
        }
    }

    /** Method 13: equals(Object o)✔
     * Returns whether or not the parameter o is equal to the deque.
     * o is considered equal if
     * it is a deque
     * and if it contains the same contents (as determined by the generic T’s equals method) in the same order. */
    public boolean equals(Object o) {
        boolean conditionOne = (o instanceof Deque<?>);
        if (!conditionOne) {
            return false;
        } else {
            boolean conditionTwo = (this.size == ((Deque<?>) o).size());
            if (!conditionTwo) {
                return false;
            } else {
                for (int i = 0; i < size; i = i + 1) {
                    if (!(this.get(i).equals(((Deque<?>) o).get(i)))) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    /** Method 14: iterator(). */
    private class AdIterator implements Iterator<T> {
        private int wizPos;

        public AdIterator() {
            wizPos = 0;
        }

        public boolean hasNext() {
            return wizPos < size;
        }

        public T next() {
            T returnItem = get(wizPos);
            wizPos += 1;
            return returnItem;
        }
    }
    public Iterator<T> iterator() {
        return new AdIterator();
    }

}

