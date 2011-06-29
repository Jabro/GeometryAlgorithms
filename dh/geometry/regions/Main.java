package dh.geometry.regions;

import java.awt.Graphics;
import java.awt.Polygon;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import dh.geometry.OurPoint;
import dh.geometry.OurPolygon;

public class Main extends JPanel {
	private static final long serialVersionUID = 1L;

	private static final int scale = 20;
	private static final int size = 500;

	private static JFrame frame;

	public static final OurPolygon polygon = new OurPolygon(new OurPoint(1, 1),
			new OurPoint(3, 5), new OurPoint(6, 2), new OurPoint(9, 6),
			new OurPoint(10, 0), new OurPoint(4, 2), new OurPoint(5, -2));

	public void paint(Graphics g) {
		Polygon p = Main.polygon.getAWTPolygon(scale);
		p.translate(size / 2, size / 2);
		g.drawPolygon(p);

		g.drawLine(size / 2, 0, size / 2, size);
		g.drawLine(0, size / 2, size, size / 2);

		for (int x = -size / 2; x < size / 2; x = x + scale) {
			g.drawLine(size / 2 + x + scale / 2, size / 2 - 3, size / 2 + x
					+ scale / 2, size / 2 + 3);
		}

		for (int y = -size / 2; y < size / 2; y = y + scale) {
			g.drawLine(size / 2 - 3, size / 2 + y + scale / 2, size / 2 + 3,
					size / 2 + y + scale / 2);
		}
	}

	public static void main(String[] args) {
		frame = new JFrame();
		frame.getContentPane().add(new Main());

		frame.getContentPane().addMouseMotionListener(
				new MouseMotionListener() {

					@Override
					public void mouseMoved(MouseEvent e) {
						if (polygon.hasPoint(new OurPoint((e.getX() - size / 2)
								/ scale, -(e.getY() - size / 2) / scale)))
							JOptionPane.showMessageDialog(frame,
									"Collision detected!");
					}

					@Override
					public void mouseDragged(MouseEvent e) {

					}
				});

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(size, size);
		frame.setVisible(true);
	}

}
