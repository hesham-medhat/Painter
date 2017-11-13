package eg.edu.alexu.csd.oop.draw.cs70;

import java.awt.Point;

import eg.edu.alexu.csd.oop.draw.Shape;
import eg.edu.alexu.csd.oop.draw.Stroke;

/**
 * Holds data for ellipse representation.
 * @author H
 *
 */
public class Triangle extends Stroke {

	/**
	 * Default constructor given nothing.
	 */
	public Triangle() {
		super();
		prop.put("p1x", 0.0);
		prop.put("p2x", 0.0);
		prop.put("p3x", 0.0);
		prop.put("p1y", 0.0);
		prop.put("p2y", 0.0);
		prop.put("p3y", 0.0);
	}

	/**
	 * Constructor if props were given as is.
	 * @param p1x x-coordinate of the 1st point.
	 * @param p2x x-coordinate of the 2nd point.
	 * @param p3x x-coordinate of the 3rd point.
	 * @param p1y y-coordinate of the 1st point.
	 * @param p2y y-coordinate of the 2nd point.
	 * @param p3y y-coordinate of the 3rd point.
	 */
	public Triangle(final double p1x, final double p2x,
			final double p3x, final double p1y,
			final double p2y, final double p3y) {
		prop.put("p1x", p1x);
		prop.put("p2x", p2x);
		prop.put("p3x", p3x);
		prop.put("p1y", p1y);
		prop.put("p2y", p2y);
		prop.put("p3y", p3y);
		center();
	}

	/**
	 * Constructor if vertices were given as Point objects
	 * @param p1 first vertex point.
	 * @param p2 second vertex point.
	 * @param p3 third vertex point.
	 */
	public Triangle (final Point p1, final Point p2,
			final Point p3) {
		prop.put("p1x", p1.getX());
		prop.put("p2x", p2.getX());
		prop.put("p2x", p2.getX());
		prop.put("p1y", p1.getY());
		prop.put("p2y", p2.getY());
		prop.put("p3y", p3.getY());
		center();
	}

	/**
	 * Gets and sets the center by calculating it to be the
	 * mid-X and mid-Y according to the vertices.
	 * @return center calculated as Point object
	 */
	public Point center() {
		final double p1x = prop.get("p1x");
		final double p2x = prop.get("p2x");
		final double p3x = prop.get("p3x");
		final double p1y = prop.get("p1y");
		final double p2y = prop.get("p2y");
		final double p3y = prop.get("p3y");
		final double minX = (int)
				Math.min(p1x, Math.min(p2x, p3x));
		final double maxX = (int)
				Math.max(p1x, Math.max(p2x, p3x));
		final double minY = (int)
				Math.min(p1y, Math.min(p2y, p3y));
		final double maxY = (int)
				Math.min(p1y, Math.max(p2y, p3y));
		center.x = (int)
				((minX + maxX) / 2);
		center.y = (int)
				((minY + maxY) / 2);
		return center;
	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		Shape newTriangle = new Triangle(prop.get("p1x"),
				prop.get("p2x"), prop.get("p3x"), prop.get("p1y"),
						prop.get("p2y"), prop.get("p3y"));
		cloneBasic(newTriangle, this);
		return newTriangle;
	}

}
