package eg.edu.alexu.csd.oop.draw.cs70;

import java.awt.Point;
import java.util.HashMap;
import java.util.Map;

import eg.edu.alexu.csd.oop.draw.Shape;

/**
 * Holds data for line segment representation.
 * @author H
 *
 */
public class LineSegment extends Stroke {

	/**
	 * Default constructor given nothing.
	 */
	public LineSegment() {
		super();
		prop.put("fpx", 0.0);
		prop.put("fpy", 0.0);
		prop.put("spx", 0.0);
		prop.put("spy", 0.0);
	}

	/**
	 * Constructor if points were given as coordinates.
	 * @param fpx first point's x coordinate.
	 * @param fpy first point's y coordinate.
	 * @param spx second point's x coordinate.
	 * @param spy second point's y coordinate.
	 */
	public LineSegment(final double fpx,
			final double fpy, final double spx,
			final double spy) {
		Map<String, Double> properties =
				new HashMap<>();
		properties.put("fpx", fpx);
		properties.put("fpy", fpy);
		properties.put("spx", spx);
		properties.put("spy", spy);
		prop = properties;
	}

	/**
	 * Constructor if start-end points were given as Points.
	 * @param fp first point.
	 * @param sp second point.
	 */
	public LineSegment(final Point fp, final Point sp) {
		Map<String, Double> properties =
				new HashMap<>();
		properties.put("fpx", fp.getX());
		properties.put("fpy", fp.getY());
		properties.put("spx", sp.getX());
		properties.put("spy", sp.getY());
		prop = properties;
	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		Shape newShape = new LineSegment(prop.get("fpx"), prop.get("fpy"), prop.get("spx"), prop.get("spy"));
		cloneBasic(newShape, this);
		return newShape;
	}

}
