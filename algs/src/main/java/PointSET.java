import edu.princeton.cs.algs4.*;

public class PointSET {
    private final SET<Point2D> points;

    public PointSET() {
        points = new SET<>();
    }

    public boolean isEmpty() {
        return points.isEmpty();
    }

    public int size() {
        return points.size();
    }

    public void insert(Point2D p) {
        if (p == null) {
            throw new IllegalArgumentException();
        }
        if (this.contains(p)) {
            return;
        }
        points.add(p);
    }

    public boolean contains(Point2D p) {
        if (p == null) {
            throw new IllegalArgumentException();
        }
        return points.contains(p);
    }

    public void draw() {
        for (Point2D p : points) {
            StdDraw.point(p.x(), p.y());
        }
    }

    public Iterable<Point2D> range(RectHV rect) {
        Queue<Point2D> containedPoints = new Queue<>();
        for (Point2D p : points) {
            if (rect.contains(p)) {
                containedPoints.enqueue(p);
            }
        }
        return containedPoints;
    }

    public Point2D nearest(Point2D p) {
        double minDistanceSquared = Double.MAX_VALUE;
        Point2D nearestPoint = null;
        for (Point2D point: points) {
            double distanceSquared = point.distanceSquaredTo(p);
            if (nearestPoint == null || distanceSquared < minDistanceSquared) {
                nearestPoint = point;
                minDistanceSquared = distanceSquared;
            }
        }
        return nearestPoint;
    }

    public static void main(String[] args) {
    }
}