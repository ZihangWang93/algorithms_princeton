/* *****************************************************************************
 *  Name: Zihang Wang
 *  Date: 2019/06/06
 *  Description: Implementation RandomizedQuene using array lists
 **************************************************************************** */

import edu.princeton.cs.algs4.StdRandom;

import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private Item[] rQueue;
    private int size;
    private int last;

    public RandomizedQueue() {
        size = 2;
        rQueue = (Item[]) new Object[size];
        last = -1;
    }

    public boolean isEmpty() {
        return last == -1;
    }

    public int size() {
        return last + 1;
    }

    public void enqueue(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("enque a null item");
        }
        if (last == size - 1) {
            incrSize();
            size *= 2;
        }
        rQueue[++last] = item;
    }

    public Item dequeue() {
        if (isEmpty()) {
            throw new NoSuchElementException("This is an empty dequeue");
        }
        if (last + 1 == size / 4) {
            decrSize();
            size /= 2;
        }
        int index = StdRandom.uniform(last + 1);
        Item toRmove = rQueue[index];
        for (int i = index; i < last; i++) {
            rQueue[i] = rQueue[i + 1];
        }
        rQueue[last--] = null;
        return toRmove;
    }

    public Item sample() {
        if (isEmpty()) {
            throw new NoSuchElementException("The queue is empty");
        }
        int index = StdRandom.uniform(last + 1);
        return rQueue[index];
    }

    public Iterator<Item> iterator() {
        return new RQueueIterator();
    }

    private class RQueueIterator implements Iterator<Item> {
        int currentIndex = -1;

        public boolean hasNext() {
            return currentIndex != last;
        }

        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException("There is no more to return");
            }
            return rQueue[++currentIndex];
        }

        public void remove() {
            throw new UnsupportedOperationException("There is no remove method in Iterator");
        }
    }

    private void incrSize() {
        Item[] rQ = (Item[]) new Object[size * 2];
        for (int i = 0; i <= last; i++) {
            rQ[i] = rQueue[i];
        }
        rQueue = rQ;
    }

    private void decrSize() {
        Item[] rQ = (Item[]) new Object[size / 2];
        for (int i = 0; i <= last; i++) {
            rQ[i] = rQueue[i];
        }
        rQueue = rQ;
    }


    public static void main(String[] args) {
        RandomizedQueue<Integer> rq = new RandomizedQueue<Integer>();
        for (int i = 0; i <= 10; i++) {
            rq.enqueue(i);
            System.out.println(Arrays.toString(rq.rQueue));
            System.out.println(rq.size);
            /*
            for (Integer j : rq.rQueue) {
                System.out.print(j);
                System.out.println(rq.size);
                System.out.println(" ");
            }
            */
        }

    }
}
