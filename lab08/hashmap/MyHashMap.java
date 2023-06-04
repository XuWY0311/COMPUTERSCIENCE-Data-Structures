package hashmap;

import java.util.*;

/**
 *  A hash table-backed Map implementation. Provides amortized constant time
 *  access to elements via get(), remove(), and put() in the best case.
 *
 *  Assumes null keys will never be inserted, and does not resize down upon remove().
 *  @author YOUR NAME HERE
 */


public class MyHashMap<K, V> implements Map61B<K, V> {

    /**
     * Protected helper class to store key/value pairs
     * The protected qualifier allows subclass access
     */


    /* Instance Variables */
    private Collection<Node>[] buckets;
    private int size;
    private double loadFactor;
    private static final double DEFAULT_LOAD_FACTOR = 0.75;
    private static final int DEFAULT_SIZE_FACTOR = 16;
    private Set<K> keySet;
    // You should probably define some more!


    /**
     * Constructors
     */
    public MyHashMap() {
        this(DEFAULT_SIZE_FACTOR, DEFAULT_LOAD_FACTOR);
    }

    public MyHashMap(int initialSize) {
        this(initialSize, DEFAULT_LOAD_FACTOR);
    }

    /**
     * MyHashMap constructor that creates a backing array of initialSize.
     * The load factor (# items / # buckets) should always be <= loadFactor
     *
     * @param initialSize initial size of backing array
     * @param maxLoad     maximum load factor
     */
    public MyHashMap(int initialSize, double maxLoad) {
        keySet = new HashSet<>();
        buckets = createTable(initialSize);
        size = 0;
        loadFactor = maxLoad;
    }

    /**
     * Returns a new node to be placed in a hash table bucket
     */
    private Node createNode(K key, V value) {
        return new Node(key, value);
    }

    /**
     * Returns a data structure to be a hash table bucket
     * <p>
     * The only requirements of a hash table bucket are that we can:
     * 1. Insert items (`add` method)
     * 2. Remove items (`remove` method)
     * 3. Iterate through items (`iterator` method)
     * <p>
     * Each of these methods is supported by java.util.Collection,
     * Most data structures in Java inherit from Collection, so we
     * can use almost any data structure as our buckets.
     * <p>
     * Override this method to use different data structures as
     * the underlying bucket type
     * <p>
     * BE SURE TO CALL THIS FACTORY METHOD INSTEAD OF CREATING YOUR
     * OWN BUCKET DATA STRUCTURES WITH THE NEW OPERATOR!
     */
    protected Collection<Node> createBucket() {
        return new LinkedList<>();
    }

    /**
     * Returns a table to back our hash table. As per the comment
     * above, this table can be an array of Collection objects
     * <p>
     * BE SURE TO CALL THIS FACTORY METHOD WHEN CREATING A TABLE SO
     * THAT ALL BUCKET TYPES ARE OF JAVA.UTIL.COLLECTION
     *
     * @param tableSize the size of the table to create
     */
    private Collection<Node>[] createTable(int tableSize) {

        Collection<Node>[] table = new Collection[tableSize];
        for (int i = 0; i < tableSize; i++) {
            table[i] = createBucket();
        }
        return table;
    }

    // TODO: Implement the methods of the Map61B Interface below
    // Your code won't compile until you do so!

































    public void put(K key, V value) {
        Node node = getNode(key);
        if (node != null) {
            node.value = value;
            return;
        }
        if (((double) size) / buckets.length > loadFactor) {
            rebucket(buckets.length * 2);
        }
        size++;
        int idx = findBucket(key);
        Collection<Node> bucketList = buckets[idx];
        if (bucketList == null) {
            bucketList = createBucket();
            buckets[idx] = bucketList;
        }
        bucketList.add(createNode(key, value));
    }
    private void rebucket(int targetSize) {
        Collection<Node>[] newBuckets = createTable(targetSize);
        for (Collection<Node> c : this.buckets) {
            if (c == null) {
                continue;
            }
            for (Node n : c) {
                K key = n.key;
                int idx = findBucket(key, newBuckets.length);
                if (newBuckets[idx] == null) {
                    newBuckets[idx] = createBucket();
                }
                newBuckets[idx].add(getNode(key));
            }
        }
        buckets = newBuckets;
    }


    @Override
    public int size() {
        return size;
    }


    @Override
    public void clear() {
        size = 0;
        buckets = createTable(DEFAULT_SIZE_FACTOR);
    }


    private Node getNode(K key) {
        int idx = findBucket(key);
        Collection<Node> bucketList = buckets[idx];
        if (bucketList != null) {
            for (Node node : bucketList) {
                if (node.key.equals(key)) {
                    return node;
                }
            }
        }
        return null;
    }


    public V get(K key) {
        Node node = getNode(key);
        if (node == null) {
            return null;
        }
        return node.value;
    }

    private int findBucket(K key, int numBuckets) {
        return Math.floorMod(key.hashCode(), numBuckets);
    }
    private int findBucket(K key) {
        return findBucket(key, buckets.length);
    }


    @Override
    public V remove(K key) {
        throw new UnsupportedOperationException();
    }

    @Override
    public V remove(K key, V value) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Set<K> keySet() {
        throw new UnsupportedOperationException();
    }

    public Iterator<K> iterator() {
        throw new UnsupportedOperationException();
    }

    protected class Node {
        K key;
        V value;

        Node(K k, V v) {
            key = k;
            value = v;
        }
    }

    public boolean containsKey(K key) {
        return (getNode(key) != null);
    }

}
