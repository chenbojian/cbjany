import java.util.Arrays;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

public class FastCollinearPoints {

    private LineSegment[] segments;
    private Point[][] existingSegmentPoints;
    private int countOfSegments;

    public FastCollinearPoints(Point[] points) {
        Arrays.sort(points);
        for (int i = 0; i < points.length - 1; i++) {
            if (points[i].compareTo(points[i + 1]) == 0) {
                throw new IllegalArgumentException();
            }
        }

        segments = new LineSegment[points.length / 2 + 1];
        existingSegmentPoints = new Point[points.length / 2 + 1][2];
        countOfSegments = 0;

        for (int i = 0; i < points.length; i++) {
            Point[] copiedPoints = copyAndSwap(points, i);
            copiedPoints[0].draw();
            Arrays.sort(copiedPoints, 1, copiedPoints.length, copiedPoints[0].slopeOrder());
            for (int j = 1; j < copiedPoints.length - 2; j ++) {
                Point[] fourPoints = new Point[4];
                fourPoints[0] = copiedPoints[0];
                fourPoints[1] = copiedPoints[j];
                fourPoints[2] = copiedPoints[j + 1];
                fourPoints[3] = copiedPoints[j + 2];
                if (isFourPointsOnSameSlope(fourPoints[0], fourPoints[1], fourPoints[2], fourPoints[3])) {
                    Arrays.sort(fourPoints);
                    if (isSlopeAlreadyExist(fourPoints[0], fourPoints[3])) {
                        continue;
                    }
                    existingSegmentPoints[countOfSegments][0] = fourPoints[0];
                    existingSegmentPoints[countOfSegments][1] = fourPoints[3];
                    segments[countOfSegments++] = new LineSegment(fourPoints[0], fourPoints[3]);
                }
            }
        }
    }

    private boolean isSlopeAlreadyExist(Point p1, Point p2) {
        for (int i = 0; i < countOfSegments; i++) {
            Point ep1 = existingSegmentPoints[i][0];
            Point ep2 = existingSegmentPoints[i][1];
            if (ep1.compareTo(p1) == 0 && ep2.compareTo(p2) == 0 ) {
                return true;
            }
            if (ep1.compareTo(p2) == 0 && ep2.compareTo(p1) == 0 ) {
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
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}

