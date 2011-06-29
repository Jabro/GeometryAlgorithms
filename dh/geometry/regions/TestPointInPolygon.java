package dh.geometry.regions;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import dh.geometry.OurPoint;
import dh.geometry.OurPolygon;

public class TestPointInPolygon {

	@Test
	public void testFirstPolygon() {
		OurPolygon polygon = new OurPolygon(new OurPoint(1, 1), new OurPoint(3, 5),
				new OurPoint(6, 2), new OurPoint(9, 6), new OurPoint(10, 0), new OurPoint(
						4, 2), new OurPoint(5, -2));

		assertTrue(polygon.hasPoint(new OurPoint(3, 4)));
		assertTrue(polygon.hasPoint(new OurPoint(3, 1)));
		assertFalse(polygon.hasPoint(new OurPoint(3, -2)));
		assertFalse(polygon.hasPoint(new OurPoint(5, 4)));
		assertFalse(polygon.hasPoint(new OurPoint(5, 1)));
		assertFalse(polygon.hasPoint(new OurPoint(5, -2)));
		assertFalse(polygon.hasPoint(new OurPoint(7, 4)));
		assertTrue(polygon.hasPoint(new OurPoint(7, 1)));
		assertFalse(polygon.hasPoint(new OurPoint(7, -2)));
	}

	@Test
	public void testSecondPolygon() {
		OurPolygon polygon = new OurPolygon(new OurPoint(2, 3), new OurPoint(3, 2),
				new OurPoint(1, 0), new OurPoint(0, 1), new OurPoint(0, 2));

		assertFalse(polygon.hasPoint(new OurPoint(0, 0)));
		assertTrue(polygon.hasPoint(new OurPoint(1, 1)));
		assertTrue(polygon.hasPoint(new OurPoint(2, 2)));
		assertFalse(polygon.hasPoint(new OurPoint(2.5, 2.5)));
		assertFalse(polygon.hasPoint(new OurPoint(3, 3)));
	}
}
