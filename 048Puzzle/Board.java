/* *****************************************************************************
 *  Name: Zihang Wang
 *  Date: 06/19/2019
 *  Description: 8 puzzle representation
 **************************************************************************** */

import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdRandom;

import java.util.Arrays;

public class Board {
    private int[] board;
    private int dim;
    private int blankPos;

    public Board(int[][] blocks) {
        this.dim = blocks[0].length;
        board = new int[dim * dim];

        for (int i = 0; i < dim; i++) {
            for (int j = 0; j < dim; j++) {
                board[i * dim + j] = blocks[i][j];

                if (board[i * dim + j] == 0) {
                    blankPos = i * dim + j;
                }
            }
        }
    }

    public int dimension() {
        return dim;
    }

    public int hamming() {
        int ham = 0;
        for (int i = 0; i < dim * dim; i++) {
            if (board[i] != 0 && board[i] != i + 1) {
                ham += 1;
            }
        }

        return ham;
    }

    public int manhattan() {
        int mnht = 0;
        for (int i = 0; i < dim * dim; i++) {
            if (board[i] != 0) {
                int goalRow = i / dim + 1;
                int goalCol = i % dim + 1;
                int actRow = ((board[i] - 1) / dim + 1);
                int actCol = (board[i] - 1) % dim + 1;
                mnht += Math.abs(goalRow - actRow) + Math.abs(goalCol - actCol);
            }
        }
        return mnht;
    }

    public boolean isGoal() {
        for (int i = 0; i < dim * dim - 1; i++) {
            if (board[i] != i + 1) {
                return false;
            }
        }
        return true;
    }

    public Board twin() {
        int[] twin = this.board.clone();
        int swtPos1 = StdRandom.uniform(dim * dim);
        while (twin[swtPos1] == 0) {
            swtPos1 = StdRandom.uniform(dim * dim);
        }

        int swtPos2 = StdRandom.uniform(dim * dim);
        while (twin[swtPos2] == 0 || swtPos2 == swtPos1) {
            swtPos2 = StdRandom.uniform(dim * dim);
        }

        int temp = twin[swtPos1];
        twin[swtPos1] = twin[swtPos2];
        twin[swtPos2] = temp;

        int[][] twin2D = new int[dim][dim];
        for (int i = 0; i < dim; i++) {
            for (int j = 0; j < dim; j++) {
                twin2D[i][j] = twin[i * dim + j];
            }
        }

        return new Board(twin2D);
    }

    public boolean equals(Object y) {
        if (y.getClass() != this.getClass()) {
            return false;
        }

        Board that = (Board) y;
        if (!Arrays.equals(this.board, that.board)) {
            return false;
        }

        return true;
    }

    public Iterable<Board> neighbors() {
        Stack<Board> stackBoard = new Stack<>();
        if ((blankPos + 1) % dim != 0) {
            stackBoard.push(swap(blankPos + 1));
        }

        if (blankPos / dim != 0) {
            stackBoard.push(swap(blankPos - dim));
        }

        if (blankPos % dim != 0) {
            stackBoard.push(swap(blankPos - 1));
        }

        if (blankPos / dim != dim - 1) {
            stackBoard.push(swap(blankPos + dim));
        }

        return stackBoard;
    }

    private Board swap(int i) {
        int[] copyBoard = this.board.clone();
        copyBoard[blankPos] = copyBoard[i];
        copyBoard[i] = 0;

        int[][] board2D = new int[dim][dim];
        for (int m = 0; m < dim; m++) {
            for (int n = 0; n < dim; n++) {
                board2D[m][n] = copyBoard[m * dim + n];
            }
        }
        return new Board(board2D);
    }

    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(dim + "\n");

        for (int i = 0; i < dim; i++) {
            for (int j = 0; j < dim; j++) {
                s.append(board[i * dim + j] + "  ");
            }
            s.append("\n");
        }
        return s.toString();
    }

    public static void main(String[] args) {

    }
}
