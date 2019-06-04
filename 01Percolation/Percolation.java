/****************************************************************************
 *  Name:Zihang Wang
 *  Date:2019.05.31
 *  Description:Percolation data structure
 *****************************************************************************/

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private boolean[] id;
    private WeightedQuickUnionUF ufTop;
    private WeightedQuickUnionUF ufBottom;
    private int dim;                              // the dimension of the grid
    private int num;                              // the number of open sites

    // initialize a n-n grid
    public Percolation(int n) {
        if (n <= 0) {
            throw new java.lang.IllegalArgumentException(" Unexpecting Dimension Size");
        }

        id = new boolean[n * n + 2];
        id[0] = true;
        id[n * n + 1] = true;
        for (int i = 1; i <= n * n; i++) {
            id[i] = false;
        }
        dim = n;
        num = 0;

        ufTop = new WeightedQuickUnionUF(n * n + 2);
        ufBottom = new WeightedQuickUnionUF(n * n + 2);
    }

    // open the site if it is not opened yet
    public void open(int row, int col) {
        int index = xyTo1D(row, col);
        isValid(index);
        if (!id[index]) {
            id[index] = true;
            connectLeft(index);
            connectRight(index);
            connectUp(index);
            connectDown(index);
            num += 1;
        }
    }

    // check if specific site is open
    public boolean isOpen(int row, int col) {
        int index = xyTo1D(row, col);
        isValid(index);
        return id[index];
    }

    // check if specific site is full
    public boolean isFull(int row, int col) {
        int index = xyTo1D(row, col);
        isValid(index);
        return ufBottom.connected(index, 0);
    }

    // return the number of the open sites
    public int numberOfOpenSites() {
        return num;
    }

    // return if this system percolate
    public boolean percolates() {
        return ufTop.find(dim * dim + 1) == ufTop.find(ufBottom.find(0));
    }

    // helper method transfer a 2D (row, col) data into 1D data
    private int xyTo1D(int row, int col) {
        return (row - 1) * dim + col;
    }

    // helper method to verify the indice is valid or not
    private void isValid(int index) {
        if (index <= 0 || index > dim * dim) {
            throw new java.lang.IllegalArgumentException(
                    "Unexpected index less than 0 or bigger than n^2");
        }
    }

    // helper method to Union the surrouding indice
    private void connectLeft(int cur) {
        if (cur % dim != 1 && id[cur - 1]) {
            ufTop.union(cur, cur - 1);
            ufBottom.union(cur, cur - 1);
        }
    }

    private void connectRight(int cur) {
        if (cur % dim != 0 && id[cur + 1]) {
            ufTop.union(cur, cur + 1);
            ufBottom.union(cur, cur + 1);
        }
    }

    private void connectUp(int cur) {
        if (cur > dim * (dim - 1) && cur <= dim * dim) {
            ufTop.union(cur, dim * dim + 1);
        }
        else if (id[cur + dim]) {
            ufTop.union(cur, cur + dim);
            ufBottom.union(cur, cur + dim);
        }
    }

    private void connectDown(int cur) {
        if (cur > 0 && cur <= dim) {
            ufBottom.union(cur, 0);
        }
        else if (id[cur - dim]) {
            ufTop.union(cur, cur - dim);
            ufBottom.union(cur, cur - dim);
        }
    }

    public static void main(String[] args) {
        Percolation p = new Percolation(4);
        p.open(1, 1);
        p.open(1, 2);
        p.open(1, 3);
        p.open(1, 4);
        p.open(2, 1);
        p.open(3, 1);
        System.out.print(p.percolates());
    }
}
