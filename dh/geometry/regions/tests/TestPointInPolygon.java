package dh.geometry.regions.tests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import dh.geometry.regions.Point;
import dh.geometry.regions.Polygon;

public class TestPointInPolygon {

	@Test
	public void testFirstPolygon() {
		Polygon polygon = new Polygon(new Point(1, 1), new Point(3, 5),
				new Point(6, 2), new Point(9, 6), new Point(10, 0), new Point(
						4, 2), new Point(5, -2));

		assertTrue(polygon.hasPoint(new Point(3, 4)));
		assertTrue(polygon.hasPoint(new Point(3, 1)));
		assertFalse(polygon.hasPoint(new Point(3, -2)));
		assertFalse(polygon.hasPoint(new Point(5, 4)));
		assertFalse(polygon.hasPoint(new Point(5, 1)));
		assertFalse(polygon.hasPoint(new Point(5, -2)));
		assertFalse(polygon.hasPoint(new Point(7, 4)));
		assertTrue(polygon.hasPoint(new Point(7, 1)));
		assertFalse(polygon.hasPoint(new Point(7, -2)));
	}

	@Test
	public void testSecondPolygon() {
		Polygon polygon = new Polygon(new Point(2, 3), new Point(3, 2),
				new Point(1, 0), new Point(0, 1), new Point(0, 2));

		assertFalse(polygon.hasPoint(new Point(0, 0)));
		assertTrue(polygon.hasPoint(new Point(1, 1)));
		assertTrue(polygon.hasPoint(new Point(2, 2)));
		assertFalse(polygon.hasPoint(new Point(2.5, 2.5)));
		assertFalse(polygon.hasPoint(new Point(3, 3)));
	}
}
