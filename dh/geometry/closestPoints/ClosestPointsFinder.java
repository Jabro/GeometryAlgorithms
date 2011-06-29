package dh.geometry.closestPoints;

import java.util.ArrayList;

import dh.geometry.OurPoint;

public class ClosestPointsFinder {

	public OurPoint[] getClosestPoints(OurPoint[] allPoints) {

		/*
		 * Check ob das übergebene Array vielleicht nur 0,1,2 Elemente
		 * beinhaltet. Falls zwei Punkte enthalten sind werden diese
		 * zurückgegegeben. Ansonsten null.
		 */
		if (allPoints.length < 2) {
			return null;
		} else if (allPoints.length == 2) {
			if (allPoints[0] != null && allPoints[1] != null) {
				return allPoints;
			} else {
				return null;
			}
		}

		/*
		 * In folgendem Array sollen nachher die dichtesten zwei Punkte
		 * übergeben werden.
		 */
		OurPoint[] closestPoints = null;

		/*
		 * Das folgende Array beinhaltet die Punktemenge nach x 
		 * sortiert.
		 */

		OurPoint[] sortedByX = sortByX(allPoints);

		/*
		 * Aufruf der Rekursion für die komplette Punktemenge
		 */

		closestPoints = getClosestPointsRecursive(sortedByX, 0,
				sortedByX.length - 1);

		return closestPoints;

	}

	private OurPoint[] getClosestPointsRecursive(OurPoint[] sortedByX, int i, int j) {
		OurPoint[] closestPoints = null;

		/*
		 * Check, ob nur keine, einer, zwei oder 3 Punkt(e) vorhanden sind. Bei
		 * zwei gib diese zurück. Bei drei suche die kürzeste Distanz in extra
		 * Methode. Ansonsten null.
		 */
		if (j - i < 1) {
			return null;
		} else if (j - i == 1) {
			closestPoints = new OurPoint[] { sortedByX[i], sortedByX[j] };
			return closestPoints;
		} else if (j - i == 2) {
			try {
				OurPoint[] threePoints = new OurPoint[] { sortedByX[i],
						sortedByX[i + 1], sortedByX[j] };
				return getClosestFromThree(threePoints);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		// Teilungsindex bestimmen
		int mid = (i + j) / 2;
		// Aufruf Rekursion für die linke Hälfte
		OurPoint[] closestPointsLeft = getClosestPointsRecursive(sortedByX, i,
				mid);
		// Aufruf Rekursion für die rechte Hälfte
		OurPoint[] closestPointsRight = getClosestPointsRecursive(sortedByX,
				mid + 1, j);

		double distanceLeft;
		double distanceRight;

		// Nachbereitende Analyse nach der Rekursion
		if (closestPointsLeft != null) {
			distanceLeft = distance(closestPointsLeft[0], closestPointsLeft[1]);
		} else {
			distanceLeft = Double.MAX_VALUE;
		}
		if (closestPointsRight != null) {
			distanceRight = distance(closestPointsRight[0],
					closestPointsRight[1]);
		} else {
			distanceRight = Double.MAX_VALUE;
		}
		double distance = (distanceLeft < distanceRight) ? distanceLeft
				: distanceRight;
		closestPoints = (distanceLeft < distanceRight) ? closestPointsLeft
				: closestPointsRight;

		// Die Entfernungen checken, die die Grenze überschreiten:
		// Linke Grenze des Rechten Bereichs:
		double right = sortedByX[mid].getX() + distance;
		// Rechte Grenze des Linken Bereichs:
		double left = sortedByX[mid + 1].getX() - distance;

		// Alle Punkte des Linken Bereichs suchen, die in Frage kommen:
		OurPoint[] pointsLeft = getCriticalPointsIndexMinus(sortedByX,
				Math.max(left, sortedByX[i].getX()), sortedByX[mid + 1].getX(),
				mid, i);
		// Alle Punkte des Rechten Bereichs suchen, die in Frage kommen:
		OurPoint[] pointsRight = getCriticalPointsIndexPlus(sortedByX,
				sortedByX[mid].getX(), Math.min(right, sortedByX[j].getX()),
				mid, j);

		// Kürzeren Abstand zwischen 2 Punkten aus dem linken und rechten
		// Teilbereich suchen
		OurPoint[] evenCloser = lookForCloser(pointsLeft, pointsRight, distance);
		if (evenCloser != null) {
			closestPoints = evenCloser;
		}

		return closestPoints;
	}

	/*
	 * Hier wird aus zwei benachbarten Punktemengen nach einem Punktepaar
	 * gesucht, dessen Distanz kleiner als distance ist. Falls keines gefunden
	 * wird: return null
	 */
	private OurPoint[] lookForCloser(OurPoint[] pointsLeft, OurPoint[] pointsRight,
			double distance) {
		OurPoint p1 = null;
		OurPoint p2 = null;
		double newDistance = Double.MAX_VALUE;
		for (OurPoint point1 : pointsLeft) {
			for (OurPoint point2 : pointsRight) {
				/*
				 * durch die if Abfragen wird überprüft, ob die Distanz
				 * überhaupt komplett errechnet werden muss, oder es schon
				 * offensichtlich ist, dass dieses Punktepaar die Distanz nicht
				 * unterbietet
				 */
				if ((Math.abs(point1.getX() - point2.getX()) < distance)
						|| (Math.abs(point1.getY() - point2.getY()) < distance)) {
					if (distance(point1, point2) < distance
							&& distance(point1, point2) < newDistance) {
						p1 = point1;
						p2 = point2;
						newDistance = distance(point1, point2);
					}
				}
			}
		}
		if (p1 != null && p2 != null) {
			OurPoint[] gotCloser = { p1, p2 };
			return gotCloser;
		} else {
			return null;
		}
	}

	
	/*
	 * Hier werden Punkte auf der rechten Seite der Grenze gesucht, die so nah an der Grenze
	 * sind, dass sie vielleicht die momentane Minimaldistanz unterbieten können in Verbindung
	 * mit einem Punkt der linken Seite.
	 */
	private OurPoint[] getCriticalPointsIndexPlus(OurPoint[] sortedByX, double left,
			double right, int mid, int maxIndex) {
		ArrayList<OurPoint> points = new ArrayList<OurPoint>();
		getCriticalPointsRecursiveIndexPlus(sortedByX, left, right, mid + 1,
				points, maxIndex);
		Object[] array = points.toArray();
		OurPoint[] result = new OurPoint[array.length];
		for (int i = 0; i < array.length; i++) {
			result[i] = (OurPoint) array[i];
		}

		return result;
	}

	/*
	 * Hier werden Punkte auf der linken Seite der Grenze gesucht, die so nah an der Grenze
	 * sind, dass sie vielleicht die momentane Minimaldistanz unterbieten können in Verbindung
	 * mit einem Punkt der rechten Seite.
	 */
	private OurPoint[] getCriticalPointsIndexMinus(OurPoint[] sortedByX, double left,
			double right, int mid, int minIndex) {
		ArrayList<OurPoint> points = new ArrayList<OurPoint>();
		if (sortedByX[mid].getX() >= left && sortedByX[mid].getX() <= right) {
			if (!points.contains(sortedByX[mid])) {
				points.add(sortedByX[mid]);
			}
		}
		getCriticalPointsRecursiveIndexMinus(sortedByX, left, right, mid - 1,
				points, minIndex);
		Object[] array = points.toArray();
		OurPoint[] result = new OurPoint[array.length];
		for (int i = 0; i < array.length; i++) {
			result[i] = (OurPoint) array[i];
		}

		return result;
	}

	/*
	 * Hier werden Punkte auf der rechten Seite der Grenze gesucht, die so nah an der Grenze
	 * sind, dass sie vielleicht die momentane Minimaldistanz unterbieten können in Verbindung
	 * mit einem Punkt der linken Seite.
	 * Rekursionsmethode. Geht immer einen Schritt weiter nach rechts im Array, bis man außerhalb
	 * der Grenzen ist.
	 */
	private void getCriticalPointsRecursiveIndexPlus(OurPoint[] sortedByX,
			double left, double right, int index, ArrayList<OurPoint> points,
			int maxIndex) {
		if (sortedByX[index].getX() <= right && index <= maxIndex) {
			if (!points.contains(sortedByX[index])) {
				points.add(sortedByX[index]);
			}
			if (index + 1 < sortedByX.length) {
				getCriticalPointsRecursiveIndexPlus(sortedByX, left, right,
						index + 1, points, maxIndex);
			}
		}

	}

	/*
	 * Hier werden Punkte auf der linken Seite der Grenze gesucht, die so nah an der Grenze
	 * sind, dass sie vielleicht die momentane Minimaldistanz unterbieten können in Verbindung
	 * mit einem Punkt der rechten Seite.
	 * Rekursionsmethode. Geht immer einen Schritt weiter nach links im Array, bis man außerhalb
	 * der Grenzen ist.
	 */
	private void getCriticalPointsRecursiveIndexMinus(OurPoint[] sortedByX,
			double left, double right, int index, ArrayList<OurPoint> points,
			int minIndex) {
		if (sortedByX[index].getX() >= left && index >= minIndex) {
			if (!points.contains(sortedByX[index])) {
				points.add(sortedByX[index]);
			}
			if (index - 1 >= 0) {
				getCriticalPointsRecursiveIndexMinus(sortedByX, left, right,
						index - 1, points, minIndex);
			}
		}

	}

	/*
	 * Liefert die beiden Punkte mit kürzester Distanz aus einer Menge von drei
	 * Punkten.
	 */
	private OurPoint[] getClosestFromThree(OurPoint[] threePoints) throws Exception {
		if (threePoints.length != 3) {
			throw new Exception("Something went wrong here.");
		}
		double d1 = distance(threePoints[0], threePoints[1]);
		double d2 = distance(threePoints[1], threePoints[2]);
		double d3 = distance(threePoints[2], threePoints[0]);
		OurPoint[] closest = new OurPoint[2];
		if (d1 <= d2 && d1 <= d3) {
			closest[0] = threePoints[0];
			closest[1] = threePoints[1];
		} else if (d2 <= d1 && d2 <= d3) {
			closest[0] = threePoints[1];
			closest[1] = threePoints[2];
		} else {
			closest[0] = threePoints[2];
			closest[1] = threePoints[0];
		}

		return closest;
	}

	
	/*
	 * Liefert die euklidische Distanz zweier Punkte
	 */
	private double distance(OurPoint point, OurPoint point2) {
		return Math.sqrt(Math.pow(point.getX() - point2.getX(), 2)
				+ Math.pow(point.getY() - point2.getY(), 2));
	}

	/*
	 * Die folgenden Methoden dienen nur zur Sortierung der Punkte. Einmal nach
	 * X und einmal nach Y Koordinaten.
	 */

	public OurPoint[] sortByX(OurPoint[] allPoints) {
		OurPoint[] toSortByX = allPoints.clone();
		quicksortByX(toSortByX, 0, toSortByX.length - 1);
		return toSortByX;
	}

	private void quicksortByX(OurPoint[] toSortByX, int low, int high) {
		int i = low, j = high;
		OurPoint pivot = toSortByX[low + (high - low) / 2];
		while (i <= j) {
			while (toSortByX[i].getX() < pivot.getX()) {
				i++;
			}
			while (toSortByX[j].getX() > pivot.getX()) {
				j--;
			}
			if (i <= j) {
				exchange(toSortByX, i, j);
				i++;
				j--;
			}
		}
		if (low < j)
			quicksortByX(toSortByX, low, j);
		if (i < high)
			quicksortByX(toSortByX, i, high);
	}

	public OurPoint[] sortByY(OurPoint[] allPoints) {
		OurPoint[] toSortByY = allPoints.clone();
		quicksortByY(toSortByY, 0, toSortByY.length - 1);
		return toSortByY;
	}

	private void quicksortByY(OurPoint[] toSortByY, int low, int high) {
		int i = low, j = high;
		OurPoint pivot = toSortByY[low + (high - low) / 2];
		while (i <= j) {
			while (toSortByY[i].getY() < pivot.getY()) {
				i++;
			}
			while (toSortByY[j].getY() > pivot.getY()) {
				j--;
			}
			if (j <= j) {
				exchange(toSortByY, i, j);
				i++;
				j--;
			}
		}
		if (low < j)
			quicksortByY(toSortByY, low, j);
		if (i < high)
			quicksortByY(toSortByY, i, high);
	}

	private void exchange(OurPoint[] points, int i, int j) {
		OurPoint temp = points[i];
		points[i] = points[j];
		points[j] = temp;
	}
	
	
	/*
	 * Test der Methoden.
	 * 
	 */

	public static void main(String[] args) {
		OurPoint p1 = new OurPoint(1, 2);
		OurPoint p2 = new OurPoint(5, 6);
		OurPoint p3 = new OurPoint(5, 2);
		OurPoint p4 = new OurPoint(4, 8);
		OurPoint p5 = new OurPoint(1, 20);
		OurPoint p6 = new OurPoint(4, 19);
		OurPoint p7 = new OurPoint(4, 22);
		OurPoint p8 = new OurPoint(3, 16);
		OurPoint p9 = new OurPoint(0, 0);
		OurPoint p10 = new OurPoint(2, 34);
		OurPoint p11 = new OurPoint(2, 33);
		OurPoint p12 = new OurPoint(20, 20);
		OurPoint[] points = { p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12 };

		ClosestPointsFinder cpf = new ClosestPointsFinder();
		OurPoint[] result = cpf.getClosestPoints(points);
		for (OurPoint p : result) {
			System.out.println(p);
		}

	}
}
