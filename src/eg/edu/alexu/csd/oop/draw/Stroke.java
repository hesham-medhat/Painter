package eg.edu.alexu.csd.oop.draw;

import java.awt.Color;
import java.awt.Point;
import java.io.IOException;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.json.simple.JSONArray;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import eg.edu.alexu.csd.oop.draw.Shape;
import javafx.scene.layout.Pane;

/**
 * Class of all shapes containing common characteristics and helper method for
 * the cloning process.
 * 
 * @author H
 *
 */
public abstract class Stroke implements Shape {

	private static final int RGB_COUNT = 3;

	/**
	 * JavaFx shape.
	 */
	private javafx.scene.shape.Shape fxShape;

	/**
	 * @return the fxShape
	 */
	public javafx.scene.shape.Shape getFxShape() {
		return fxShape;
	}

	/**
	 * @param fxShape
	 *            the fxShape to set
	 */
	public void setFxShape(javafx.scene.shape.Shape fxShape) {
		this.fxShape = fxShape;
	}

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
	 * Map containing the properties of the shape. String keys to Double values.
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
			center = new Point((int) position.getX(), (int) position.getY());
		} else {
			throw new IllegalArgumentException("Got null position!");
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
			throw new IllegalArgumentException("Got null border color!");
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
			throw new IllegalArgumentException("Got null filler color!");
		}
	}

	@Override
	public Color getFillColor() {
		return fill;
	}

	@Override
	public void setProperties(final Map<String, Double> properties) {
		prop = properties;
	}

	@Override
	public Map<String, Double> getProperties() {
		return prop;
	}

	@Override
	public void draw(Pane drawingPane) {
		drawingPane.getChildren().add(fxShape);
	}

	/**
	 * Clones fields in Stroke except prop. It is encouraged to use it when
	 * cloning any subclass as this should be common code while cloning. Only
	 * remaining step for cloning would be getting specific properties of the
	 * specific subclass to be cloned.
	 * 
	 * Ideally used as helper function in clone() overridden method from Object
	 * for Shapes.
	 * 
	 * @param newShape
	 *            to become a clone.
	 * @param other
	 *            to be cloned.
	 */
	public void cloneBasic(Shape newShape, final Shape other) {
		Color oldColor = other.getColor();
		Color newColor = new Color(oldColor.getRed(), oldColor.getGreen(), oldColor.getBlue());
		Color oldFillColor = other.getColor();
		Color newFillColor = new Color(oldFillColor.getRed(), oldFillColor.getGreen(), oldFillColor.getBlue());
		newShape.setColor(newColor);
		newShape.setFillColor(newFillColor);
		Point newPosition = new Point(other.getPosition());
		newShape.setPosition(newPosition);
	}

	public abstract Object clone() throws CloneNotSupportedException;

	public Element buildXMLElement(Document dom) {
		Element shapeRoot = dom.createElement(this.toString());
		float[] colorArr = new float[3];
		colorArr = color.getRGBColorComponents(colorArr);
		float[] fillArr = new float[3];
		fillArr = color.getRGBColorComponents(fillArr);
		Element colorArrB = dom.createElement("colorArr");
		Element fillArrB = dom.createElement("fillArr");
		for (int i = 0; i < 3; i++) {
			
			colorArrB.appendChild(dom.createTextNode(((Float) colorArr[i]).toString()));
			fillArrB.appendChild(dom.createTextNode(((Float) fillArr[i]).toString()));
		}
		shapeRoot.appendChild(colorArrB);
		shapeRoot.appendChild(fillArrB);
		shapeRoot.appendChild(dom.createElement("x").appendChild(dom.createTextNode(((Double) center.getX()).toString())));
		shapeRoot.appendChild(dom.createElement("y").appendChild(dom.createTextNode(((Double) center.getY()).toString())));
		shapeRoot.appendChild(dom.createElement("stroke").appendChild(dom.createTextNode(strokeWidth.toString())));
		shapeRoot.appendChild(dom.createElement("prop").appendChild(dom.createTextNode(prop.toString())));
		return shapeRoot;
	}

	public static Shape parseXMLShape(Node n) {
		try {
			Class<?> shapeClass = Class.forName(n.getFirstChild().toString());
			Shape sh = (Shape) shapeClass.newInstance();
			NodeList propList = n.getChildNodes();
			float[] colorArr = new float[3];
			float[] fillArr = new float[3];
			Point position = new Point();
			for (int i = 0; i < propList.getLength(); i++) {
				Element ele = (Element) propList.item(i);
				String tag = ele.getTagName();
				if (tag.equals("colorArr")) {
					NodeList colorArrB = ele.getChildNodes();
					for (int h = 0; h < 3; h++) {
						colorArr[i] = Float.parseFloat(colorArrB.item(i).getTextContent());
					}
					Color c = new Color(colorArr[0], colorArr[1], colorArr[2]);
					sh.setColor(c);
				} else if (tag.equals("fillArr")) {
					NodeList fillArrB = ele.getChildNodes();
					for (int h = 0; h < 3; h++) {
						fillArr[i] = Float.parseFloat(fillArrB.item(i).getTextContent());
					}
					Color f = new Color(fillArr[0], fillArr[1], fillArr[2]);
					sh.setColor(f);
				} else if (tag.equals("x")) {
					position.x = (int) (Double.parseDouble(ele.getTextContent()));
				} else if (tag.equals("y")) {
					position.y = (int) (Double.parseDouble(ele.getTextContent()));
				} else if (tag.equals("stroke")) {
					if (sh instanceof Stroke) {
						((Stroke) sh).strokeWidth = Double.parseDouble(ele.getTextContent());
					}
				} else if (tag.equals("prop")) {
					String str = ele.getTextContent();
					sh.setProperties(Stroke.readMap(str));
				} else {
					System.err.println("Element undefined!");
				}
			}
			sh.setPosition(position);
			return sh;
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
			e.printStackTrace();// Class not found.
			return null;
		}
	}

	public static HashMap<String, Double> readMap(String str) {
		Properties props = new Properties();
		try {
			props.load(new StringReader(str.substring(1, str.length() - 1).replace(", ", "\n")));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		HashMap<String, Double> propMap = new HashMap<String, Double>();
		for (Map.Entry<Object, Object> e : props.entrySet()) {
			propMap.put((String) e.getKey(), Double.parseDouble((String) e.getValue()));
		}
		return propMap;
	}

	public abstract javafx.scene.shape.Shape makeFx();

	@SuppressWarnings("unchecked")
	public JSONArray buildJsonArray() {
		JSONArray jBasicB = new JSONArray();
		jBasicB.add(this.toString());
		float[] colorArr = new float[RGB_COUNT];
		colorArr = color.getRGBColorComponents(colorArr);
		float[] fillArr = new float[RGB_COUNT];
		fillArr = color.getRGBColorComponents(fillArr);
		JSONArray colorArrB = new JSONArray();
		JSONArray fillArrB = new JSONArray();
		for (int i = 0; i < RGB_COUNT; i++) {
			colorArrB.add(colorArr[i]);
			fillArrB.add(fillArr[i]);
		}
		jBasicB.add(colorArrB);
		jBasicB.add(fillArrB);
		jBasicB.add(center.getX());
		jBasicB.add(center.getY());
		jBasicB.add(prop.toString());
		return jBasicB;
	}

	public static Shape readJsonArray(JSONArray jShape) {
		int i = 0;
		try {
			String className = jShape.get(i++).toString();
			Shape readShape = (Shape) Class.forName(className).newInstance();
			readShape.setColor(getJsonColor((JSONArray) jShape.get(i++)));
			readShape.setFillColor(getJsonColor((JSONArray) jShape.get(i++)));
			Point center = new Point((int) Double.parseDouble(jShape.get(i++).toString()), (int) Double.parseDouble(jShape.get(i++).toString()));
			readShape.setPosition(center);
			readShape.setProperties(readMap(jShape.get(i++).toString()));
			((Stroke) readShape).setFxShape(((Stroke) readShape).makeFx());
			return readShape;
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static Color getJsonColor (JSONArray jV) {
		JSONArray jColor = jV;
		float red, green, blue;
		red = Float.parseFloat(jColor.get(0).toString());
		green = Float.parseFloat(jColor.get(1).toString());
		blue = Float.parseFloat(jColor.get(2).toString());
		return new Color(red, green, blue);
	}

	public String toString() {
		return this.getClass().getName();
	}

}
