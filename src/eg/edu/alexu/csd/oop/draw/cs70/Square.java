package eg.edu.alexu.csd.oop.draw.cs70;

import java.awt.Point;

import eg.edu.alexu.csd.oop.draw.Shape;
import eg.edu.alexu.csd.oop.draw.Stroke;
import gui.ColourAdapter;

/**
 * Holds data for square representation.
 * 
 * @author H
 *
 */
public class Square extends Stroke {

	/**
	 * Empty constructor.
	 */
	public Square() {
		super();
		prop.put("ulx", 0.0);
		prop.put("uly", 0.0);
		prop.put("sideL", 0.0);
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
	 * @param sideL
	 *            side length of the square.
	 */
	public Square(final double ulx, final double uly, final double sideL) {
		super();
		prop.put("ulx", ulx);
		prop.put("uly", uly);
		prop.put("sideL", sideL);
		prop.put("rotated", 0.0);
		center.x = (int) (ulx + sideL / 2);
		center.y = (int) (uly + sideL / 2);
	}

	/**
	 * Constructor if given upper left corner and side length.
	 * 
	 * @param ul
	 *            upper left corner given as Point object.
	 * @param sideL
	 *            side length of the square.
	 */
	public Square(final Point ul, final double sideL) {
		super();
		prop.put("ulx", ul.getX());
		prop.put("uly", ul.getY());
		prop.put("sideL", sideL);
		prop.put("rotated", 0.0);
		center.x = (int) (ul.getX() + sideL / 2);
		center.y = (int) (ul.getY() + sideL / 2);
	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		Shape newSquare = new Square(prop.get("ulx"), prop.get("uly"), prop.get("sideL"));
		newSquare.getProperties().put("rotated", prop.get("rotated"));
		cloneBasic(newSquare, this);
		return newSquare;
	}

	@Override
	public javafx.scene.shape.Shape makeFx() {
		javafx.scene.shape.Rectangle fxSquare = new javafx.scene.shape.Rectangle(prop.get("ulx"), prop.get("uly"),
				prop.get("sideL"), prop.get("sideL"));
		fxSquare.setFill(ColourAdapter.getFxColour(fill));
		fxSquare.setStroke(ColourAdapter.getFxColour(color));
		return fxSquare;
	}

}
