package eg.edu.alexu.csd.oop.draw;

import java.awt.Color;
import java.awt.Point;
import java.util.HashMap;
import java.util.Map;

import javax.json.Json;
import javax.json.*;

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
	 * Stroke thickness of the shape's borders.
	 */
	protected Double strokeWidth;

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

	/**
	 * Default constructor.
	 */
	public Stroke() {
		prop = new HashMap<>();
		strokeWidth = 1.0;
		color = Color.BLACK;
		fill = Color.BLUE;
		center = new Point();
	}

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
	public void setProperties(final Map<String, Double>
	properties) {
		prop = properties;
	}

	@Override
	public Map<String, Double> getProperties() {
		return prop;
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

	public JsonArray buildJsonArray() {
		JsonArrayBuilder jBasicB = Json.createArrayBuilder();
		jBasicB.add(this.toString());
		float[] colorArr = new float[3];
		colorArr = color.getRGBColorComponents(colorArr);
		float[] fillArr = new float[3];
		fillArr = color.getRGBColorComponents(fillArr);
		JsonArrayBuilder colorArrB = Json.createArrayBuilder();
		JsonArrayBuilder fillArrB = Json.createArrayBuilder();
		for (int i = 0; i < 3; i++) {
			colorArrB.add((double) colorArr[i]);
			fillArrB.add((double) fillArr[i]);
		}
		jBasicB.add(colorArrB);
		jBasicB.add(fillArrB);
		jBasicB.add(center.getX());
		jBasicB.add(center.getY());
		jBasicB.add(strokeWidth);		
		jBasicB.add(prop.toString());
		return jBasicB.build();
	}

	public String toString() {
		return this.getClass().getName();
	}

}