import java.util.Arrays;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

public class FastCollinearPoints {

    private LineSegment[] segments;
    private Point[][] existingSegmentPoints;
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

        segments = new LineSegment[1];
        existingSegmentPoints = new Point[1][2];

        for (int i = 0; i < points.length; i++) {
            Point[] copiedPoints = copyAndSwap(points, i);
            copiedPoints[0].draw();
            Arrays.sort(copiedPoints, 1, copiedPoints.length, copiedPoints[0].slopeOrder());
            findAndAddToSegments(copiedPoints);

        }
    }

    private void findAndAddToSegments(Point[] points) {
        int i = 1;
        while(i < points.length) {
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
        if (isSegmentPointsAlreadyExist(p, q)) {
            return;
        }
        existingSegmentPoints[countOfSegments][0] = p;
        existingSegmentPoints[countOfSegments][1] = q;
        if (existingSegmentPoints.length - 1 == countOfSegments) {
            Point[][] resizedExistingSegmentPoints = new Point[existingSegmentPoints.length * 2][2];
            for (int i = 0; i < existingSegmentPoints.length; i++) {
                resizedExistingSegmentPoints[i] = existingSegmentPoints[i];
            }
            existingSegmentPoints = resizedExistingSegmentPoints;
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

    private boolean isSegmentPointsAlreadyExist(Point p1, Point p2) {
        for (int i = 0; i < countOfSegments; i++) {
            Point ep1 = existingSegmentPoints[i][0];
            Point ep2 = existingSegmentPoints[i][1];
            if (ep1.compareTo(p1) == 0 && ep2.compareTo(p2) == 0) {
                return true;
            }
            if (ep1.compareTo(p2) == 0 && ep2.compareTo(p1) == 0) {
                return true;
            }
        }
        return false;
    }

    private Point[] copyAndSwap(Point[] points, int i) {
        Point[] result = new Point[points.length];
        for (int j = 0; j < points.length; j++) {
            result[j] = points[j];
        }
        Point tmp = result[i];
        result[i] = result[0];
        result[0] = tmp;
        return result;
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

