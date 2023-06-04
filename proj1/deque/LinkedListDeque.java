package deque;
import java.util.Iterator;

public class LinkedListDeque<T> implements Deque<T>, Iterable<T> {

    /** Class_Part 1: Declarations. */

    /** Declared Variable 1: Node. */
    private class TNode {
        private TNode prev;
        private T item;
        private TNode next;

        public TNode(TNode p, T i, TNode n) {
            prev = p;
            item = i;
            next = n;
        }
    }
    /** Declared Variable 2: Sentinel. */
    private TNode sentinel;

    /** Declared Variable 3: Size. */
    private int size;



    /** Class_Part 2: Assignments.
     * Creates an empty linked list deque. */
    public LinkedListDeque() {
        /** Assigned Variable 1: Sentinel. */
        sentinel = new TNode(null, null, null);

        /** Assigned Variable 2: Size. */
        size = 0;
    }

    /** Class_Part 3: Methods. */

    /** Method 1: addFirst(T item)✔
     * Adds an item of type T to the front of the deque. You can assume that item is never null.*/
    public void addFirst(T item) {
        if (size == 0) {
            TNode first = new TNode(sentinel, item, sentinel);
            sentinel.prev = first;
            sentinel.next = first;
            size += 1;
        } else {
            TNode originalFirst = sentinel.next;
            TNode first = new TNode(sentinel, item, originalFirst);
            sentinel.next = first;
            originalFirst.prev = first;
            size += 1;
        }
    }

    /** Method 2: addLast(T item)✔
     * Adds an item of type T to the back of the deque. You can assume that item is never null.*/
    public void addLast(T item) {
        if (size == 0) {
            TNode last = new TNode(sentinel, item, sentinel);
            sentinel.prev = last;
            sentinel.next = last;
            size += 1;
        } else {
            TNode originalLast = sentinel.prev;
            TNode last = new TNode(originalLast, item, sentinel);
            sentinel.prev = last;
            originalLast.next = last;
            size += 1;
        }
    }

    /** Method 3: isEmpty()✔. */

    /** Method 4: size()✔
     * Returns the number of items in the deque.*/
    public int size() {
        return size;
    }

    /** Method 5: printDeque()✔
     * Prints the items in the deque from first to last, separated by a space.
     * Once all the items have been printed, print out a new line.*/
    public void printDeque() {
        for (int i = 0; i <= size; i = i + 1) {
            System.out.print(get(i));
            System.out.print(" ");
        }
        System.out.println(" ");
    }

    /** Method 6: removeFirst()✔
     * Removes and returns the item at the front of the deque. If no such item exists, returns null.*/
    public T removeFirst() {
        if (size == 0) {
            return null;
        } else {
            TNode originalFirst = sentinel.next;
            sentinel.next = originalFirst.next;
            originalFirst.next.prev = sentinel;
            size = size - 1;
            return originalFirst.item;
        }
    }

    /** Method 7: removeLast()✔
     * Removes and returns the item at the back of the deque. If no such item exists, returns null.*/
    public T removeLast() {
        if (size == 0) {
            return null;
        } else {
            TNode originalLast = sentinel.prev;
            originalLast.prev.next = sentinel;
            sentinel.prev = originalLast.prev;
            size = size - 1;
            return originalLast.item;
        }
    }

    /** Method 8: get(int index)✔
     * Gets the item at the given index, where 0 is the front, 1 is the next item, and so forth.
     * If no such item exists, returns null. Must not alter the deque!*/
    public T get(int index) {
        T targetItem = null;
        TNode targetTNode = sentinel.next;
        if (index >= size) {
            return null;
        }
        if (index == 0) {
            return targetTNode.item;
        }
        if (index > 0) {
            for (int i = 1; i <= index; i = i + 1) {
                targetTNode = targetTNode.next;
                targetItem = targetTNode.item;
            }
        }
        return targetItem;
    }

    /** Method 9: equals(Object o)✔
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

    /** Method 10: getRecursive(int index)✔
     * Same as get, but uses recursion. */
    public T getRecursive(int index) {
        if (index >= size) {
            return null;
        }
        if (index == 0) {
            return sentinel.next.item;
        } else {
            int indicator = 0;
            TNode startNode = sentinel.next;
            return getRecursiveHelper(index, indicator, startNode);
        }
    }

    /** Method 11: getRecursiveHelper(int index, int count, tNode ptr) */
    private T getRecursiveHelper(int index, int indicator, TNode startNode) {
        if (index == indicator) {
            return startNode.item;
        }
        return getRecursiveHelper(index, indicator + 1, startNode.next);
    }

    /** Method 12: iterator(). */
    private class IIDIterator implements Iterator<T> {
        private int wizPos;

        public IIDIterator() {
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
        return new IIDIterator();
    }

}
