/* *****************************************************************************
 *  Name: Zihang Wang
 *  Date: 2019/06/06
 *  Description: Implementation Deque datastructure using arraylist.
 **************************************************************************** */

import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {
    private Node frontSentinel;
    private Node backSentinel;
    private int size;

    private class Node {
        Item item;
        Node previous;
        Node next;
    }

    public Deque() {
        frontSentinel = new Node();
        backSentinel = new Node();
        frontSentinel.next = backSentinel;
        backSentinel.previous = frontSentinel;
        size = 0;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public void addFirst(Item item) {
        if (item == null) {
            throw new IllegalArgumentException(" add null to the deque");
        }
        Node node = new Node();
        node.item = item;
        node.next = frontSentinel.next;
        frontSentinel.next.previous = node;
        frontSentinel.next = node;
        node.previous = frontSentinel;
        size++;
    }

    public void addLast(Item item) {
        if (item == null) {
            throw new IllegalArgumentException(" add null to the deque ");
        }
        Node node = new Node();
        node.item = item;
        node.previous = backSentinel.previous;
        backSentinel.previous.next = node;
        backSentinel.previous = node;
        node.next = backSentinel;
        size++;
    }

    public Item removeFirst() {
        if (isEmpty()) {
            throw new NoSuchElementException(" This is an empty deque ");
        }
        Node node = frontSentinel.next;
        frontSentinel.next.next.previous = frontSentinel;
        frontSentinel.next = frontSentinel.next.next;
        node.previous = null;
        node.next = null;
        size--;
        return node.item;
    }

    public Item removeLast() {
        if (isEmpty()) {
            throw new NoSuchElementException(" This is an empty deque ");
        }
        Node node = backSentinel.previous;
        backSentinel.previous.previous.next = backSentinel;
        backSentinel.previous = backSentinel.previous.previous;
        node.previous = null;
        node.next = null;
        size--;
        return node.item;
    }

    public Iterator<Item> iterator() {
        return new DequeIterator();
    }

    private class DequeIterator implements Iterator<Item> {
        private Node current = frontSentinel;

        @Override
        public boolean hasNext() {
            return current.next != backSentinel;
        }

        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException("There is no more items to return");
            }
            current = current.next;
            return current.item;
        }

        public void remove() {
            throw new UnsupportedOperationException("There is no such method for this iterator");
        }
    }


    public static void main(String[] args) {
        Deque<Integer> deque = new Deque<>();
        deque.addFirst(0);
        deque.removeFirst();
        System.out.println(deque.isEmpty());
        deque.addFirst(0);
        System.out.println(deque.isEmpty());
        for (int i = 1; i <= 5; i++) {
            deque.addFirst(i);
            deque.addLast(i);
            System.out.println(deque.size());
        }

        for (int j : deque) {
            System.out.print(j);
        }

        for (int i = 0; i <= 4; i++) {
            deque.removeLast();
            deque.removeFirst();
            for (int j : deque) {
                System.out.print(j);
            }
        }
    }
}
