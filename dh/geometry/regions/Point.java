package dh.geometry.regions;

public class Point {
	private final double x;
	private final double y;

	// Constructor

	public Point(double x, double y) {
		this.x = x;
		this.y = y;
	}

	// Getter and Setter

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	// new methods

	public double getDistanceToPoint(Point p) {
		return Math.sqrt(Math.pow(getY() - p.getX(), 2)
				+ Math.pow(getY() - p.getY(), 2));
	}

	// methods of java.lang.Object

	@Override
	public String toString() {
		return "Point [x=" + x + ", y=" + y + "]";
	}

	@Override
	public boolean equals(Object other) {
		boolean result = false;
		if (other instanceof Point) {
			Point that = (Point) other;
			result = (that.canEqual(this) && this.getX() == that.getX() && this
					.getY() == that.getY());
		}
		return result;
	}

	@Override
	public int hashCode() {
		return (int) (41 * (41 + getX()) + getY());
	}

	public boolean canEqual(Object other) {
		return (other instanceof Point);
	}
}
