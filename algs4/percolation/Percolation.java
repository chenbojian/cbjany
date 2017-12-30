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
import java.util.ArrayList;

public class Percolation {
    private int gridSize;
    private int virtualTopIndex;
    private boolean[][] gridOpenStatus;
    private WeightedQuickUnionUF uf;

    public Percolation(int n) {
        this.gridSize = n;
        this.gridOpenStatus = new boolean[n][n];

        this.uf = new WeightedQuickUnionUF(n * n + 1);

        this.virtualTopIndex = n * n;
    }

    private int getIndex(int row, int col) {
        return (row - 1) * this.gridSize + col - 1;
    }

    private ArrayList<Integer> getOpenSurroundIndexes(int row, int col) {
        ArrayList<Integer> result = new ArrayList<Integer>();
        if (isExist(row-1, col) && isOpen(row-1, col)) {
            result.add(getIndex(row-1, col));
        }
        if (isExist(row+1, col) && isOpen(row+1, col)) {
            result.add(getIndex(row+1, col));
        }
        if (isExist(row, col-1) && isOpen(row, col-1)) {
            result.add(getIndex(row, col-1));
        }
        if (isExist(row, col+1) && isOpen(row, col+1)) {
            result.add(getIndex(row, col+1));
        }

        if (row == 1) {
            result.add(virtualTopIndex);
        }
        return result;
    }

    public    void open(int row, int col) {
        if (!isExist(row, col)) {
            throw new IllegalArgumentException();
        }
        gridOpenStatus[row-1][col-1] = true;
        ArrayList<Integer> openSurroundIndexes = getOpenSurroundIndexes(row, col);
        for (int i : openSurroundIndexes) {
            uf.union(i, getIndex(row, col));
        }
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
        return uf.connected(getIndex(row, col), virtualTopIndex);
    }

    public     int numberOfOpenSites() { // number of open sites
        int count = 0;
        for (int i = 0; i < gridSize; i++) {
            for (int j = 0; j < gridSize; j++) {
                if (gridOpenStatus[i][j]) {
                    count++;
                }
            }
        }
        return count;
    }

    public boolean percolates() {              // does the system percolate?
        for (int col = 1; col <= gridSize; col++) {
            if (uf.connected(getIndex(gridSize, col), virtualTopIndex)) {
                return true;
            }
        }
        return false;
    }

    public static void main(String[] args) {   // test client (optional)

    }
}
