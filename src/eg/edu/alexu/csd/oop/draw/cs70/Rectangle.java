package eg.edu.alexu.csd.oop.draw.cs70;

import java.awt.Point;

import eg.edu.alexu.csd.oop.draw.Shape;
import eg.edu.alexu.csd.oop.draw.Stroke;
import gui.ColourAdapter;

/**
 * Holds data for rectangle representation.
 * 
 * @author H
 *
 */
public class Rectangle extends Stroke {

	/**
	 * Empty constructor.
	 */
	public Rectangle() {
		super();
		prop.put("ulx", 0.0);
		prop.put("uly", 0.0);
		prop.put("width", 0.0);
		prop.put("height", 0.0);
		prop.put("rotated", 0.0);
		center.x = 0;
		center.y = 0;
	}

	/**
	 * Constructor if props were given as is.
	 * 
	 * @param ulx
	 *            upper left corner's x-coordinate.
	 * @param uly
	 *            upper left corner's y-coordinate.
	 * @param width
	 *            horizontal length of the rectangle.
	 * @param height
	 *            vertical length of the rectangle.
	 */
	public Rectangle(final double ulx, final double uly, final double width, final double height) {
		super();
		prop.put("ulx", ulx);
		prop.put("uly", uly);
		prop.put("width", width);
		prop.put("height", height);
		prop.put("rotated", 0.0);
		center.x = (int) (ulx + width / 2);
		center.y = (int) (uly + width / 2);
	}

	/**
	 * Constructor if given upper left corner and side length.
	 * 
	 * @param ul
	 *            upper left corner given as Point object.
	 * @param width
	 *            horizontal length of the square.
	 * @param height
	 *            vertical length of the rectangle.
	 */
	public Rectangle(final Point ul, final double width, final double height) {
		super();
		prop.put("ulx", ul.getX());
		prop.put("uly", ul.getY());
		prop.put("width", width);
		prop.put("height", height);
		prop.put("rotated", 0.0);
		center.x = (int) (ul.getX() + width / 2);
		center.y = (int) (ul.getY() + width / 2);
	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		Shape newRectangle = new Rectangle(prop.get("ulx"), prop.get("uly"), prop.get("width"), prop.get("height"));
		newRectangle.getProperties().put("rotated", prop.get("rotated"));
		cloneBasic(newRectangle, this);
		return newRectangle;
	}

	@Override
	public javafx.scene.shape.Shape makeFx() {
		javafx.scene.shape.Rectangle fxRec = new javafx.scene.shape.Rectangle(prop.get("ulx"), prop.get("uly"),
				prop.get("width"), prop.get("height"));
		fxRec.setFill(ColourAdapter.getFxColour(fill));
		fxRec.setStroke(ColourAdapter.getFxColour(color));
		return fxRec;
	}

}
