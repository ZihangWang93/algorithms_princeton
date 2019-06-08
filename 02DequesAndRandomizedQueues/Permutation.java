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
        String[] allStrings = StdIn.readAllStrings();
        int numTotal = allStrings.length;
        Deque<String> deque = new Deque<>();
        if (numToRead > numTotal) {
            throw new IllegalArgumentException(" the input is bigger than the total length ");
        }
        for (int i = 0; i < numToRead; i++) {
            int index = StdRandom.uniform(numTotal);
            deque.addLast(allStrings[index]);
        }

        for (String s : deque) {
            StdOut.println(s);
        }

    }
}
