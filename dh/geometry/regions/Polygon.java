package dh.geometry.regions;

public class Polygon {
	private Point[] points;

	public Polygon(Point... points) {
		this.points = points;
	}

	public boolean hasPoint(Point point) {
		boolean result = false;
		int i = 0;
		int j = points.length - 1;
		for (; i < points.length; j = i++) {
			if ((
			// Wenn y innerhalb der y-Grenzen liegt . . .
					(this.points[i].getY() <= point.getY())
							&& (point.getY() < this.points[j].getY()) || (this.points[j]
							.getY() <= point.getY())
							&& (point.getY() < this.points[i].getY())) && (
					// ... und der Strahl von (x, y) in die Unendlichkeit die
					// Kante vom i-ten zum j-ten Punkt kreuzt . . .
					point.getX() < (points[j].getX() - points[i].getX())
							* (point.getY() - points[i].getY())
							/ (points[j].getY() - points[i].getY())
							+ points[i].getX())) {
				result = !result;
			}
		}

		return result;
	}
}
