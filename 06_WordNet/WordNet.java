/* *****************************************************************************
 *  Name: Zihang Wang
 *  Date: 07/03/2019
 *  Description: Build the word net class
 **************************************************************************** */

import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;

import java.util.ArrayList;
import java.util.TreeMap;

public class WordNet {

    private TreeMap<String, ArrayList<Integer>> wordNetString;
    private SAP s;

    public WordNet(String synsets, String hypernyms) {
        int count = 0;
        wordNetString = new TreeMap<String, ArrayList<Integer>>();

        if (synsets == null || hypernyms == null) {
            throw new IllegalArgumentException("input is null");
        }

        In inSyn = new In(synsets);
        In inHyp = new In(hypernyms);

        while (inSyn.hasNextLine()) {
            String[] fields1 = inSyn.readLine().split(",", 3);
            if (fields1[0] != null && fields1[1] != null) {
                String[] syn = fields1[1].split(" ");
                for (String st : syn) {
                    if (wordNetString.get(st) == null) {
                        ArrayList<Integer> temp = new ArrayList<>();
                        temp.add(Integer.parseInt(fields1[0]));
                        wordNetString.put(st, temp);
                    }

                    else {
                        ArrayList<Integer> temp = wordNetString.get(st);
                        temp.add(Integer.parseInt(fields1[0]));
                        wordNetString.put(st, temp);
                    }
                }
            }

            count++;
        }

        Digraph G = new Digraph(count);

        while (inHyp.hasNextLine()) {
            String[] fields2 = inHyp.readLine().split(",");
            for (int i = 1; i < fields2.length; i++) {
                G.addEdge(Integer.parseInt(fields2[0]), Integer.parseInt(fields2[i]));
            }
        }

        s = new SAP(G);
    }

    public Iterable<String> nouns() {
        return wordNetString.keySet();
    }

    public boolean isNoun(String word) {
        if (word == null) {
            throw new IllegalArgumentException("Input is null");
        }
        return wordNetString.containsKey(word);
    }

    public int distance(String nounA, String nounB) {
        if (nounA == null || nounB == null || !isNoun(nounA) || !isNoun(nounB)) {
            throw new IllegalArgumentException("input is null or the word is not in the wordNet");
        }
        return s.length(wordNetString.get(nounA), wordNetString.get(nounB));
    }

    public String sap(String nounA, String nounB) {
        StringBuilder strb = new StringBuilder();
        if (nounA == null || nounB == null || !isNoun(nounA) || !isNoun(nounB)) {
            throw new IllegalArgumentException("input is null or the word is not in the wordNet");
        }

        int index = s.ancestor(wordNetString.get(nounA), wordNetString.get(nounB));

        for (String st : nouns()) {
            if (wordNetString.get(st).contains(index)) {
                strb.append(st);
            }
        }

        return strb.toString();
    }

    public static void main(String[] args) {

    }
}
