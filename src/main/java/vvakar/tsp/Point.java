package vvakar.tsp;

/**
 * Point in 2-D space with optional label.
 * @author vvakar
 *         Date: 9/25/14
 */
public class Point {
    public final double x, y;
    public final String label;

    public Point(double x, double y) {
        this(x, y, "");
    }

    public Point(double x, double y, String label) {
        this.x = x;
        this.y = y;
        this.label = label;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Point point = (Point) o;

        if (Double.compare(point.x, x) != 0) return false;
        if (Double.compare(point.y, y) != 0) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        temp = Double.doubleToLongBits(x);
        result = (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(y);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }
}
