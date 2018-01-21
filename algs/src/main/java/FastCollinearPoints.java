import java.util.Arrays;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

public class FastCollinearPoints {

    private LineSegment[] segments;
    private Point[] points;
    private double[][] pointSlopes;
    private int countOfSegments = 0;

    public FastCollinearPoints(Point[] points) {
        if (points == null) {
            throw new IllegalArgumentException();
        }
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
        this.points = new Point[points.length];
        for (int i = 0; i < points.length; i++) {
            this.points[i] = points[i];
        }
        Arrays.sort(this.points);

        initPointSlopes(this.points);

        segments = new LineSegment[1];

        for (int i = 0; i < this.points.length; i++) {
            Point[] copiedPoints = new Point[this.points.length];
            copiedPoints[0] = this.points[i];
            for (int j = 1; j < this.points.length; j++) {
                copiedPoints[j] = i == j ? this.points[0] : this.points[j];
            }

            Arrays.sort(copiedPoints, 1, copiedPoints.length, copiedPoints[0].slopeOrder());
            findAndAddToSegments(copiedPoints);
        }
    }

    private void initPointSlopes(Point[] points) {
        pointSlopes = new double[points.length][points.length];
        for (int i = 0; i < points.length; i++) {
            for (int j = 0; j < points.length; j++) {
                if (i > j) {
                    pointSlopes[i][j] = pointSlopes[j][i];
                } else {
                    pointSlopes[i][j] = points[i].slopeTo(points[j]);
                }
            }
        }
    }

    private void findAndAddToSegments(Point[] points) {
        int i = 1;
        while (i < points.length) {
            int count = 1;
            double currentSlope = points[0].slopeTo(points[i]);
            for (int j = i + 1; j < points.length; j++) {
                if (points[i].slopeTo(points[j]) == currentSlope) {
                    count++;
                }
            }
            if (count >= 3) {
                Point[] pointsToAdd = new Point[count + 1];
                pointsToAdd[0] = points[0];
                for (int k = 0; k < count; k++) {
                    pointsToAdd[k + 1] = points[i + k];
                }
                addToSegments(pointsToAdd);
            }
            i = i + count;
        }
    }

    private void addToSegments(Point[] points) {
        Point[] sortedPoints = Arrays.copyOf(points, points.length);
        Arrays.sort(sortedPoints);
        Point p = sortedPoints[0];
        Point q = sortedPoints[sortedPoints.length - 1];
        if (p.compareTo(points[0]) > 0) {
            return;
        }
        segments[countOfSegments] = new LineSegment(p, q);
        if (segments.length - 1 == countOfSegments) {
            LineSegment[] resizedSegments = new LineSegment[segments.length * 2];
            for (int i = 0; i < segments.length; i++) {
                resizedSegments[i] = segments[i];
            }
            segments = resizedSegments;
        }
        countOfSegments++;
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
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}

