import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

public class Solver {

    private class SearchNode implements Comparable {
        private final Board board;
        private final SearchNode prev;
        private final int length;

        SearchNode(Board board, SearchNode predecessor) {
            this.board = board;
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

        SearchNode getHead() {
            SearchNode current = this;
            while (current.prev != null) {
                current = current.prev;
            }
            return current;
        }

        private int getScore() {
            return this.length + this.board.manhattan();
        }

        @Override
        public int compareTo(Object o) {
            if (!(o instanceof SearchNode)) {
                throw new IllegalArgumentException();
            }
            SearchNode that = (SearchNode)o;

            if (this.getScore() > that.getScore()) {
                return 1;
            }

            if (this.getScore() < that.getScore()) {
                return -1;
            }

            return 0;
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
            SearchNode node = openSet.delMin();
            if (node.board.isGoal()) {
                solutionNode = node;
                break;
            }
            for (SearchNode n : node.neighbors()) {
                openSet.insert(n);
            }
            SearchNode twinNode = twinOpenSet.delMin();
            if (twinNode.board.isGoal()) {
                break;
            }
            for (SearchNode n : twinNode.neighbors()) {
                twinOpenSet.insert(n);
            }

        }

    }

    public boolean isSolvable(){
        return solutionNode != null;
    }

    public int moves(){
        if (!isSolvable()) {
            throw new IllegalArgumentException();
        }
        return solutionNode.length;
    }

    public Iterable<Board> solution(){
        if (!isSolvable()) {
            throw new IllegalArgumentException();
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