package eg.edu.alexu.csd.oop.draw.cs70;

import java.awt.Point;

import eg.edu.alexu.csd.oop.draw.Shape;
import eg.edu.alexu.csd.oop.draw.Stroke;

/**
 * Holds data for circle representation.
 * @author H
 *
 */
public class Circle extends Stroke {

	/**
	 * Default constructor given nothing.
	 */
	public Circle() {
		super();
		prop.put("radius", 0.0);
		prop.put("centerX", 0.0);
		prop.put("centerY", 0.0);
	}

	/**
	 * Constructor given props as is.
	 * @param radius radius of the circle.
	 * @param centerX x-coordinate of the center.
	 * @param centerY y-coordinate of the center.
	 */
	public Circle(final double radius,
			final double centerX, final double centerY) {
		super();
		prop.put("radius", radius);
		prop.put("centerX", centerX);
		prop.put("centerY", centerY);
		center.x = (int) centerX;
		center.y = (int) centerY;
	}

	/**
	 * Constructor given radius and center as Point object.
	 * @param radius radius of the circle.
	 * @param center center of the circle as Point object.
	 */
	public Circle(final double radius, final Point center) {
		super();
		prop.put("radius", radius);
		prop.put("centerX", center.getX());
		prop.put("centerY", center.getY());
		setPosition(center);
	}
	
	@Override
	public Object clone() throws CloneNotSupportedException {
		Shape newCircle = new Circle(prop.get("radius"),
				prop.get("centerX"), prop.get("centerY"));
		cloneBasic(newCircle, this);
		return newCircle;
	}

}
