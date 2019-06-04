/* *****************************************************************************
 *  Name: Zihang Wang
 *  Date: 2019/06/03
 *  Description: estimate the threshold for Percolation Problems
 **************************************************************************** */

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {

    // instance varialbes for PercolationStats
    private int trials;
    private Percolation pclt;
    private double[] thresholds;

    // PercolationStats Construction
    public PercolationStats(int n, int t) {
        if (n <= 0 || t <= 0) {
            throw new java.lang.IllegalArgumentException(
                    "dimension or trials is less than or equal to 0");
        }

        trials = t;
        thresholds = new double[trials];

        for (int i = 0; i < trials; i++) {
            pclt = new Percolation(n);
            int num = 0;
            while (!pclt.percolates()) {
                int row = StdRandom.uniform(1, n + 1);
                int col = StdRandom.uniform(1, n + 1);
                if (!pclt.isOpen(row, col)) {
                    pclt.open(row, col);
                    num += 1;
                }
            }
            thresholds[i] = (double) num / Math.pow(n, 2);
        }
    }

    // return the double mean of all the thresholds
    public double mean() {
        return StdStats.mean(thresholds);
    }

    // return the standard deviation of all the thresholds
    public double stddev() {
        return StdStats.stddev(thresholds);
    }

    // return the low endpoint of 95% confidence interval
    public double confidenceLo() {
        return mean() - 1.96 * stddev() / Math.sqrt(trials);
    }

    // return the high endpoint of 95% confidence interval;
    public double confidenceHi() {
        return mean() + 1.96 * stddev() / Math.sqrt(trials);
    }

    public static void main(String[] args) {
        int dim = Integer.parseInt(args[0]);
        int trials = Integer.parseInt(args[1]);
        PercolationStats pclt = new PercolationStats(dim, trials);
        System.out.println("mean                    = " + pclt.mean());
        System.out.println("stddev                  = " + pclt.stddev());
        System.out.printf("95%% confidence interval = [%f %f]", pclt.confidenceLo(),
                          pclt.confidenceHi());
    }
}
