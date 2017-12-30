/******************************************************************************
 *  Name:    Bojian Chen
 *  NetID:   bjchen
 *  Precept: P01
 *
 *  Partner Name:    N/A
 *  Partner NetID:   N/A
 *  Partner Precept: N/A
 * 
 *  Description:  Model an n-by-n percolation system using the union-find
 *                data structure.
 ******************************************************************************/
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.StdOut;

public class PercolationStats {
    private double[] thresholds;
    private int trials;

    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0) {
            throw new IllegalArgumentException();
        }
        thresholds = new double[trials];
        this.trials = trials;
        for (int t = 0; t < trials; t++) {
            int[] positions = new int[n * n];
            for (int i = 0; i < n * n; i++) {
                positions[i] = i;
            }
            
            StdRandom.shuffle(positions);
            Percolation percolation = new Percolation(n);
            int count = 0;
            for (int p : positions) {
                int r = p / n + 1;
                int c = p % n + 1;
                percolation.open(r, c);
                count++;
                if (percolation.percolates()) {
                    break;
                }
            }

            thresholds[t] = (double) count / (n * n);
        }
    }

    public double mean() {
        return StdStats.mean(thresholds);
    }

    public double stddev() {
        return StdStats.stddev(thresholds);
    }
    public double confidenceLo() {
        return mean() - 1.96 * stddev() / Math.sqrt(trials);
    }
    public double confidenceHi() {
        return mean() + 1.96 * stddev() / Math.sqrt(trials);
    }

    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        int trials = Integer.parseInt(args[1]);
        PercolationStats stats = new PercolationStats(n, trials);
        StdOut.printf("mean                    = %.16f\n", stats.mean());
        StdOut.printf("stddev                  = %.16f\n", stats.stddev());
        StdOut.printf("95%% confidence interval = [%.16f, %.16f]\n",
                        stats.confidenceLo(), stats.confidenceHi());
    }
}