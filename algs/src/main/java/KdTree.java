import edu.princeton.cs.algs4.*;

public class KdTree {
    private int size;
    private Node root;

    private static class Node {
        private Point2D p;      // the point
        private RectHV rect;    // the axis-aligned rectangle corresponding to this node
        private Node lb;        // the left/bottom subtree
        private Node rt;        // the right/top subtree
    }

    public KdTree() {
        size = 0;
        root = null;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public void insert(Point2D p) {
        if (p == null) {
            throw new IllegalArgumentException();
        }
        if (this.contains(p)) {
            return;
        }
        size++;
        root = insert(root, p, true, new RectHV(0, 0, 1, 1));
    }

    private boolean isPointLeftOrBottom(Point2D point, Point2D pointToCompare, boolean isVerticle) {
        if (isVerticle) {
            return point.x() < pointToCompare.x();
        } else {
            return point.y() < pointToCompare.y();
        }
    }

    private Node insert(Node root, Point2D point, boolean isVerticle, RectHV rect) {
        if (root == null) {
            Node node = new Node();
            node.p = point;
            if (isVerticle) {
                node.rect = new RectHV(point.x(), rect.ymin(), point.x(), rect.ymax());
            } else {
                node.rect = new RectHV(rect.xmin(), point.y(), rect.xmax(), point.y());
            }
            return node;
        }
        if (isPointLeftOrBottom(point, root.p, isVerticle)) {
            double xmax = isVerticle ? root.p.x() : rect.xmax();
            double ymax = isVerticle ? rect.ymax() : root.p.y();
            root.lb = insert(root.lb, point, !isVerticle, new RectHV(rect.xmin(), rect.ymin(), xmax, ymax));
        } else {
            double xmin = isVerticle ? root.p.x() : rect.xmin();
            double ymin = isVerticle ? rect.ymin() : root.p.y();
            root.rt = insert(root.rt, point, !isVerticle, new RectHV(xmin, ymin, rect.xmax(), rect.ymax()));
        }
        return root;
    }

    public boolean contains(Point2D p) {
        if (p == null) {
            throw new IllegalArgumentException();
        }

        return find(root, p, true) != null;
    }

    private Node find(Node root, Point2D point, boolean isVerticle) {
        if (root == null) {
            return null;
        }
        if (root.p.equals(point)) {
            return root;
        }

        if (isPointLeftOrBottom(point, root.p, isVerticle)) return find(root.lb, point, !isVerticle);
        else return find(root.rt, point, !isVerticle);
    }

    private void traverseTree(Node root, Queue<Node> queue) {
        if (root == null) {
            return;
        }
        queue.enqueue(root);
        traverseTree(root.lb, queue);
        traverseTree(root.rt, queue);
    }

    private Iterable<Node> getAllNodes() {
        Queue<Node> nodes = new Queue<>();
        traverseTree(root, nodes);
        return nodes;
    }

    public void draw() {
        for (Node node : getAllNodes()) {
            StdDraw.setPenColor(StdDraw.BLACK);
            StdDraw.setPenRadius(0.01);
            StdDraw.point(node.p.x(), node.p.y());
            StdDraw.setPenColor(node.rect.xmin() == node.rect.xmax() ? StdDraw.RED : StdDraw.BLUE);
            StdDraw.setPenRadius();
            StdDraw.line(node.rect.xmin(), node.rect.ymin(), node.rect.xmax(), node.rect.ymax());
        }
    }

    private boolean isRectContainsLeftOrBottom(RectHV rect, Node node) {
        boolean isVerticle = node.rect.xmin() == node.rect.xmax();
        if (isVerticle) {
            return rect.xmin() < node.p.x();
        } else {
            return rect.ymin() < node.p.y();
        }
    }

    private boolean isRectContainsRightOrTop(RectHV rect, Node node) {
        boolean isVerticle = node.rect.xmin() == node.rect.xmax();
        if (isVerticle) {
            return Double.compare(rect.xmax(), node.p.x()) >= 0;
        } else {
            return Double.compare(rect.ymax(), node.p.y()) >= 0;
        }
    }


    private void searchInRange(RectHV rect, Node root, Queue<Point2D> points) {
        if (root == null) {
            return;
        }
        if (rect.contains(root.p)) {
            points.enqueue(root.p);
        }

        if (isRectContainsLeftOrBottom(rect, root)) {
            searchInRange(rect, root.lb, points);
        }
        if (isRectContainsRightOrTop(rect, root)) {
            searchInRange(rect, root.rt, points);
        }
    }

    public Iterable<Point2D> range(RectHV rect) {
        Queue<Point2D> points = new Queue<>();
        searchInRange(rect, root, points);
        return points;
    }

    private Node searchNearest(Point2D point, Node root, Node nearestNode) {
        if (root == null) {
            return nearestNode;
        }
        Node newNearestNode = nearestNode;
        double distanceSquared = point.distanceSquaredTo(root.p);
        if (distanceSquared < point.distanceSquaredTo(newNearestNode.p)) {
            newNearestNode = root;
        }

        boolean isVerticle = root.rect.xmin() == root.rect.xmax();

        if (isPointLeftOrBottom(point, root.p, isVerticle)) {
            newNearestNode = searchNearest(point, root.lb, newNearestNode);
            if (root.rect.distanceSquaredTo(point) < point.distanceSquaredTo(newNearestNode.p)) {
                newNearestNode = searchNearest(point, root.rt, newNearestNode);
            }
        } else {
            newNearestNode = searchNearest(point, root.rt, newNearestNode);
            if (root.rect.distanceSquaredTo(point) < point.distanceSquaredTo(newNearestNode.p)) {
                newNearestNode = searchNearest(point, root.lb, newNearestNode);
            }
        }
        return newNearestNode;
    }

    public Point2D nearest(Point2D p) {
        if (root == null) return null;
        return searchNearest(p, root, root).p;
    }

    public static void main(String[] args) {
    }
}