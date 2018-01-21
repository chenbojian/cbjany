import java.util.Arrays;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

public class BruteCollinearPoints {

    private LineSegment[] segments;
    private int countOfSegments;

    public BruteCollinearPoints(Point[] points) {
        for (Point point : points) {
            if (point == null) {
                throw new IllegalArgumentException();
            }
        }
        for (int i = 0; i < points.length - 1; i++) {
            for (int j = 1; j < points.length; j++) {
                if (i != j && points[i].compareTo(points[j]) == 0) {
                    throw new IllegalArgumentException();
                }
            }
        }

        segments = new LineSegment[points.length];
        countOfSegments = 0;

        for (int i = 0; i < points.length; i++) {
            for (int j = i + 1; j < points.length; j++) {
                for (int k = j + 1; k < points.length; k++) {
                    for (int l = k + 1; l < points.length; l++) {
                        Point[] fourPoints = new Point[4];
                        fourPoints[0] = points[i];
                        fourPoints[1] = points[j];
                        fourPoints[2] = points[k];
                        fourPoints[3] = points[l];
                        if (isFourPointsOnSameSlope(points[i], points[j], points[k], points[l])) {
                            Arrays.sort(fourPoints);
                            segments[countOfSegments++] = new LineSegment(fourPoints[0], fourPoints[3]);
                        }
                    }
                }
            }
        }
    }

    private boolean isFourPointsOnSameSlope(Point p1, Point p2, Point p3, Point p4) {
        return p1.slopeTo(p2) == p1.slopeTo(p3) && p1.slopeTo(p3) == p1.slopeTo(p4);
    }

    public int numberOfSegments() {
        return countOfSegments;
    }

    public LineSegment[] segments() {
        LineSegment[] result = new LineSegment[countOfSegments];
        for (int i = 0; i < countOfSegments; i++) {
            result[i] = segments[i];
        }
        return result;
    }

    public static void main(String[] args) {

        // read the n points from a file
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}
