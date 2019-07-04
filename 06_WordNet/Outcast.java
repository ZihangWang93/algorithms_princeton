/* *****************************************************************************
 *  Name: Zihang Wang
 *  Date: 07/04/2019
 *  Description: Calculate the most remote word from wordNet
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class Outcast {
    private WordNet wordNet;

    public Outcast(WordNet wordNet) {
        this.wordNet = wordNet;
    }

    public String outcast(String[] nouns) {
        int dis = 0;
        int index = 0;
        int num = nouns.length;

        for (int i = 0; i < num; i++) {
            int totalDis = 0;
            for (int j = 0; j < num; j++) {
                if (j != i) {
                    totalDis += wordNet.distance(nouns[i], nouns[j]);
                }
            }
            if (totalDis > dis) {
                dis = totalDis;
                index = i;
            }

        }
        return nouns[index];
    }

    public static void main(String[] args) {
        WordNet wordnet = new WordNet(args[0], args[1]);
        Outcast outcast = new Outcast(wordnet);
        for (int t = 2; t < args.length; t++) {
            In in = new In(args[t]);
            String[] nouns = in.readAllStrings();
            StdOut.println(args[t] + ": " + outcast.outcast(nouns));
        }

    }
}
