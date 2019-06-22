/* *****************************************************************************
 *  Name: Zihang Wang
 *  Date: 2019/06/21
 *  Description: Solver for the 8puzzle game
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

public class Solver {

    private class SearchNode implements Comparable<SearchNode> {
        private int numMove;
        private Board board;
        private SearchNode predecessor;

        public SearchNode(Board board, int n, SearchNode predecessor) {
            this.numMove = n;
            this.board = board;
            this.predecessor = predecessor;
        }

        public int compareTo(SearchNode that) {
            return this.numMove + this.board.manhattan() - that.numMove - that.board.manhattan();
        }
    }

    private MinPQ<SearchNode> pqSrchNd;
    private boolean isSolvable;
    private int numMove;

    public Solver(Board initial) {
        if (initial == null) {
            throw new NullPointerException("input is null");
        }
        SearchNode srcNd = new SearchNode(initial, 0, null);
        pqSrchNd = new MinPQ<SearchNode>();
        pqSrchNd.insert(srcNd);
        numMove = 0;

        SearchNode twinSrcNd = new SearchNode(initial.twin(), 0, null);
        MinPQ<SearchNode> pqTwSrcNd = new MinPQ<>();
        pqTwSrcNd.insert(twinSrcNd);

        if (srcNd.board.isGoal()) {
            isSolvable = true;
        }

        if (twinSrcNd.board.isGoal()) {
            isSolvable = false;
        }

        while (!pqSrchNd.min().board.isGoal() && !pqTwSrcNd.min().board.isGoal()) {
            SearchNode pre = pqSrchNd.delMin();
            SearchNode twPre = pqTwSrcNd.delMin();
            numMove = pre.numMove + 1;
            for (Board b : pre.board.neighbors()) {
                if (pre.predecessor == null || !b.equals(pre.predecessor.board)) {
                    pqSrchNd.insert(new SearchNode(b, numMove, pre));
                }
            }

            for (Board twb : twPre.board.neighbors()) {
                if (twPre.predecessor == null || !twb.equals(twPre.predecessor.board)) {
                    pqTwSrcNd.insert(new SearchNode(twb, numMove, twPre));
                }
            }

            if (pqSrchNd.min().board.isGoal()) {
                isSolvable = true;
            }

            if (pqTwSrcNd.min().board.isGoal()) {
                isSolvable = false;
            }
        }
    }

    public boolean isSolvable() {
        return isSolvable;
    }

    public int moves() {
        return pqSrchNd.min().numMove;
    }

    public Iterable<Board> solution() {
        if (!isSolvable) {
            return null;
        }

        Stack<Board> solution = new Stack<>();
        SearchNode s = pqSrchNd.min();
        while (s != null) {
            solution.push(s.board);
            s = s.predecessor;
        }
        return solution;
    }


    public static void main(String[] args) {
        // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] blocks = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }
}
