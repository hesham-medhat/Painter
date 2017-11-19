package eg.edu.alexu.csd.oop.draw.cs70;

import java.awt.Point;

import eg.edu.alexu.csd.oop.draw.Shape;
import eg.edu.alexu.csd.oop.draw.Stroke;
import gui.ColourAdapter;

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
		super();
		prop.put("fpx", fpx);
		prop.put("fpy", fpy);
		prop.put("spx", spx);
		prop.put("spy", spy);
		center.x = (int) ((fpx + spx) / 2);
		center.y = (int) ((fpy + spy) / 2);
	}

	/**
	 * Constructor if start-end points were given as Points.
	 * @param fp first point.
	 * @param sp second point.
	 */
	public LineSegment(final Point fp, final Point sp) {
		super();
		prop.put("fpx", fp.getX());
		prop.put("fpy", fp.getY());
		prop.put("spx", sp.getX());
		prop.put("spy", sp.getY());
		center.x = (int) ((fp.getX() + sp.getX()) / 2);
		center.y = (int) ((fp.getY() + sp.getY()) / 2);
	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		Shape newLineSegment = new LineSegment(prop.get("fpx"),
				prop.get("fpy"), prop.get("spx"),
				prop.get("spy"));
		cloneBasic(newLineSegment, this);
		return newLineSegment;
	}

	@Override
	public javafx.scene.shape.Shape makeFx() {
		javafx.scene.shape.Line fxLine = new javafx.scene.shape.Line(prop.get("fpx"), prop.get("fpy"), prop.get("spx"),prop.get("spy"));
		fxLine.setFill(ColourAdapter.getFxColour(fill));
		fxLine.setStroke(ColourAdapter.getFxColour(color));
		return fxLine;
	}

}
