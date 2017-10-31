package eg.edu.alexu.csd.oop.draw.cs70;

import java.awt.Point;

import eg.edu.alexu.csd.oop.draw.Shape;
import eg.edu.alexu.csd.oop.draw.Stroke;

/**
 * Holds data for ellipse representation.
 * @author H
 *
 */
public class Ellipse extends Stroke {

	/**
	 * Default constructor given nothing.
	 */
	public Ellipse() {
		super();
		prop.put("radiusX", 0.0);
		prop.put("radiusY", 0.0);
		prop.put("centerX", 0.0);
		prop.put("centerY", 0.0);
	}

	/**
	 * Constructor if props were given as is.
	 * @param radiusX horizontal radius.
	 * @param radiusY vertical radius.
	 * @param centerX x-coordinate of the center.
	 * @param centerY y-coordinate of the center.
	 */
	public Ellipse(final double radiusX, final double radiusY,
			final double centerX, final double centerY) {
		prop.put("radiusX", radiusX);
		prop.put("radiusY", radiusY);
		prop.put("centerX", centerX);
		prop.put("centerY", centerY);
		center.x = (int) centerX;
		center.y = (int) centerY;
	}

	/**
	 * Constructor if center as Point object.
	 * @param radiusX horizontal radius.
	 * @param radiusY vertical radius.
	 * @param center as Point object.
	 */
	public Ellipse(final double radiusX, final double radiusY,
			final Point center) {
		prop.put("radiusX", radiusX);
		prop.put("radiusY", radiusY);
		prop.put("centerX", center.getX());
		prop.put("centerY", center.getY());
		setPosition(center);
	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		Shape newEllipse = new Ellipse(prop.get("radiusX"),
				prop.get("radiusY"), prop.get("centerX"),
				prop.get("centerY"));
		cloneBasic(newEllipse, this);
		return newEllipse;
	}

}
