package eg.edu.alexu.csd.oop.draw.cs70;

import java.awt.Point;
import java.util.HashMap;
import java.util.Map;

import eg.edu.alexu.csd.oop.draw.Shape;

/**
 * @author H
 *
 */
public class LineSegment extends Stroke {

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
	public void setProperties(Map<String, Double>
	properties) {
		// TODO Auto-generated method stub

	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		Shape newShape = new LineSegment(prop.get("fpx"), prop.get("fpy"), prop.get("spx"), prop.get("spy"));
		cloneBasic(newShape, this);
		return newShape;
	}

}
