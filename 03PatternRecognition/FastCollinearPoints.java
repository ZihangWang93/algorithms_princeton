/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;

public class FastCollinearPoints {
    private int numOfSegments;
    private ArrayList<LineSegment> segments;

    public FastCollinearPoints(Point[] points) {
        numOfSegments = 0;
        segments = new ArrayList<LineSegment>();

        if (points == null) {
            throw new IllegalArgumentException("Input is null");
        }

        for (int i = 0; i <= points.length - 4; i++) {
            if (points[i] == null) {
                throw new IllegalArgumentException("The first input is null");
            }

            Point[] p = new Point[points.length - i - 1];
            System.arraycopy(points, i + 1, p, 0, points.length - i - 1);
            Arrays.sort(p, points[i].slopeOrder());

            for (int j = 0; j <= p.length - 3; j++) {
                if (p[j] == null || p[j + 1] == null || p[j + 2] == null
                        || points[i].slopeTo(p[j]) == Double.NEGATIVE_INFINITY
                        || points[i].slopeTo(p[j + 1]) == Double.NEGATIVE_INFINITY
                        || points[i].slopeTo(p[j + 2]) == Double.NEGATIVE_INFINITY) {
                    throw new IllegalArgumentException(
                            "The input point is null or the same input");
                }

                if (points[i].slopeTo(p[j]) == points[i].slopeTo(p[j + 1])
                        && points[i].slopeTo(p[j]) == points[i].slopeTo(p[j + 2])) {
                    numOfSegments += 1;
                    Point[] p2 = { points[i], p[j], p[j + 1], p[j + 2] };
                    Arrays.sort(p2);
                    segments.add(new LineSegment(p2[0], p2[3]));
                }
            }

        }
    }

    public int numberOfSegments() {
        return numOfSegments;
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
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}
