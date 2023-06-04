package bstmap;


import java.util.ArrayList;
import java.util.Set;
import java.util.Iterator;

public class BSTMap<K extends Comparable<K>, V> implements Map61B<K, V> {
    /** Part 1: Declarations. */
    private BSTNode root;
    private int size;
    private class BSTNode {
        private K key;
        private V value;
        private BSTNode left;
        private BSTNode right;
        public BSTNode(K k, V v) {
            key = k;
            value = v;
        }
    }
    public Iterator<K> iterator() {
        return new BSTMap.BSTIterator();
    }
    /** Part 2: Initiation. */
    public BSTMap() {
        clear();
    }

    public void clear() {
        this.root = null;
        this.size = 0;
    }

    /** Part 3: Methods. */

    /** Method 1: put(K key, V value). */

    public void put(K key, V value) {
        if (!containsKey(key)) {
            size += 1;
        }
        root = putHelper(key, value, root);
    }

    private BSTNode putHelper(K key, V val, BSTNode p) {
        if (p == null) {
            return new BSTNode(key, val);
        }
        int compareVal = key.compareTo(p.key);
        if (compareVal == 0) {
            p.value = val;
        }
        if (compareVal < 0) {
            p.left = putHelper(key, val, p.left);
        } else {
            p.right = putHelper(key, val, p.right);
        }
        return p;
    }

    /** Method 2: Iterator. */

    private class BSTIterator implements Iterator<K> {
        private ArrayList<BSTNode> keys;
        int index;

        public BSTIterator() {
            this.index = 0;
            this.keys = new ArrayList<>();
            gatherKeys(this.keys, root);
        }

        @Override
        public boolean hasNext() {
            return this.index < this.keys.size();
        }

        @Override
        public K next() {
            K returnVal = keys.get(this.index).key;
            this.index += 1;
            return returnVal;
        }
    }

    /** Method 3: ContainsKey(K key). */
    public boolean containsKey(K key) {
        return getHelper(key, this.root) != null;
    }

    /** Method 4: get(K key). */
    public V get(K key) {
        BSTNode n = getHelper(key, this.root);
        if (n == null) {
            return null;
        } else {
            return n.value;
        }
    }
    private BSTNode getHelper(K key, BSTNode p) {
        if (p == null) {
            return null;
        }
        int compareVal = key.compareTo(p.key);
        if (compareVal < 0) {
            return getHelper(key, p.left);
        } else if (compareVal > 0) {
            return getHelper(key, p.right);
        } else {
            return p;
        }
    }
    /** Method 5: size(). */
    public int size() {
        return size;
    }

    /** Method 6: printInOrder(). */
    private void gatherKeys(ArrayList<BSTNode> bst, BSTNode p) {
        if (root == null) {
            return;
        }
        gatherKeys(bst, p.left);
        bst.add(p);
        gatherKeys(bst, p.right);
    }
    public void printInOrder() {
        ArrayList<BSTNode> nodes = new ArrayList<>();
        gatherKeys(nodes, this.root);
        for (BSTNode n : nodes) {
            System.out.print(n.key);
            System.out.println(n.value);
        }
    }

    /** Other Methods. */
    public Set<K> keySet() {
        throw new UnsupportedOperationException("Unsupported");
    }

    public V remove(K key) {
        throw new UnsupportedOperationException("Unsupported");
    }

    public V remove(K key, V value) {
        throw new UnsupportedOperationException("Unsupported");
    }
}

