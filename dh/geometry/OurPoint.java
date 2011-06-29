package dh.geometry;

public class OurPoint {
	private final double x;
	private final double y;

	// Constructor

	public OurPoint(double x, double y) {
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

	/*
	 * Liefert die euklidische Distanz zweier Punkte
	 */

	public double getDistance(OurPoint p) {
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
		if (other instanceof OurPoint) {
			OurPoint that = (OurPoint) other;
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
		return (other instanceof OurPoint);
	}
}
