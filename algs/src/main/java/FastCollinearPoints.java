import java.util.Arrays;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

public class FastCollinearPoints {

    private LineSegment[] segments;
    private int countOfSegments = 0;

    public FastCollinearPoints(Point[] points) {
        validatePoints(points);

        segments = new LineSegment[points.length];

        findCollinear(points);
    }

    private void findCollinear(Point[] originPoints) {
        Point[] points = Arrays.copyOf(originPoints, originPoints.length);
        Arrays.sort(points);

        for (int i = 0; i < points.length; i++) {
            Point[] tempPoints = new Point[points.length - 1];
            System.arraycopy(points, 0, tempPoints, 0, i);
            System.arraycopy(points, i + 1, tempPoints, i, points.length - i - 1);
            Arrays.sort(tempPoints, points[i].slopeOrder());

            Point[] slopeOrderedPoints = new Point[tempPoints.length + 1];
            slopeOrderedPoints[0] = points[i];
            System.arraycopy(tempPoints, 0, slopeOrderedPoints, 1, tempPoints.length);

            findAndAddToSegments(slopeOrderedPoints);
        }
    }

    private void validatePoints(Point[] points) {
        if (points == null) {
            throw new IllegalArgumentException();
        }
        for (Point point : points) {
            if (point == null) {
                throw new IllegalArgumentException();
            }
        }
        Point[] copiedPoints = Arrays.copyOf(points, points.length);
        Arrays.sort(copiedPoints);

        for (int i = 0; i < copiedPoints.length - 1; i++) {
            if (copiedPoints[i].compareTo(copiedPoints[i + 1]) == 0) {
                throw new IllegalArgumentException();
            }
        }
    }

    private void findAndAddToSegments(Point[] points) {
        int i = 1;
        while (i < points.length) {
            int count = 1;
            Point min = points[0];
            Point max = points[0];

            if (points[i].compareTo(max) > 0) {
                max = points[i];
            }

            if (points[i].compareTo(min) < 0) {
                min = points[i];
            }
            for (int j = i; j < points.length - 1; j++) {
                if (points[0].slopeTo(points[j]) == points[0].slopeTo(points[j + 1])) {
                    if (points[j + 1].compareTo(max) > 0) {
                        max = points[j + 1];
                    }

                    if (points[j + 1].compareTo(min) < 0) {
                        min = points[j + 1];
                    }
                    count++;
                } else {
                    break;
                }
            }

            if (count >= 3 && min.compareTo(points[0]) == 0) {
                addToSegments(min, max);
            }
            i = i + count;
        }
    }

    private void addToSegments(Point p, Point q) {
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

