/* *****************************************************************************
 *  Name: Zihang Wang
 *  Date: 07/11/2019
 *  Description: BoggleSolver Class for a boggle game
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.StdOut;

import java.util.HashSet;

public class BoggleSolver {

    private TrieSETR dictionary;
    private HashSet<String> words;
    private final int[] scores = new int[] { 0, 0, 0, 1, 1, 2, 3, 5 };
    private int col;
    private int row;

    public BoggleSolver(String[] dictionary) {
        this.dictionary = new TrieSETR();
        for (int i = 0; i < dictionary.length; i++) {
            this.dictionary.add(dictionary[i]);
        }
    }

    public Iterable<String> getAllValidWords(BoggleBoard board) {
        this.col = board.cols();
        this.row = board.rows();
        this.words = new HashSet<>();

        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                boolean[][] isVisited = new boolean[row][col];
                StringBuilder s = new StringBuilder();
                dfs(i, j, board, s, isVisited);
            }
        }
        return this.words;
    }

    private boolean isExist(int i, int j) {
        if (i >= 0 && i < row && j >= 0 && j < col) {
            return true;
        }
        return false;
    }

    private void dfs(int i, int j, BoggleBoard board, StringBuilder s, boolean[][] isVisited) {
        Queue<int[]> next = new Queue<>();

        if (isExist(i - 1, j - 1)) {
            next.enqueue(new int[] { i - 1, j - 1 });
        }

        if (isExist(i - 1, j)) {
            next.enqueue(new int[] { i - 1, j });
        }

        if (isExist(i - 1, j + 1)) {
            next.enqueue(new int[] { i - 1, j + 1 });
        }

        if (isExist(i, j + 1)) {
            next.enqueue(new int[] { i, j + 1 });
        }

        if (isExist(i + 1, j + 1)) {
            next.enqueue(new int[] { i + 1, j + 1 });
        }

        if (isExist(i + 1, j)) {
            next.enqueue(new int[] { i + 1, j });
        }

        if (isExist(i + 1, j - 1)) {
            next.enqueue(new int[] { i + 1, j - 1 });
        }

        if (isExist(i, j - 1)) {
            next.enqueue(new int[] { i, j - 1 });
        }


        if ((board.getLetter(i, j) == 'Q')) {
            s.append("QU");
        }
        else {
            s.append(board.getLetter(i, j));
        }

        if (dictionary.containsPrefix(s.toString())) {
            isVisited[i][j] = true;
            while (!next.isEmpty()) {
                int[] index = next.dequeue();
                if (!isVisited[index[0]][index[1]]) {
                    dfs(index[0], index[1], board, s, isVisited);
                }
            }
            if (dictionary.contains(s.toString()) && s.length() >= 3) {
                this.words.add(s.toString());
            }

            if (s.length() >= 2 && s.charAt(s.length() - 2) == 'Q') {
                s.deleteCharAt(s.length() - 1);
                s.deleteCharAt(s.length() - 1);
                isVisited[i][j] = false;
            }
            else {
                s.deleteCharAt(s.length() - 1);
                isVisited[i][j] = false;
            }
        }
        else if (s.charAt(s.length() - 2) == 'Q') {
            s.deleteCharAt(s.length() - 1);
            s.deleteCharAt(s.length() - 1);
        }
        else {
            s.deleteCharAt(s.length() - 1);
        }
    }

    public int scoreOf(String word) {
        if (dictionary.contains(word)) {
            if (word.length() >= 8) {
                return 11;
            }
            return scores[word.length()];
        }
        return 0;
    }

    public static void main(String[] args) {
        In in = new In(args[0]);
        String[] dictionary = in.readAllStrings();
        BoggleSolver solver = new BoggleSolver(dictionary);
        BoggleBoard board = new BoggleBoard(args[1]);
        int score = 0;
        for (String word : solver.getAllValidWords(board)) {
            StdOut.println(word);
            score += solver.scoreOf(word);
        }
        StdOut.println("Score = " + score);
    }
}
