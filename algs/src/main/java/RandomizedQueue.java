import java.util.NoSuchElementException;
import java.util.Iterator;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private int n;
    private int first;
    private int last;
    private Item[] q;

    public RandomizedQueue() {
        q = (Item[]) new Object[2];
        first = 0;
        last = 0;
        n = 0;
    }

    public boolean isEmpty() {
        return n == 0;
    }

    public int size() {
        return n;
    }

    private void resize(int capacity) {
        assert capacity >= n;
        Item[] temp = (Item[]) new Object[capacity];
        for (int i = 0; i < n; i++) {
            temp[i] = q[(first + i) % q.length];
        }
        q = temp;
        first = 0;
        last = n;
    }

    public void enqueue(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }
        if (n == q.length)
            resize(2 * q.length); // double size of array if necessary
        q[last++] = item; // add item
        if (last == q.length)
            last = 0; // wrap-around
        n++;
    }

    private void swap(int i, int j) {
        if (i == j) {
            return;
        }
        Item tmp = q[i];
        q[i] = q[j];
        q[j] = tmp;
    }

    private int getIndex() {
        int idx = StdRandom.uniform(n);
        return (first + idx) % q.length;
    }

    public Item dequeue() {
        if (isEmpty())
            throw new NoSuchElementException("Queue underflow");
        swap(first, getIndex());
        Item item = q[first];
        q[first] = null; // to avoid loitering
        n--;
        first++;
        if (first == q.length)
            first = 0; // wrap-around
        // shrink size of array if necessary
        if (n > 0 && n == q.length / 4)
            resize(q.length / 2);
        return item;
    }

    public Item sample() {
        if (isEmpty())
            throw new NoSuchElementException("Queue underflow");
        return q[getIndex()];
    }

    public Iterator<Item> iterator() {
        return new ArrayIterator();
    }

    private class ArrayIterator implements Iterator<Item> {
        private RandomizedQueue<Item> innerQueue;

        public ArrayIterator() {
            innerQueue = new RandomizedQueue<Item>();
            for (int i = 0; i < n; i++) {
                innerQueue.enqueue(q[(first + i) % q.length]);
            }
        }

        public boolean hasNext() {
            return innerQueue.size() > 0;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

        public Item next() {
            if (!hasNext())
                throw new NoSuchElementException();
            return innerQueue.dequeue();
        }
    }

    public static void main(String[] args) {
        RandomizedQueue<Integer> q = new RandomizedQueue<Integer>();

        q.enqueue(1);
        q.enqueue(2);
        q.enqueue(3);
        q.enqueue(4);

        for (Integer i : q) {
            StdOut.println(i);
        }
        StdOut.println("--------------");

        int size = q.size();
        for (int i = 0; i < size; i++) {
            StdOut.println(q.sample());
        }
        StdOut.println("--------------");
        for (int i = 0; i < size; i++) {
            StdOut.println(q.dequeue());
        }

    }
}