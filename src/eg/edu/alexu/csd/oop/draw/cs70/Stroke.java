package eg.edu.alexu.csd.oop.draw.cs70;

import java.awt.Color;
import java.awt.Point;
import java.util.Map;

import eg.edu.alexu.csd.oop.draw.Shape;

/**
 * Class of all shapes containing common
 * characteristics and helper method for the
 * cloning process.
 * 
 * @author H
 *
 */
public abstract class Stroke implements Shape {

	/**
	 * Center point of the shape.
	 */
	protected Point center;

	/**
	 * Border color of the shape.
	 */
	protected Color color;

	/**
	 * Filler color within the shape's borders.
	 */
	protected Color fill;

	/**
	 * Map containing the properties of the shape.
	 * String keys to Double values.
	 */
	protected Map<String, Double> prop;

	@Override
	public void setPosition(final Point position) {
		if (position != null) {
			center = new Point((int) position.getX(),
					(int) position.getY());
		} else {
			throw new
			IllegalArgumentException("Got null position!");
		}
	}

	@Override
	public Point getPosition() {
		return center;
	}

	@Override
	public void setColor(final Color color) {
		if (color != null) {
			this.color = color;
		} else {
			throw new
			IllegalArgumentException("Got null border color!");
		}
	}

	@Override
	public Color getColor() {
		return color;
	}

	@Override
	public void setFillColor(final Color color) {
		if (color != null) {
			this.fill = color;
		} else {
			throw new
			IllegalArgumentException("Got null filler color!");
		}
	}

	@Override
	public Color getFillColor() {
		return fill;
	}

	@Override
	public void draw(final java.awt.Graphics canvas) {
		return;
	}

	/**
	 * Clones fields in Stroke except prop.
	 * It is encouraged to use it when cloning any subclass
	 * as this should be common code while cloning.
	 * Only remaining step for cloning would be getting specific
	 * properties of the specific subclass to be cloned.
	 * 
	 * Ideally used as helper function in clone() overridden
	 * method from Object for Shapes.
	 * 
	 * @param newShape to become a clone. 
	 * @param other to be cloned.
	 */
	public void cloneBasic(Shape newShape, final Shape other) {
		newShape.setColor(other.getColor());
		newShape.setFillColor(other.getFillColor());
		newShape.setPosition(other.getPosition());
	}

	public abstract Object clone()
			throws CloneNotSupportedException;

}
