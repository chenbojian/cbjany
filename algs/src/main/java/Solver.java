import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

public class Solver {

    private class SearchNode implements Comparable<SearchNode> {
        private final Board board;
        private final SearchNode prev;
        private final int length;
        private final int manhattan;
        private final boolean isGoal;

        SearchNode(Board board, SearchNode predecessor) {
            this.board = board;
            this.manhattan = this.board.manhattan();
            this.isGoal = this.board.isGoal();
            this.prev = predecessor;
            if (predecessor == null) {
                this.length = 0;
            } else {
                this.length = predecessor.length + 1;
            }
        }

        Iterable<SearchNode> neighbors() {
            Queue<SearchNode> result = new Queue<>();
            for (Board b : board.neighbors()) {
                if (prev != null && prev.board.equals(board)) {
                    continue;
                }
                result.enqueue(new SearchNode(b, this));
            }
            return result;
        }

        Iterable<Board> reconstructPath() {
            Stack<Board> result = new Stack<>();
            SearchNode current = this;
            while (current != null) {
                result.push(current.board);
                current = current.prev;
            }
            return result;
        }


        @Override
        public int compareTo(SearchNode that) {
            return this.length + this.manhattan - that.length - that.manhattan;
        }
    }

    private SearchNode solutionNode = null;

    public Solver(Board initial){
        if (initial == null) {
            throw new IllegalArgumentException();
        }

        MinPQ<SearchNode> openSet = new MinPQ<>();
        openSet.insert(new SearchNode(initial, null));
        MinPQ<SearchNode> twinOpenSet = new MinPQ<>();
        twinOpenSet.insert(new SearchNode(initial.twin(), null));

        while (!openSet.isEmpty() && !twinOpenSet.isEmpty()) {
            SearchNode result = findGoal(openSet);
            SearchNode twinResult = findGoal(twinOpenSet);
            if (result != null) {
                solutionNode = result;
                break;
            }
            if (twinResult != null) {
                break;
            }
        }

    }

    private SearchNode findGoal(MinPQ<SearchNode> openSet) {
        SearchNode node = openSet.delMin();
        if (node.isGoal) {
            return node;
        }
        for (SearchNode n : node.neighbors()) {
            openSet.insert(n);
        }
        return null;
    }

    public boolean isSolvable(){
        return solutionNode != null;
    }

    public int moves(){
        if (!isSolvable()) {
            return -1;
        }
        return solutionNode.length;
    }

    public Iterable<Board> solution(){
        if (!isSolvable()) {
            return null;
        }
        return solutionNode.reconstructPath();
    }

    public static void main(String[] args) {

        // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] blocks = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }
}