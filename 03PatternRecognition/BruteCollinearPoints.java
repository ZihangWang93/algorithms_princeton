/* *****************************************************************************
 *  Name: Zihang Wang
 *  Date: 06/12/2019
 *  Description: BruteForce method for checking collinear
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;

public class BruteCollinearPoints {
    private int numberOfSegments;
    private ArrayList<LineSegment> segments;

    public BruteCollinearPoints(Point[] points) {
        if (points == null) {
            throw new IllegalArgumentException("null input");
        }

        numberOfSegments = 0;
        segments = new ArrayList<LineSegment>();
        int numOfPoints = points.length;

        for (int i = 0; i <= numOfPoints - 4; i++) {

            if (points[i] == null) {
                throw new IllegalArgumentException("first point is null");
            }

            for (int j = i + 1; j <= numOfPoints - 3; j++) {

                if (points[j] == null || points[j].slopeTo(points[i]) == Double.NEGATIVE_INFINITY) {
                    throw new IllegalArgumentException(
                            "second point is null or same input points ");
                }

                for (int k = j + 1; k <= numOfPoints - 2; k++) {

                    if (points[k] == null
                            || points[k].slopeTo(points[i]) == Double.NEGATIVE_INFINITY
                            || points[k].slopeTo(points[j]) == Double.NEGATIVE_INFINITY) {
                        throw new IllegalArgumentException(
                                "third point is null or same input points");
                    }

                    if (points[i].slopeTo(points[j]) == points[i].slopeTo(points[k])) {

                        for (int l = k + 1; l <= numOfPoints - 1; l++) {

                            if (points[l] == null
                                    || points[l].slopeTo(points[i]) == Double.NEGATIVE_INFINITY
                                    || points[l].slopeTo(points[j]) == Double.NEGATIVE_INFINITY
                                    || points[l].slopeTo(points[k]) == Double.NEGATIVE_INFINITY) {
                                throw new IllegalArgumentException(
                                        "fourth point is null or same input points");
                            }

                            if (points[i].slopeTo(points[l]) == points[i].slopeTo(points[j])) {
                                numberOfSegments += 1;
                                Point[] p = { points[i], points[j], points[k], points[l] };
                                Arrays.sort(p);
                                segments.add(new LineSegment(p[0], p[3]));
                            }

                        }
                    }
                }
            }
        }
    }

    public int numberOfSegments() {
        return numberOfSegments;
    }

    public LineSegment[] segments() {
        return segments.toArray(new LineSegment[segments.size()]);
    }

    public static void main(String[] args) {
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
