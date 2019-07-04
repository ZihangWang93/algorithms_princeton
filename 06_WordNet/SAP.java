/* *****************************************************************************
 *  Name: Zihang Wang
 *  Date: 07/03/2019
 *  Description: Shortest Ancestral Path Data Type
 **************************************************************************** */

import edu.princeton.cs.algs4.BreadthFirstDirectedPaths;
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class SAP {
    private final Digraph G;

    public SAP(Digraph G) {

        if (G == null) {
            throw new IllegalArgumentException("the input is null");
        }
        this.G = new Digraph(G);
    }

    public int length(int v, int w) {
        int[] result = bfsSearch(v, w);
        return result[1];
    }

    public int ancestor(int v, int w) {
        int[] result = bfsSearch(v, w);
        return result[0];

    }

    public int length(Iterable<Integer> v, Iterable<Integer> w) {
        if (v == null || w == null) {
            throw new IllegalArgumentException("The input is null");
        }
        int[] result = bfsSearch(v, w);
        return result[1];
    }

    public int ancestor(Iterable<Integer> v, Iterable<Integer> w) {
        if (v == null || w == null) {
            throw new IllegalArgumentException("The input is null");
        }
        int[] result = bfsSearch(v, w);
        return result[0];
    }

    private int[] bfsSearch(int v, int w) {
        int ancestor = 0;
        int dis = Integer.MAX_VALUE;
        BreadthFirstDirectedPaths bfsV = new BreadthFirstDirectedPaths(G, v);
        BreadthFirstDirectedPaths bfsW = new BreadthFirstDirectedPaths(G, w);

        for (int i = 0; i < G.V(); i++) {
            if (bfsV.hasPathTo(i) && bfsW.hasPathTo(i) && bfsV.distTo(i) + bfsW.distTo(i) < dis) {
                dis = bfsV.distTo(i) + bfsW.distTo(i);
                ancestor = i;
            }
        }

        if (dis == Integer.MAX_VALUE) {
            ancestor = -1;
            dis = -1;
        }

        int[] result = new int[2];
        result[0] = ancestor;
        result[1] = dis;

        return result;
    }

    private int[] bfsSearch(Iterable<Integer> v, Iterable<Integer> w) {
        int ancestor = 0;
        int dis = Integer.MAX_VALUE;
        BreadthFirstDirectedPaths bfsV = new BreadthFirstDirectedPaths(G, v);
        BreadthFirstDirectedPaths bfsW = new BreadthFirstDirectedPaths(G, w);

        for (int i = 0; i < G.V(); i++) {
            if (bfsV.hasPathTo(i) && bfsW.hasPathTo(i) && bfsV.distTo(i) + bfsW.distTo(i) < dis) {
                dis = bfsV.distTo(i) + bfsW.distTo(i);
                ancestor = i;
            }
        }

        if (dis == Integer.MAX_VALUE) {
            ancestor = -1;
            dis = -1;
        }

        int[] result = new int[2];
        result[0] = ancestor;
        result[1] = dis;

        return result;
    }


    public static void main(String[] args) {
        In in = new In(args[0]);
        Digraph G = new Digraph(in);
        SAP sap = new SAP(G);
        while (!StdIn.isEmpty()) {
            int v = StdIn.readInt();
            int w = StdIn.readInt();
            int length = sap.length(v, w);
            int ancestor = sap.ancestor(v, w);
            StdOut.printf("length = %d, ancestor = %d\n", length, ancestor);
        }
    }
}
