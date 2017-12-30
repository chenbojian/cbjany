/******************************************************************************
 *  Name:    Bojian Chen
 *  NetID:   bjchen
 *  Precept: P01
 *
 *  Partner Name:    N/A
 *  Partner NetID:   N/A
 *  Partner Precept: N/A
 * 
 *  Description:  PercolationStats
 *
 ******************************************************************************/
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.StdOut;

public class PercolationStats {
    private static final double CONFIDENCE_95 = 1.96;

    private final double mean;
    private final double stddev;
    private final double confidenceLo;
    private final double confidenceHi;

    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0) {
            throw new IllegalArgumentException();
        }
        double[] thresholds = new double[trials];
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

        mean = StdStats.mean(thresholds);
        stddev = StdStats.stddev(thresholds);
        confidenceLo = mean - CONFIDENCE_95 * stddev / Math.sqrt(trials);
        confidenceHi = mean + CONFIDENCE_95 * stddev / Math.sqrt(trials);
    }

    public double mean() {
        return mean;
    }

    public double stddev() {
        return stddev;
    }
    public double confidenceLo() {
        return confidenceLo;
    }
    public double confidenceHi() {
        return confidenceHi;
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