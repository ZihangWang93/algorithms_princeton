/* *****************************************************************************
 *  Name: Zihang Wang
 *  Date: 2019/06/08
 *  Description: Permutation Client
 **************************************************************************** */

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class Permutation {

    public static void main(String[] args) {
        int numToRead = Integer.parseInt(args[0]);
        RandomizedQueue<String> deque = new RandomizedQueue<>();

        while (!StdIn.isEmpty()) {
            deque.enqueue(StdIn.readString());
        }

        for (int i = 0; i < numToRead; i++) {
            int index = StdRandom.uniform(deque.size());
            StdOut.println(deque.dequeue());
        }

    }
}
