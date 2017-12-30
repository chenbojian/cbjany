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

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private final int gridSize;
    private final int virtualTopIndex;
    private final int virtualBottomIndex;
    private boolean[][] gridOpenStatus;
    private boolean[][] gridFullStatus;
    private final WeightedQuickUnionUF checkFullUf;
    private final WeightedQuickUnionUF checkPercolateUf;
    private int numberOfOpenSites;

    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException();
        }
        this.gridSize = n;
        this.gridOpenStatus = new boolean[n][n];
        this.gridFullStatus = new boolean[n][n];

        this.checkFullUf = new WeightedQuickUnionUF(n * n + 1);
        this.checkPercolateUf = new WeightedQuickUnionUF(n * n + 2);

        this.virtualTopIndex = n * n;
        this.virtualBottomIndex = n * n + 1;
    }

    private int getIndex(int row, int col) {
        return (row - 1) * this.gridSize + col - 1;
    }

    public    void open(int row, int col) {
        if (!isExist(row, col)) {
            throw new IllegalArgumentException();
        }
        if (isOpen(row, col)) {
            return;
        }

        gridOpenStatus[row-1][col-1] = true;

        if (isExist(row-1, col) && isOpen(row-1, col)) {
            checkFullUf.union(getIndex(row-1, col), getIndex(row, col));
            checkPercolateUf.union(getIndex(row-1, col), getIndex(row, col));
        }
        if (isExist(row+1, col) && isOpen(row+1, col)) {
            checkFullUf.union(getIndex(row+1, col), getIndex(row, col));
            checkPercolateUf.union(getIndex(row+1, col), getIndex(row, col));
        }
        if (isExist(row, col-1) && isOpen(row, col-1)) {
            checkFullUf.union(getIndex(row, col-1), getIndex(row, col));
            checkPercolateUf.union(getIndex(row, col-1), getIndex(row, col));
        }
        if (isExist(row, col+1) && isOpen(row, col+1)) {
            checkFullUf.union(getIndex(row, col+1), getIndex(row, col));
            checkPercolateUf.union(getIndex(row, col+1), getIndex(row, col));
        }

        if (row == 1) {
            checkFullUf.union(virtualTopIndex, getIndex(row, col));
            checkPercolateUf.union(virtualTopIndex, getIndex(row, col));            
        }
        if (row == gridSize) {
            checkPercolateUf.union(virtualBottomIndex, getIndex(row, col));
        }

        numberOfOpenSites += 1;
    }

    private boolean isExist(int row, int col) {
        if (row < 1 || row > gridSize || col < 1 || col > gridSize) {
            return false;
        }
        return true;
    }

    public boolean isOpen(int row, int col) {
        if (!isExist(row, col)) {
            throw new IllegalArgumentException();
        }
        return gridOpenStatus[row-1][col-1];
    }

    public boolean isFull(int row, int col) {
        if (!isExist(row, col)) {
            throw new IllegalArgumentException();
        }
        if (!gridFullStatus[row-1][col-1]) {
            gridFullStatus[row-1][col-1] =
                checkFullUf.connected(getIndex(row, col), virtualTopIndex);
        }
        return gridFullStatus[row-1][col-1];
    }

    public     int numberOfOpenSites() { // number of open sites
        return numberOfOpenSites;
    }

    public boolean percolates() {              // does the system percolate?
        return checkPercolateUf.connected(virtualBottomIndex, virtualTopIndex);
    }

    public static void main(String[] args) {   // test client (optional)

    }
}
