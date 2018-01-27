import java.util.Arrays;

import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.StdOut;

public class Board {
    private final int n;
    private int[][] blocks;

    public Board(int[][] blocks) {
        preCheckArguments(blocks);
        this.n = blocks.length;
        this.blocks = blocks;
    }

    private void preCheckArguments(int[][] blocks) {
        if (blocks == null) {
            throw new IllegalArgumentException();
        }
        if (blocks.length < 2) {
            throw new IllegalArgumentException();
        }
        if (blocks[0].length != blocks.length) {
            throw new IllegalArgumentException();
        }
        int[] nums = new int[blocks.length * blocks.length];
        int k = 0;
        for (int i = 0; i < blocks.length; i++) {
            for (int j = 0; j < blocks.length; j++) {
                nums[k++] = blocks[i][j];
            }
        }
        Arrays.sort(nums);
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] != i) {
                throw new IllegalArgumentException();
            }
        }

    }

    private int getGoalRow(int num) {
        if (num == 0) {
            return this.n - 1;
        }

        return (num - 1) / this.n;
    }

    private int getGoalCol(int num) {
        if (num == 0) {
            return this.n - 1;
        }

        return (num - 1) % this.n;
    }

    public int dimension() {
        return n;
    }

    public int hamming() {
        int result = 0;
        for (int i = 0; i < this.n; i++) {
            for (int j = 0; j < this.n; j++) {
                if (blocks[i][j] == 0) {
                    continue;
                }
                int goalRow = getGoalRow(blocks[i][j]);
                int goalCol = getGoalCol(blocks[i][j]);
                if (goalRow != i || goalCol != j) {
                    result++;
                }
            }
        }
        return result;
    }

    public int manhattan() {
        int result = 0;
        for (int i = 0; i < this.n; i++) {
            for (int j = 0; j < this.n; j++) {
                if (blocks[i][j] == 0) {
                    continue;
                }
                int goalRow = getGoalRow(blocks[i][j]);
                int goalCol = getGoalCol(blocks[i][j]);
                if (goalRow != i || goalCol != j) {
                    result += Math.abs(goalRow - i) + Math.abs(goalCol - j);
                }
            }
        }
        return result;
    }

    public boolean isGoal() {
        for (int i = 0; i < this.n; i++) {
            for (int j = 0; j < this.n; j++) {
                if (blocks[i][j] == 0) {
                    continue;
                }
                int goalRow = getGoalRow(blocks[i][j]);
                int goalCol = getGoalCol(blocks[i][j]);
                if (goalRow != i || goalCol != j) {
                    return false;
                }
            }
        }
        return true;
    }

    public Board twin() {
        int zeroRow = -1;
        int zeroCol = -1;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (blocks[i][j] == 0) {
                    zeroRow = i;
                    zeroCol = j;
                    break;
                }
            }
        }
        int row = getNeighborIndexes(zeroRow).dequeue();
        int col = getNeighborIndexes(zeroCol).dequeue();
        int[][] twinBlocks = cloneBlocksWithExchange(zeroRow, col, row, zeroCol);

        return new Board(twinBlocks);
    }

    private int[][] cloneBlocksWithExchange(int srcRow, int srcCol, int destRow, int destCol) {
        int[][] clonedBlocks = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                clonedBlocks[i][j] = (int)blocks[i][j];
            }
        }
        int temp = clonedBlocks[srcRow][srcCol];
        clonedBlocks[srcRow][srcCol] = clonedBlocks[destRow][destCol];
        clonedBlocks[destRow][destCol] = temp;
        return clonedBlocks;
    }

    public boolean equals(Object y) {
        if (y == this) return true;
        if (y == null) return false;
        if (y.getClass() != this.getClass()) return false;
        Board that = (Board) y;

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (this.blocks[i][j] != that.blocks[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }

    public Iterable<Board> neighbors() {
        int zeroRow = -1;
        int zeroCol = -1;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (blocks[i][j] == 0) {
                    zeroRow = i;
                    zeroCol = j;
                    break;
                }
            }
        }

        Queue<Integer> neighborRows = getNeighborIndexes(zeroRow);
        Queue<Integer> neighborCols = getNeighborIndexes(zeroCol);
        Queue<Board> result = new Queue<>();
        for (int row : neighborRows) {
            int[][] newBlocks = cloneBlocksWithExchange(zeroRow, zeroCol, row, zeroCol);
            result.enqueue(new Board(newBlocks));
        }
        for (int col : neighborCols) {
            int[][] newBlocks = cloneBlocksWithExchange(zeroRow, zeroCol, zeroRow, col);
            result.enqueue(new Board(newBlocks));
        }
        return result;
    }

    private Queue<Integer> getNeighborIndexes(int index) {
        Queue<Integer> result = new Queue<>();
        if (index == 0) {
            result.enqueue(1);
            return result;
        }
        if (index == n - 1) {
            result.enqueue(n - 2);
            return result;
        }
        result.enqueue(index - 1);
        result.enqueue(index + 1);
        return result;
    }

    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(n).append("\n");

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                s.append(String.format("%2d ", (int)blocks[i][j]));
            }
            s.append("\n");
        }

        return s.toString();
    }

    public static void main(String[] args) {
        int[][] blocks = new int[3][3];
        blocks[0][0] = 0;
        blocks[0][1] = 1;
        blocks[0][2] = 3;
        blocks[1][0] = 4;
        blocks[1][1] = 2;
        blocks[1][2] = 5;
        blocks[2][0] = 7;
        blocks[2][1] = 8;
        blocks[2][2] = 6;
        Board board = new Board(blocks);
        StdOut.println(board);
        StdOut.println(board.hamming());
        StdOut.println(board.manhattan());
        for (Board b : board.neighbors()) {
            StdOut.println(b);
        }
    }
}
