package hw2;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {

    /** Part 1: Declarations. */
    private Percolation percolationStat;
    private double[] thresholdArray;
    private int times;
    private double magicNumber;

    /** Part 2: Assignment / Initiation. */
    public PercolationStats(int N, int T, PercolationFactory pf) {
        /** Exception. */
        if (N <= 0 | T <= 0) {
            throw new IllegalArgumentException("size N or times T should be positive");
        }
        /** Assignment. */
        times = T;
        thresholdArray = new double[T];
        /** Each time of experiment. */
        for (int i = 0; i <= T - 1; i++) {
            percolationStat = pf.make(N);
            double percolationThreshold;
            /** Each random open. */
            while (true) {
                int row = StdRandom.uniform(N);
                int col = StdRandom.uniform(N);
                if (!percolationStat.isOpen(row, col)) {
                    percolationStat.open(row, col);
                    if (percolationStat.percolates()) {
                        percolationThreshold = (double) percolationStat.numberOfOpenSites() / (double) (N * N);
                        thresholdArray[i] = percolationThreshold;
                        break;
                    }
                }
            }
        }
    }


    /** Part 3: Methods. */

    /** Method 1: Sample mean of percolation threshold. */
    public double mean() {
        return StdStats.mean(thresholdArray);
    }

    /** Method 2: Sample standard deviation of percolation threshold. */
    public double stddev() {
        return StdStats.stddev(thresholdArray);
    }

    /** Method 3: Low endpoint of 95% confidence interval. */
    public double confidenceLow() {
        magicNumber = 1.96;
        double confidenceLow = (mean() - magicNumber * stddev() / Math.sqrt(times));
        return confidenceLow;
    }

    /** Method 4: High endpoint of 95% confidence interval. */
    public double confidenceHigh() {
        magicNumber = 1.96;
        double confidenceHigh = (mean() + magicNumber * stddev() / Math.sqrt(times));
        return confidenceHigh;
    }
}
