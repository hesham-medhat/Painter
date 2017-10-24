package eg.edu.alexu.csd.oop.draw.cs70;

import eg.edu.alexu.csd.oop.draw.Shape;

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

	public Ellipse(final double radiusX, final double radiusY,
			final double centerX, final double centerY) {
		prop.put("radiusX", radiusX);
		prop.put("radiusY", radiusY);
		prop.put("centerX", centerX);
		prop.put("centerY", centerY);
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
